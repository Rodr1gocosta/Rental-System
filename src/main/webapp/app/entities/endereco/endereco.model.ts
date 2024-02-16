import { ICliente } from 'app/entities/cliente/cliente.model';

export interface IEndereco {
  id: number;
  cep?: string | null;
  logradouro?: string | null;
  numero?: string | null;
  complemento?: string | null;
  bairro?: string | null;
  cliente?: Pick<ICliente, 'id'> | null;
}

export type NewEndereco = Omit<IEndereco, 'id'> & { id: null };
