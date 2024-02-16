import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IVeiculo, NewVeiculo } from '../veiculo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IVeiculo for edit and NewVeiculoFormGroupInput for create.
 */
type VeiculoFormGroupInput = IVeiculo | PartialWithRequiredKeyOf<NewVeiculo>;

type VeiculoFormDefaults = Pick<NewVeiculo, 'id'>;

type VeiculoFormGroupContent = {
  id: FormControl<IVeiculo['id'] | NewVeiculo['id']>;
  placa: FormControl<IVeiculo['placa']>;
  marca: FormControl<IVeiculo['marca']>;
  modelo: FormControl<IVeiculo['modelo']>;
  dataAquisicao: FormControl<IVeiculo['dataAquisicao']>;
  classe: FormControl<IVeiculo['classe']>;
  grupo: FormControl<IVeiculo['grupo']>;
  situcao: FormControl<IVeiculo['situcao']>;
};

export type VeiculoFormGroup = FormGroup<VeiculoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class VeiculoFormService {
  createVeiculoFormGroup(veiculo: VeiculoFormGroupInput = { id: null }): VeiculoFormGroup {
    const veiculoRawValue = {
      ...this.getFormDefaults(),
      ...veiculo,
    };
    return new FormGroup<VeiculoFormGroupContent>({
      id: new FormControl(
        { value: veiculoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      placa: new FormControl(veiculoRawValue.placa, {
        validators: [Validators.required, Validators.maxLength(7)],
      }),
      marca: new FormControl(veiculoRawValue.marca, {
        validators: [Validators.required, Validators.maxLength(20)],
      }),
      modelo: new FormControl(veiculoRawValue.modelo, {
        validators: [Validators.required, Validators.maxLength(20)],
      }),
      dataAquisicao: new FormControl(veiculoRawValue.dataAquisicao),
      classe: new FormControl(veiculoRawValue.classe, {
        validators: [Validators.required],
      }),
      grupo: new FormControl(veiculoRawValue.grupo, {
        validators: [Validators.required],
      }),
      situcao: new FormControl(veiculoRawValue.situcao, {
        validators: [Validators.required],
      }),
    });
  }

  getVeiculo(form: VeiculoFormGroup): IVeiculo | NewVeiculo {
    return form.getRawValue() as IVeiculo | NewVeiculo;
  }

  resetForm(form: VeiculoFormGroup, veiculo: VeiculoFormGroupInput): void {
    const veiculoRawValue = { ...this.getFormDefaults(), ...veiculo };
    form.reset(
      {
        ...veiculoRawValue,
        id: { value: veiculoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): VeiculoFormDefaults {
    return {
      id: null,
    };
  }
}
