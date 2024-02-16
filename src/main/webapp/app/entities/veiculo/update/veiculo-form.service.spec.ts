import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../veiculo.test-samples';

import { VeiculoFormService } from './veiculo-form.service';

describe('Veiculo Form Service', () => {
  let service: VeiculoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VeiculoFormService);
  });

  describe('Service methods', () => {
    describe('createVeiculoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createVeiculoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            placa: expect.any(Object),
            marca: expect.any(Object),
            modelo: expect.any(Object),
            dataAquisicao: expect.any(Object),
            classe: expect.any(Object),
            grupo: expect.any(Object),
            situcao: expect.any(Object),
          }),
        );
      });

      it('passing IVeiculo should create a new form with FormGroup', () => {
        const formGroup = service.createVeiculoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            placa: expect.any(Object),
            marca: expect.any(Object),
            modelo: expect.any(Object),
            dataAquisicao: expect.any(Object),
            classe: expect.any(Object),
            grupo: expect.any(Object),
            situcao: expect.any(Object),
          }),
        );
      });
    });

    describe('getVeiculo', () => {
      it('should return NewVeiculo for default Veiculo initial value', () => {
        const formGroup = service.createVeiculoFormGroup(sampleWithNewData);

        const veiculo = service.getVeiculo(formGroup) as any;

        expect(veiculo).toMatchObject(sampleWithNewData);
      });

      it('should return NewVeiculo for empty Veiculo initial value', () => {
        const formGroup = service.createVeiculoFormGroup();

        const veiculo = service.getVeiculo(formGroup) as any;

        expect(veiculo).toMatchObject({});
      });

      it('should return IVeiculo', () => {
        const formGroup = service.createVeiculoFormGroup(sampleWithRequiredData);

        const veiculo = service.getVeiculo(formGroup) as any;

        expect(veiculo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IVeiculo should not enable id FormControl', () => {
        const formGroup = service.createVeiculoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewVeiculo should disable id FormControl', () => {
        const formGroup = service.createVeiculoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
