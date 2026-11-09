import {apiRequest,ApiError} from './api.js';
import {getValidSession,endSession} from '../lib/session.js';
export async function loginAccount({identificador,senha},options={}){
 const {status,data}=await apiRequest('/api/auth/login',{...options,method:'POST',body:{identificador:identificador.trim(),senha}});
 if(status!==200||!data||typeof data.token!=='string'||!data.token||!Number.isInteger(data.id)||typeof data.nome!=='string'||typeof data.username!=='string'||typeof data.email!=='string')throw new ApiError('Resposta de login inesperada. Tente novamente mais tarde.');
 return data;
}

export async function authorizedRequest(path,options={}){
 const current=getValidSession();if(!current)throw new ApiError('Sua sessão expirou. Entre novamente.',401);
 try{return await apiRequest(path,{...options,token:current.token})}catch(error){if(error.status===401){if(getValidSession()?.token===current.token)endSession();throw new ApiError('Sua sessão expirou. Entre novamente.',401)}throw error}
}
