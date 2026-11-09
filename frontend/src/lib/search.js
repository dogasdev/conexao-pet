export const emptyFilters={cidade:'',porte:'',sexo:'',especie:''};
export const normalize=s=>String(s).normalize('NFD').replace(/[\u0300-\u036f]/g,'').toLowerCase().trim();
export function filterAnimals(animals,filters){return animals.filter(a=>a.status==='disponivel' && (!filters.cidade || normalize(`${a.cidade}, ${a.uf}`)===normalize(filters.cidade)) && ['porte','sexo','especie'].every(k=>!filters[k]||a[k]===filters[k]));}
export const ageLabel=months=>months<12?`${months} ${months===1?'mês':'meses'}`:`${Math.floor(months/12)} ${Math.floor(months/12)===1?'ano':'anos'}${months%12?` e ${months%12} ${months%12===1?'mês':'meses'}`:''}`;
