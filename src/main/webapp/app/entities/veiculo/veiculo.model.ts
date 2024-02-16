import dayjs from 'dayjs/esm';
import { IContrato } from 'app/entities/contrato/contrato.model';
import { Classe } from 'app/entities/enumerations/classe.model';
import { Grupo } from 'app/entities/enumerations/grupo.model';
import { Situacao } from 'app/entities/enumerations/situacao.model';

export interface IVeiculo {
  id: number;
  placa?: string | null;
  marca?: string | null;
  modelo?: string | null;
  dataAquisicao?: dayjs.Dayjs | null;
  classe?: keyof typeof Classe | null;
  grupo?: keyof typeof Grupo | null;
  situcao?: keyof typeof Situacao | null;
  contrato?: Pick<IContrato, 'id'> | null;
}

export type NewVeiculo = Omit<IVeiculo, 'id'> & { id: null };
