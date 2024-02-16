import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IContrato, NewContrato } from '../contrato.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContrato for edit and NewContratoFormGroupInput for create.
 */
type ContratoFormGroupInput = IContrato | PartialWithRequiredKeyOf<NewContrato>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IContrato | NewContrato> = Omit<
  T,
  'periodo' | 'contratoDataInicio' | 'contratoDataFinal' | 'contratoDataRetirada' | 'contratoDataEntrega'
> & {
  periodo?: string | null;
  contratoDataInicio?: string | null;
  contratoDataFinal?: string | null;
  contratoDataRetirada?: string | null;
  contratoDataEntrega?: string | null;
};

type ContratoFormRawValue = FormValueOf<IContrato>;

type NewContratoFormRawValue = FormValueOf<NewContrato>;

type ContratoFormDefaults = Pick<
  NewContrato,
  'id' | 'periodo' | 'contratoDataInicio' | 'contratoDataFinal' | 'contratoDataRetirada' | 'contratoDataEntrega'
>;

type ContratoFormGroupContent = {
  id: FormControl<ContratoFormRawValue['id'] | NewContrato['id']>;
  taxaDoDia: FormControl<ContratoFormRawValue['taxaDoDia']>;
  kmInicial: FormControl<ContratoFormRawValue['kmInicial']>;
  kmFinal: FormControl<ContratoFormRawValue['kmFinal']>;
  periodo: FormControl<ContratoFormRawValue['periodo']>;
  contratoDataInicio: FormControl<ContratoFormRawValue['contratoDataInicio']>;
  contratoDataFinal: FormControl<ContratoFormRawValue['contratoDataFinal']>;
  contratoDataRetirada: FormControl<ContratoFormRawValue['contratoDataRetirada']>;
  contratoDataEntrega: FormControl<ContratoFormRawValue['contratoDataEntrega']>;
  cliente: FormControl<ContratoFormRawValue['cliente']>;
  veiculo: FormControl<ContratoFormRawValue['veiculo']>;
};

export type ContratoFormGroup = FormGroup<ContratoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContratoFormService {
  createContratoFormGroup(contrato: ContratoFormGroupInput = { id: null }): ContratoFormGroup {
    const contratoRawValue = this.convertContratoToContratoRawValue({
      ...this.getFormDefaults(),
      ...contrato,
    });
    return new FormGroup<ContratoFormGroupContent>({
      id: new FormControl(
        { value: contratoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      taxaDoDia: new FormControl(contratoRawValue.taxaDoDia),
      kmInicial: new FormControl(contratoRawValue.kmInicial),
      kmFinal: new FormControl(contratoRawValue.kmFinal),
      periodo: new FormControl(contratoRawValue.periodo, {
        validators: [Validators.required],
      }),
      contratoDataInicio: new FormControl(contratoRawValue.contratoDataInicio, {
        validators: [Validators.required],
      }),
      contratoDataFinal: new FormControl(contratoRawValue.contratoDataFinal, {
        validators: [Validators.required],
      }),
      contratoDataRetirada: new FormControl(contratoRawValue.contratoDataRetirada),
      contratoDataEntrega: new FormControl(contratoRawValue.contratoDataEntrega),
      cliente: new FormControl(contratoRawValue.cliente),
      veiculo: new FormControl(contratoRawValue.veiculo),
    });
  }

  getContrato(form: ContratoFormGroup): IContrato | NewContrato {
    return this.convertContratoRawValueToContrato(form.getRawValue() as ContratoFormRawValue | NewContratoFormRawValue);
  }

  resetForm(form: ContratoFormGroup, contrato: ContratoFormGroupInput): void {
    const contratoRawValue = this.convertContratoToContratoRawValue({ ...this.getFormDefaults(), ...contrato });
    form.reset(
      {
        ...contratoRawValue,
        id: { value: contratoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContratoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      periodo: currentTime,
      contratoDataInicio: currentTime,
      contratoDataFinal: currentTime,
      contratoDataRetirada: currentTime,
      contratoDataEntrega: currentTime,
    };
  }

  private convertContratoRawValueToContrato(rawContrato: ContratoFormRawValue | NewContratoFormRawValue): IContrato | NewContrato {
    return {
      ...rawContrato,
      periodo: dayjs(rawContrato.periodo, DATE_TIME_FORMAT),
      contratoDataInicio: dayjs(rawContrato.contratoDataInicio, DATE_TIME_FORMAT),
      contratoDataFinal: dayjs(rawContrato.contratoDataFinal, DATE_TIME_FORMAT),
      contratoDataRetirada: dayjs(rawContrato.contratoDataRetirada, DATE_TIME_FORMAT),
      contratoDataEntrega: dayjs(rawContrato.contratoDataEntrega, DATE_TIME_FORMAT),
    };
  }

  private convertContratoToContratoRawValue(
    contrato: IContrato | (Partial<NewContrato> & ContratoFormDefaults),
  ): ContratoFormRawValue | PartialWithRequiredKeyOf<NewContratoFormRawValue> {
    return {
      ...contrato,
      periodo: contrato.periodo ? contrato.periodo.format(DATE_TIME_FORMAT) : undefined,
      contratoDataInicio: contrato.contratoDataInicio ? contrato.contratoDataInicio.format(DATE_TIME_FORMAT) : undefined,
      contratoDataFinal: contrato.contratoDataFinal ? contrato.contratoDataFinal.format(DATE_TIME_FORMAT) : undefined,
      contratoDataRetirada: contrato.contratoDataRetirada ? contrato.contratoDataRetirada.format(DATE_TIME_FORMAT) : undefined,
      contratoDataEntrega: contrato.contratoDataEntrega ? contrato.contratoDataEntrega.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
