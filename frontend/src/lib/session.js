// Session is intentionally memory-only: never save passwords or tokens in localStorage.
let session=null;let expiryTimer;let revision=0;const subscribers=new Set();
export const getSession=()=>session;
export const getSessionRevision=()=>revision;
export function subscribeSession(fn){subscribers.add(fn);return()=>subscribers.delete(fn)}
function emit(){revision++;for(const fn of subscribers)fn()}
export function tokenExpiresAt(token){try{const part=token.split('.')[1].replace(/-/g,'+').replace(/_/g,'/');const claims=JSON.parse(atob(part));return typeof claims.exp==='number'&&Number.isFinite(claims.exp)?claims.exp*1000:0}catch{return 0}}
export function endSession(){clearTimeout(expiryTimer);session=null;emit()}
export function startSession(data){const expiresAt=tokenExpiresAt(data.token);if(expiresAt<=Date.now())throw new Error('O servidor retornou uma sessão expirada ou inválida. Entre novamente.');clearTimeout(expiryTimer);session={token:data.token,user:{id:data.id,nome:data.nome,username:data.username,email:data.email},expiresAt};expiryTimer=setTimeout(endSession,Math.min(expiresAt-Date.now(),2147483647));emit();return session}
export function getValidSession(){if(session&&session.expiresAt<=Date.now())endSession();return session}
if(typeof window!=='undefined'){window.addEventListener('focus',getValidSession);document.addEventListener('visibilitychange',()=>{if(!document.hidden)getValidSession()})}
