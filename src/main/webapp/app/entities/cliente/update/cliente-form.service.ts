import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICliente, NewCliente } from '../cliente.model';
import dayjs from 'dayjs';

const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
const nHabilidacaoPattern = /^\d{11}$/;

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICliente for edit and NewClienteFormGroupInput for create.
 */
type ClienteFormGroupInput = ICliente | PartialWithRequiredKeyOf<NewCliente>;

type ClienteFormDefaults = Pick<NewCliente, 'id'>;

type ClienteFormGroupContent = {
  id: FormControl<ICliente['id'] | NewCliente['id']>;
  nome: FormControl<ICliente['nome']>;
  telefone1: FormControl<ICliente['telefone1']>;
  telefone2: FormControl<ICliente['telefone2']>;
  email: FormControl<ICliente['email']>;
  cpf: FormControl<ICliente['cpf']>;
  rg: FormControl<ICliente['rg']>;
  nHabilidacao: FormControl<ICliente['nHabilidacao']>;
  estadoEmissaoHabilitacao: FormControl<ICliente['estadoEmissaoHabilitacao']>;
  validadeHabilitacao: FormControl<ICliente['validadeHabilitacao']>;
  endereco: FormGroup<{
    id: FormControl<number | null | undefined>;
    cep: FormControl<string | null | undefined>;
    logradouro: FormControl<string | null | undefined>;
    numero: FormControl<string | null | undefined>;
    complemento: FormControl<string | null | undefined>;
    bairro: FormControl<string | null | undefined>;
  }>;
};

export type ClienteFormGroup = FormGroup<ClienteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ClienteFormService {
  createClienteFormGroup(cliente: ClienteFormGroupInput = { id: null }): ClienteFormGroup {
    const clienteRawValue = {
      ...this.getFormDefaults(),
      ...cliente,
    };
    return new FormGroup<ClienteFormGroupContent>({
      id: new FormControl(
        { value: clienteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(clienteRawValue.nome, {
        validators: [Validators.required],
      }),
      telefone1: new FormControl(clienteRawValue.telefone1, {
        validators: [Validators.required],
      }),
      telefone2: new FormControl(clienteRawValue.telefone2),
      email: new FormControl(clienteRawValue.email, {
        validators: [Validators.required, Validators.maxLength(30), Validators.pattern(emailPattern)],
      }),
      cpf: new FormControl(clienteRawValue.cpf, {
        validators: [Validators.required, Validators.maxLength(11)],
      }),
      rg: new FormControl(clienteRawValue.rg, {
        validators: [Validators.required, Validators.maxLength(10)],
      }),
      nHabilidacao: new FormControl(clienteRawValue.nHabilidacao, {
        validators: [Validators.required, Validators.pattern(nHabilidacaoPattern)],
      }),
      estadoEmissaoHabilitacao: new FormControl(clienteRawValue.estadoEmissaoHabilitacao, {
        validators: [Validators.required],
      }),
      validadeHabilitacao: new FormControl(clienteRawValue.validadeHabilitacao, {
        validators: [Validators.required],
      }),
      endereco: new FormGroup({
        id: new FormControl(clienteRawValue.endereco?.id),
        cep: new FormControl(clienteRawValue.endereco?.cep, {
          validators: [Validators.required],
        }),
        logradouro: new FormControl(clienteRawValue.endereco?.logradouro, {
          validators: [Validators.required],
        }),
        numero: new FormControl(clienteRawValue.endereco?.numero, {
          validators: [Validators.required],
        }),
        complemento: new FormControl(clienteRawValue.endereco?.complemento),
        bairro: new FormControl(clienteRawValue.endereco?.bairro, {
          validators: [Validators.required],
        }),
      }),
    });
  }

  getCliente(form: ClienteFormGroup): ICliente | NewCliente {
    return form.getRawValue() as ICliente | NewCliente;
  }

  resetForm(form: ClienteFormGroup, cliente: ClienteFormGroupInput): void {
    const clienteRawValue = { ...this.getFormDefaults(), ...cliente };
    const estadoEmissaoHabilitacao = this.convertDates(clienteRawValue.estadoEmissaoHabilitacao);
    const validadeHabilitacao = this.convertDates(clienteRawValue.validadeHabilitacao);
    form.reset(
      {
        ...clienteRawValue,
        id: { value: clienteRawValue.id, disabled: true },
        estadoEmissaoHabilitacao,
        validadeHabilitacao,
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private convertDates(date: any): Date | null {
    if (date) {
      return dayjs(date).toDate();
    }
    return null;
  }

  private getFormDefaults(): ClienteFormDefaults {
    return {
      id: null,
    };
  }
}
