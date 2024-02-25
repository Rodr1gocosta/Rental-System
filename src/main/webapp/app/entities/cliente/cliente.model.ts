import dayjs from 'dayjs/esm';
import { IEndereco } from 'app/entities/endereco/endereco.model';
import { IContrato } from 'app/entities/contrato/contrato.model';

export interface ICliente {
  id: number;
  nome?: string | null;
  telefone1?: string | null;
  telefone2?: string | null;
  email?: string | null;
  cpf?: string | null;
  rg?: string | null;
  nHabilidacao?: string | null;
  estadoEmissaoHabilitacao?: dayjs.Dayjs | null;
  validadeHabilitacao?: dayjs.Dayjs | null;
  endereco?: IEndereco | null;
  contrato?: Pick<IContrato, 'id'> | null;
}

export type NewCliente = Omit<ICliente, 'id'> & { id: null };
