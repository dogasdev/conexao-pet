export const userFields=['username','email','senha','telefone','nome','sobrenome','cpf','cidade'];
export const institutionFields=['nomeInstituicao','cnpj','rua','numero','bairro','cep',...userFields];
export function registrationPayload(values,type){
 return Object.fromEntries((type==='instituicao'?institutionFields:userFields).map(key=>{
 let value=String(values[key]??'');
 if(key!=='senha')value=value.trim();
 if(['cpf','cep','telefone'].includes(key))value=value.replace(/\D/g,'');
 if(key==='cnpj')value=value.replace(/[^a-z0-9]/gi,'').toUpperCase();
 return [key,value];
 }));
}
export function registrationError(values,type='usuario'){
 const payload=registrationPayload(values,type);
 if(Object.entries(payload).some(([key,value])=>!value.trim()))return 'Preencha todos os campos obrigatórios.';
 if(!/^\d{11}$/.test(payload.cpf))return 'Informe um CPF com 11 números.';
 if(!/^\d{10,11}$/.test(payload.telefone))return 'Informe o telefone com DDD e 10 ou 11 números.';
 if(type==='instituicao'&&!/^\d{8}$/.test(payload.cep))return 'Informe um CEP com 8 números.';
 if(type==='instituicao'&&registrationPayload(values,type).cnpj.length!==14)return 'Informe um CNPJ de 14 caracteres.';
 if(values.senha!==values.confirmarSenha)return 'As senhas precisam ser iguais.';
 if(new TextEncoder().encode(values.senha).length>72)return 'A senha deve ter no máximo 72 bytes. Reduza o tamanho ou a quantidade de caracteres especiais.';
 if(!values.aceite)return 'Leia e aceite os termos para continuar.';
 return '';
}
