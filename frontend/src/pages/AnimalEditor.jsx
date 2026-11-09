import React, {useEffect, useRef, useState} from 'react';
import {getAnimal, saveAnimal, removeAnimal} from '../services/animals.js';

const labels = {gato:'Gato', cachorro:'Cachorro', macho:'Macho', femea:'Fêmea', pequeno:'Pequeno', médio:'Médio', grande:'Grande', meses:'Meses', anos:'Anos'};
function Choice({label, name, values, value}) {
  return <label>{label}<select required name={name} defaultValue={value || ''}>
    <option value="">Selecione</option>{values.map(v => <option key={v} value={v}>{labels[v]}</option>)}
  </select></label>;
}

export default function AnimalEditor({id, onSaved}) {
  const [pet, setPet] = useState(null);
  const [error, setError] = useState('');
  const [busy, setBusy] = useState(false);
  const [confirm, setConfirm] = useState('');
  const lock = useRef(false);
  useEffect(() => {
    if (!id) return;
    const ctl = new AbortController();
    getAnimal(id, {signal:ctl.signal}).then(setPet).catch(e => {if (!ctl.signal.aborted) setError(e.message)});
    return () => ctl.abort();
  }, [id]);

  async function perform(action) {
    if (lock.current) return;
    lock.current = true;
    setBusy(true);
    setError('');
    try { await action() }
    catch (e) { setError(e.message) }
    finally { lock.current = false; setBusy(false); setConfirm('') }
  }
  function save(e) {
    e.preventDefault();
    const body = Object.fromEntries(new FormData(e.currentTarget));
    body.nome = body.nome.trim();
    body.descricao = body.descricao.trim();
    body.idade = Number(body.idade);
    body.idadeAproximada = body.idadeAproximada === 'on';
    perform(async () => onSaved(await saveAnimal(id, body)));
  }
  return <section className="registration-page"><div className="registration-card">
    <a href={id ? '#animal/'+id : '#inicio'}>← Voltar</a>
    <h1>{id ? 'Gerenciar anúncio' : 'Anunciar um animal'}</h1>
    {error && <p role="alert" className="registration-error">{error}</p>}
    {id && !pet ? (!error && <p>Carregando anúncio…</p>) : <>
      <form onSubmit={save}><fieldset disabled={busy || !!confirm}><div className="registration-grid">
        <label>Nome do animal<input name="nome" required maxLength={60} defaultValue={pet?.nome}/></label>
        <Choice name="especie" label="Espécie" values={['gato','cachorro']} value={pet?.especie}/>
        <label>Idade<input type="number" name="idade" min="0" max="2147483647" step="1" required defaultValue={pet?.idade}/></label>
        <Choice name="unidadeIdade" label="Unidade da idade" values={['meses','anos']} value={pet?.unidadeIdade}/>
        <Choice name="sexo" label="Sexo" values={['macho','femea']} value={pet?.sexo}/>
        <Choice name="porte" label="Porte" values={['pequeno','médio','grande']} value={pet?.porte==='medio'?'médio':pet?.porte}/>
        <label className="terms-check wide"><input name="idadeAproximada" type="checkbox" defaultChecked={pet?.idadeAproximada}/>Idade aproximada</label>
        <label className="wide">Foto do animal (link)<input name="fotoAnimal" required maxLength={255} defaultValue={pet?.foto} placeholder="https://…"/></label>
        <label className="wide">Descrição<textarea required name="descricao" rows="4" defaultValue={pet?.descricao}/></label>
      </div><button className="brutal-button submit-register">{busy?'Aguarde…':id?'Salvar alterações':'Publicar anúncio'}</button></fieldset></form>
      {id && <section className="manage-actions">{confirm ? <div role="group" aria-label="Confirmação">
        <strong>{confirm==='adotar'?'Concluir adoção':'Excluir anúncio'} de {pet.nome}?</strong>
        <p>O anúncio e o registro do animal serão excluídos permanentemente.</p>
        <button disabled={busy} onClick={()=>setConfirm('')}>Cancelar</button>
        <button disabled={busy} onClick={()=>perform(async()=>{await removeAnimal(id,confirm==='adotar');onSaved(null)})}>Confirmar {confirm==='adotar'?'adoção':'exclusão'}</button>
      </div> : <><button disabled={busy} onClick={()=>setConfirm('adotar')}>Marcar como adotado</button>
        <button disabled={busy} onClick={()=>setConfirm('excluir')}>Excluir anúncio</button></>}</section>}
    </>}
  </div></section>;
}
