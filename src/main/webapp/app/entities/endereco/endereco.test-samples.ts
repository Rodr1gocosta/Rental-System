import { IEndereco, NewEndereco } from './endereco.model';

export const sampleWithRequiredData: IEndereco = {
  id: 31391,
  cep: 'shoo oddball qua',
  logradouro: 'ha whose blank',
  numero: 'when tenth troll',
  bairro: 'than',
};

export const sampleWithPartialData: IEndereco = {
  id: 14065,
  cep: 'provided',
  logradouro: 'mid geez',
  numero: 'heartwood pish unwieldy',
  complemento: 'ew',
  bairro: 'instead',
};

export const sampleWithFullData: IEndereco = {
  id: 32427,
  cep: 'divide so but',
  logradouro: 'which toad',
  numero: 'under gadzooks',
  complemento: 'between',
  bairro: 'lest',
};

export const sampleWithNewData: NewEndereco = {
  cep: 'unless worriedly',
  logradouro: 'hoarse once',
  numero: 'engineer afore',
  bairro: 'average',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
