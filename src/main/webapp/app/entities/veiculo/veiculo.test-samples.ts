import dayjs from 'dayjs/esm';

import { IVeiculo, NewVeiculo } from './veiculo.model';

export const sampleWithRequiredData: IVeiculo = {
  id: 22610,
  placa: 'conside',
  marca: 'ah degenerate',
  modelo: 'cleverly ignite',
  classe: 'LOCACAO',
  grupo: 'SEDAN_MEDIO',
  situcao: 'REPARO',
};

export const sampleWithPartialData: IVeiculo = {
  id: 1513,
  placa: 'stub sa',
  marca: 'worse incidentally g',
  modelo: 'eek disburse',
  dataAquisicao: dayjs('2024-02-05'),
  classe: 'PASSEIO',
  grupo: 'SEDAN_LUXO',
  situcao: 'ALUGADO',
};

export const sampleWithFullData: IVeiculo = {
  id: 32184,
  placa: 'afore a',
  marca: 'to segment ack',
  modelo: 'to but',
  dataAquisicao: dayjs('2024-02-05'),
  classe: 'LOCACAO',
  grupo: 'HATCH_COMPACTO',
  situcao: 'MANUTENCAO',
};

export const sampleWithNewData: NewVeiculo = {
  placa: 'executi',
  marca: 'inaugurate',
  modelo: 'instead heavily',
  classe: 'LOCACAO',
  grupo: 'SEDAN_MEDIO',
  situcao: 'MANUTENCAO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
