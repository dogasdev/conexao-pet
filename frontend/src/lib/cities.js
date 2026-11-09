import cities from '../data/cities.json' with {type:'json'};
import {normalize} from './search.js';
export const cityLabel=c=>`${c.nome}, ${c.uf}`;
const indexed=cities.map(c=>({...c,key:normalize(c.nome),labelKey:normalize(cityLabel(c))}));
export function suggestCities(query,limit=8){const q=normalize(query);if(!q)return [];return indexed.filter(c=>c.labelKey.includes(q)).sort((a,b)=>(a.key===q?0:a.key.startsWith(q)?1:2)-(b.key===q?0:b.key.startsWith(q)?1:2)||a.nome.localeCompare(b.nome,'pt-BR')||a.uf.localeCompare(b.uf)).slice(0,limit);}
export function resolveCity(query){const q=normalize(query);const matches=indexed.filter(c=>c.key===q||c.labelKey===q);return matches.length===1?matches[0]:null;}
