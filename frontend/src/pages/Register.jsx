import React,{useState,useRef} from 'react';
import {UserRound,Building2,ArrowRight,Eye,EyeOff,CheckCircle2} from 'lucide-react';
import {suggestCities,resolveCity,cityLabel} from '../lib/cities.js';
import {registrationError} from '../lib/registration.js';
import {registerAccount,registrationEnabled} from '../services/registration.js';
import './registration.css';
export default function Register(){
 const [type,setType]=useState('usuario'),[city,setCity]=useState(''),[show,setShow]=useState(false),[busy,setBusy]=useState(false),[error,setError]=useState(''),[success,setSuccess]=useState('');const lock=useRef(false),message=useRef(null);
 const choices=suggestCities(city);
 async function submit(e){
 e.preventDefault();if(lock.current)return;const form=e.currentTarget;const data=Object.fromEntries(new FormData(form));data.aceite=data.aceite==='on';
 const resolved=resolveCity(city);const problem=registrationError(data,type)||(!resolved?'Escolha uma cidade brasileira com seu estado.':'');
 if(problem){setError(problem);requestAnimationFrame(()=>message.current?.focus());return;}
 data.cidade=cityLabel(resolved);setError('');lock.current=true;setBusy(true);
 try{const msg=await registerAccount(data,type);form.reset();setCity('');setSuccess(msg)}catch(err){setError(err.message)}finally{lock.current=false;setBusy(false);requestAnimationFrame(()=>message.current?.focus())}
 }
 return <section className="registration-page"><div className="registration-card">
 {success?<div className="registration-success" ref={message} tabIndex="-1" role="status"><CheckCircle2 size={44}/><h1>Conta criada!</h1><p>{success}</p><p>Você já pode entrar com seu e-mail ou nome de usuário.</p><a className="brutal-button" href="#login">Entrar na minha conta <ArrowRight size={20}/></a></div>:<>
 <h1>Crie sua conta</h1><p className="registration-intro">Escolha como você quer participar.</p>
 <div className="account-types" aria-label="Tipo de cadastro">{[['usuario','Pessoa física',UserRound],['instituicao','Instituição',Building2]].map(([v,label,Icon])=><button disabled={busy} type="button" key={v} aria-pressed={type===v} className={type===v?'selected':''} onClick={()=>{setType(v);setError('')}}><Icon size={21}/>{label}</button>)}</div>
 {!registrationEnabled&&<p className="registration-notice">Prévia do formulário. A criação de contas estará disponível quando o serviço de cadastro for conectado.</p>}
 <form onSubmit={submit}>
 <fieldset disabled={busy}>
 {type==='instituicao'&&<><legend>Dados da instituição</legend><div className="registration-grid"><Field name="nomeInstituicao" label="Nome da instituição" maxLength={150} wide/><Field name="cnpj" label="CNPJ" maxLength={18}  title="Informe o CNPJ com 14 caracteres, com ou sem pontuação."/><Field name="cep" label="CEP" inputMode="numeric" pattern="[0-9]{5}-?[0-9]{3}" maxLength={9}/><Field name="rua" label="Rua" maxLength={150}/><Field name="numero" label="Número" maxLength={10}/><Field name="bairro" label="Bairro" maxLength={100} wide/></div></>}
 {type==='usuario'?<legend>Seus dados</legend>:<h2>Responsável pela instituição</h2>}
 <div className="registration-grid"><Field name="nome" label="Nome" maxLength={80} autoComplete="given-name"/><Field name="sobrenome" label="Sobrenome" maxLength={100} autoComplete="family-name"/><Field name="cpf" label="CPF" inputMode="numeric" maxLength={14} pattern={String.raw`([0-9]{11}|[0-9]{3}\.[0-9]{3}\.[0-9]{3}-[0-9]{2})`} title="Informe 11 números, com ou sem pontos e traço."/><Field name="telefone" label="Telefone" type="tel" autoComplete="tel" maxLength={20} />
 <label className="wide">{type==='instituicao'?'Cidade da instituição e do responsável':'Cidade'}<input name="cidade" value={city} onChange={e=>setCity(e.target.value)} list="registration-cities" placeholder="Digite a cidade e selecione o estado" maxLength={100} required autoComplete="off"/><datalist id="registration-cities">{choices.map(c=><option key={c.id} value={cityLabel(c)}/>)}</datalist></label></div>
 <h2>Dados de acesso</h2><div className="registration-grid"><Field name="username" label="Nome de usuário" maxLength={50} autoComplete="username"/><Field name="email" label="E-mail" type="email" maxLength={255} autoComplete="email"/>
 {['senha','confirmarSenha'].map((name,i)=><label key={name}>{i?'Confirmar senha':'Senha'}<div className="password-input"><input name={name} type={show?'text':'password'} required autoComplete="new-password" maxLength={72}/><button type="button" onClick={()=>setShow(!show)} aria-label={`${show?'Ocultar':'Mostrar'} ${i?'confirmação da senha':'senha'}`}>{show?<EyeOff size={20}/>:<Eye size={20}/>}</button></div></label>)}</div>
 <label className="terms-check"><input name="aceite" type="checkbox" required/><span>Li e aceito os <a href="#termos" target="_blank" rel="noopener">Termos de Serviço</a> e li a <a href="#privacidade" target="_blank" rel="noopener">Política de Privacidade</a>.</span></label>
 </fieldset>
 {error&&<p className="registration-error" role="alert" ref={message} tabIndex="-1">{error}</p>}
 <button className="brutal-button submit-register" type="submit" disabled={busy||!registrationEnabled}>{busy?'Enviando cadastro…':'Criar minha conta'}<ArrowRight size={21}/></button>
 </form></>}
 </div></section>
}
function Field({label,wide=false,required=true,...props}){return <label className={wide?'wide':''}>{label}<input required={required} {...props}/></label>}
