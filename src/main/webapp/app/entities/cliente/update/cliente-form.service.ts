import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICliente, NewCliente } from '../cliente.model';

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
  endereco: FormControl<ICliente['endereco']>;
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
        validators: [Validators.required, Validators.maxLength(30)],
      }),
      cpf: new FormControl(clienteRawValue.cpf, {
        validators: [Validators.required, Validators.maxLength(11)],
      }),
      rg: new FormControl(clienteRawValue.rg, {
        validators: [Validators.required, Validators.maxLength(10)],
      }),
      nHabilidacao: new FormControl(clienteRawValue.nHabilidacao, {
        validators: [Validators.required, Validators.maxLength(12)],
      }),
      estadoEmissaoHabilitacao: new FormControl(clienteRawValue.estadoEmissaoHabilitacao, {
        validators: [Validators.required],
      }),
      validadeHabilitacao: new FormControl(clienteRawValue.validadeHabilitacao, {
        validators: [Validators.required],
      }),
      endereco: new FormControl(clienteRawValue.endereco),
    });
  }

  getCliente(form: ClienteFormGroup): ICliente | NewCliente {
    return form.getRawValue() as ICliente | NewCliente;
  }

  resetForm(form: ClienteFormGroup, cliente: ClienteFormGroupInput): void {
    const clienteRawValue = { ...this.getFormDefaults(), ...cliente };
    form.reset(
      {
        ...clienteRawValue,
        id: { value: clienteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ClienteFormDefaults {
    return {
      id: null,
    };
  }
}
