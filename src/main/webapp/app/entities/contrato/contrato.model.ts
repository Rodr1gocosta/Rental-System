import dayjs from 'dayjs/esm';
import { ICliente } from 'app/entities/cliente/cliente.model';
import { IVeiculo } from 'app/entities/veiculo/veiculo.model';

export interface IContrato {
  id: number;
  taxaDoDia?: string | null;
  kmInicial?: number | null;
  kmFinal?: number | null;
  periodo?: dayjs.Dayjs | null;
  contratoDataInicio?: dayjs.Dayjs | null;
  contratoDataFinal?: dayjs.Dayjs | null;
  contratoDataRetirada?: dayjs.Dayjs | null;
  contratoDataEntrega?: dayjs.Dayjs | null;
  cliente?: Pick<ICliente, 'id'> | null;
  veiculo?: Pick<IVeiculo, 'id'> | null;
}

export type NewContrato = Omit<IContrato, 'id'> & { id: null };
