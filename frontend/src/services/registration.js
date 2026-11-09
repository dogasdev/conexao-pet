import {registrationPayload} from '../lib/registration.js';
import {apiRequest,apiEnabled} from './api.js';
export const registrationEnabled=apiEnabled;
export async function registerAccount(values,type,{apiBase,...options}={}){
 if(!['usuario','instituicao'].includes(type))throw new Error('Tipo de cadastro inválido.');
 try{const {status,data}=await apiRequest(`/api/auth/cadastro/${type}`,{...options,...(apiBase!==undefined?{base:apiBase}:{}),method:'POST',body:registrationPayload(values,type)});
 if(status!==201||typeof data?.mensagem!=='string')throw new Error('Resposta inesperada do servidor. Confirme se a conta foi criada antes de tentar novamente.');return data.mensagem;
 }catch(error){if(error.message.includes('demorou'))throw new Error('O servidor demorou para responder. Confirme se a conta foi criada antes de tentar novamente.');throw error}
}
