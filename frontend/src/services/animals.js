import { animals } from '../data/animals.js';
import { normalize, filterAnimals } from '../lib/search.js';
import { resolveCity } from '../lib/cities.js';
import { apiRequest, apiEnabled } from './api.js';
import { authorizedRequest } from './auth.js';

const endpoint = '/api/anuncios';
const locations = new Map();

function adapt(row) {
  const id = row.idAnuncio ?? row.id;
  const nome = row.nome ?? row.nomeAnimal;
  if (!Number.isInteger(id) || !nome) throw new Error('Resposta de anúncio inválida.');
  const place = row.cidadeResponsavel || '';
  const resolved = place && resolveCity(place);
  return {
    id, nome, idade: row.idade, unidadeIdade: row.unidadeIdade,
    idadeMeses: row.idade * (row.unidadeIdade === 'anos' ? 12 : 1),
    idadeAproximada: row.idadeAproximada, especie: row.especie,
    sexo: row.sexo, porte: normalize(row.porte), status: normalize(row.status),
    foto: row.fotoAnimal, descricao: row.descricao, responsavel: row.nomeResponsavel,
    cidade: resolved?.nome || place, uf: resolved?.uf || ''
  };
}

export async function getAnimal(id, options = {}) {
  if (!/^\d+$/.test(String(id))) throw new Error('Endereço de anúncio inválido.');
  if (!(options.enabled ?? apiEnabled)) return animals.find(a => a.id === Number(id));
  const {data} = await apiRequest(endpoint + '/' + id, options);
  const pet = adapt(data);
  locations.set(pet.id, {cidade: pet.cidade, uf: pet.uf});
  return pet;
}

export async function listAnimals(filters, options = {}) {
  if (!(options.enabled ?? apiEnabled)) return filterAnimals(animals, filters);
  const {data} = await apiRequest(endpoint, options);
  if (!Array.isArray(data)) throw new Error('Lista de anúncios inválida.');
  const rows = data.map(adapt).map(pet => ({...pet, ...locations.get(pet.id)}));
  const candidates = filterAnimals(rows, {...filters, cidade: ''});
  if (!filters.cidade) return candidates;
  // Only request missing locations for pets matching the other filters.
  const pending = candidates.filter(pet => !locations.has(pet.id) && !pet.cidade);
  async function fillLocations() {
    while (pending.length && !options.signal?.aborted) {
      const pet = pending.pop();
      await getAnimal(pet.id, options);
    }
  }
  await Promise.all(Array.from({length: Math.min(4, pending.length)}, fillLocations));
  return filterAnimals(candidates.map(pet => ({...pet, ...locations.get(pet.id)})), filters);
}

export async function saveAnimal(id, body, options = {}) {
  const {data, status} = await authorizedRequest(id ? endpoint + '/' + id : endpoint,
    {...options, method: id ? 'PUT' : 'POST', body});
  if (status !== (id ? 200 : 201) || !Number.isInteger(data?.idAnuncio)) {
    throw new Error('Resposta inesperada. Confira a vitrine antes de reenviar.');
  }
  locations.delete(data.idAnuncio);
  return data.idAnuncio;
}

export async function removeAnimal(id, adoption, options = {}) {
  const path = endpoint + '/' + id + (adoption ? '/adotar' : '');
  const {status} = await authorizedRequest(path, {...options, method: adoption ? 'PATCH' : 'DELETE'});
  if (status !== 204) throw new Error('Confira o anúncio antes de tentar novamente.');
  locations.delete(Number(id));
}
