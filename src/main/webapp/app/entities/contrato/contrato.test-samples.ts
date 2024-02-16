import dayjs from 'dayjs/esm';

import { IContrato, NewContrato } from './contrato.model';

export const sampleWithRequiredData: IContrato = {
  id: 23820,
  periodo: dayjs('2024-02-05T10:12'),
  contratoDataInicio: dayjs('2024-02-05T14:56'),
  contratoDataFinal: dayjs('2024-02-05T03:21'),
};

export const sampleWithPartialData: IContrato = {
  id: 27982,
  taxaDoDia: 'inasmuch until',
  periodo: dayjs('2024-02-05T19:38'),
  contratoDataInicio: dayjs('2024-02-05T21:54'),
  contratoDataFinal: dayjs('2024-02-05T16:54'),
};

export const sampleWithFullData: IContrato = {
  id: 3930,
  taxaDoDia: 'invigorate willfully delicious',
  kmInicial: 4929,
  kmFinal: 28011,
  periodo: dayjs('2024-02-05T17:40'),
  contratoDataInicio: dayjs('2024-02-05T11:09'),
  contratoDataFinal: dayjs('2024-02-05T09:37'),
  contratoDataRetirada: dayjs('2024-02-05T04:02'),
  contratoDataEntrega: dayjs('2024-02-05T09:30'),
};

export const sampleWithNewData: NewContrato = {
  periodo: dayjs('2024-02-05T00:18'),
  contratoDataInicio: dayjs('2024-02-05T22:51'),
  contratoDataFinal: dayjs('2024-02-05T02:39'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
