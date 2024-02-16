import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../contrato.test-samples';

import { ContratoFormService } from './contrato-form.service';

describe('Contrato Form Service', () => {
  let service: ContratoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContratoFormService);
  });

  describe('Service methods', () => {
    describe('createContratoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContratoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            taxaDoDia: expect.any(Object),
            kmInicial: expect.any(Object),
            kmFinal: expect.any(Object),
            periodo: expect.any(Object),
            contratoDataInicio: expect.any(Object),
            contratoDataFinal: expect.any(Object),
            contratoDataRetirada: expect.any(Object),
            contratoDataEntrega: expect.any(Object),
            cliente: expect.any(Object),
            veiculo: expect.any(Object),
          }),
        );
      });

      it('passing IContrato should create a new form with FormGroup', () => {
        const formGroup = service.createContratoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            taxaDoDia: expect.any(Object),
            kmInicial: expect.any(Object),
            kmFinal: expect.any(Object),
            periodo: expect.any(Object),
            contratoDataInicio: expect.any(Object),
            contratoDataFinal: expect.any(Object),
            contratoDataRetirada: expect.any(Object),
            contratoDataEntrega: expect.any(Object),
            cliente: expect.any(Object),
            veiculo: expect.any(Object),
          }),
        );
      });
    });

    describe('getContrato', () => {
      it('should return NewContrato for default Contrato initial value', () => {
        const formGroup = service.createContratoFormGroup(sampleWithNewData);

        const contrato = service.getContrato(formGroup) as any;

        expect(contrato).toMatchObject(sampleWithNewData);
      });

      it('should return NewContrato for empty Contrato initial value', () => {
        const formGroup = service.createContratoFormGroup();

        const contrato = service.getContrato(formGroup) as any;

        expect(contrato).toMatchObject({});
      });

      it('should return IContrato', () => {
        const formGroup = service.createContratoFormGroup(sampleWithRequiredData);

        const contrato = service.getContrato(formGroup) as any;

        expect(contrato).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IContrato should not enable id FormControl', () => {
        const formGroup = service.createContratoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewContrato should disable id FormControl', () => {
        const formGroup = service.createContratoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
