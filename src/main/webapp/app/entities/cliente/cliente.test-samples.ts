import dayjs from 'dayjs/esm';

import { ICliente, NewCliente } from './cliente.model';

export const sampleWithRequiredData: ICliente = {
  id: 13690,
  nome: 'coppice',
  telefone1: 'endorse elliptical',
  email: 'Silas28@bol.com.br',
  cpf: 'crossly whe',
  rg: 'pace',
  nHabilidacao: 'hourglass ja',
  estadoEmissaoHabilitacao: dayjs('2024-02-05'),
  validadeHabilitacao: dayjs('2024-02-05'),
};

export const sampleWithPartialData: ICliente = {
  id: 3925,
  nome: 'regarding',
  telefone1: 'why shelter however',
  telefone2: 'loosely consequently',
  email: 'Margarida74@live.com',
  cpf: 'questioning',
  rg: 'ick',
  nHabilidacao: 'genuine',
  estadoEmissaoHabilitacao: dayjs('2024-02-05'),
  validadeHabilitacao: dayjs('2024-02-05'),
};

export const sampleWithFullData: ICliente = {
  id: 21640,
  nome: 'stable herring',
  telefone1: 'until',
  telefone2: 'after tremendous',
  email: 'Calebe.Silva52@live.com',
  cpf: 'aid fooey',
  rg: 'supposing',
  nHabilidacao: 'hmph hmph gr',
  estadoEmissaoHabilitacao: dayjs('2024-02-05'),
  validadeHabilitacao: dayjs('2024-02-05'),
};

export const sampleWithNewData: NewCliente = {
  nome: 'why',
  telefone1: 'than where',
  email: 'Matheus.Batista@live.com',
  cpf: 'that',
  rg: 'interpose ',
  nHabilidacao: 'gee',
  estadoEmissaoHabilitacao: dayjs('2024-02-05'),
  validadeHabilitacao: dayjs('2024-02-05'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
