// All requests go to the Java API. Vite forwards /api in development.
const env=import.meta.env||{};
export const apiEnabled=!!env.DEV || env.VITE_API_ENABLED==='true' || env.VITE_REGISTRATION_ENABLED==='true';
export const apiBase=(env.VITE_API_BASE_URL||'').replace(/\/$/,'');
export class ApiError extends Error{constructor(message,status=0){super(message);this.status=status;}}
export async function apiRequest(path,{method='GET',body,token,signal,fetcher=fetch,enabled=apiEnabled,base=apiBase,timeoutMs=15000}={}){
 if(!enabled)throw new ApiError('O serviço de contas ainda não está conectado nesta prévia.');
 const controller=new AbortController();const abort=()=>controller.abort();signal?.addEventListener('abort',abort,{once:true});if(signal?.aborted)controller.abort();
 const timer=setTimeout(abort,timeoutMs);
 try{
 const response=await fetcher(`${base}${path}`,{method,headers:{Accept:'application/json, text/plain',...(body?{'Content-Type':'application/json'}:{}),...(token?{Authorization:`Bearer ${token}`}:{})},...(body?{body:JSON.stringify(body)}:{}),signal:controller.signal});
 const text=await response.text();let data;try{data=JSON.parse(text)}catch{data=text}
 if(!response.ok){const fields=data && typeof data==='object' && !data.erro && response.status===400 ? Object.entries(data).filter(([,v])=>typeof v==='string').map(([k,v])=>k+': '+v).join(' · ') : '';const message=fields || (response.status===401?'E-mail, usuário ou senha incorretos.':response.status===403?'Você não tem permissão para acessar este recurso.':typeof data?.erro==='string'?data.erro:response.status>=500?'O servidor não conseguiu concluir a solicitação. Tente novamente mais tarde.':'Não foi possível concluir a solicitação. Confira os dados.');throw new ApiError(message,response.status)}
 return {status:response.status,data};
 }catch(error){if(error.name==='AbortError')throw new ApiError('O servidor demorou para responder. Tente novamente.');if(error instanceof TypeError)throw new ApiError('Não foi possível acessar o servidor. Confira sua conexão ou tente novamente mais tarde.');throw error}
 finally{clearTimeout(timer);signal?.removeEventListener('abort',abort)}
}
