import test from 'node:test';
import assert from 'node:assert/strict';
import {listAnimals,getAnimal,saveAnimal,removeAnimal} from '../services/animals.js';
import {emptyFilters} from './search.js';
import {startSession,endSession} from './session.js';

const row = {idAnuncio:87,idAnimal:2,nome:'Mia',idade:2,unidadeIdade:'anos',
  idadeAproximada:true,especie:'gato',sexo:'femea',porte:'médio',status:'disponível'};
const detail = {...row,id:87,nomeAnimal:'Mia',nomeResponsavel:'Ana',cidadeResponsavel:'Fortaleza, CE'};
delete detail.idAnuncio;
delete detail.nome;
test('vitrine e busca usam id do anúncio e cidade da ficha, sem enviar token', async () => {
  const calls=[];
  const options={enabled:true,fetcher:async(path,req)=>{
    calls.push(path);
    assert.equal(req.headers.Authorization,undefined);
    return Response.json(path.endsWith('/87') ? detail : [row]);
  }};
  const pets=await listAnimals({...emptyFilters,cidade:'Fortaleza, CE',porte:'medio'},options);
  assert.deepEqual(calls,['/api/anuncios','/api/anuncios/87']);
  assert.equal(pets[0].id,87);
  assert.equal(pets[0].idadeMeses,24);
  assert.equal((await getAnimal(87,options)).responsavel,'Ana');
  assert.deepEqual(await listAnimals({...emptyFilters,cidade:'Recife, PE'},options),[]);
});
test('criar, editar, adotar e excluir seguem o contrato Java com Bearer', async () => {
  const token='e30.'+Buffer.from(JSON.stringify({exp:Date.now()/1000+7200})).toString('base64url')+'.test';
  startSession({id:1,nome:'Ana',token});
  const calls=[];
  const options={enabled:true,fetcher:async(path,req)=>{
    assert.equal(req.headers.Authorization,'Bearer '+token);
    calls.push([req.method,path]);
    if(req.body) return Response.json(row,{status:req.method==='POST'?201:200});
    return new Response(null,{status:204});
  }};
  try {
    assert.equal(await saveAnimal(null,{nome:'Mia'},options),87);
    await saveAnimal(87,{nome:'Mia'},options);
    await removeAnimal(87,true,options);
    await removeAnimal(87,false,options);
    assert.deepEqual(calls,[['POST','/api/anuncios'],['PUT','/api/anuncios/87'],['PATCH','/api/anuncios/87/adotar'],['DELETE','/api/anuncios/87']]);
  } finally {endSession()}
});
