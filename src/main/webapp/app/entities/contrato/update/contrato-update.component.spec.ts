import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';
import { IVeiculo } from 'app/entities/veiculo/veiculo.model';
import { VeiculoService } from 'app/entities/veiculo/service/veiculo.service';
import { IContrato } from '../contrato.model';
import { ContratoService } from '../service/contrato.service';
import { ContratoFormService } from './contrato-form.service';

import { ContratoUpdateComponent } from './contrato-update.component';

describe('Contrato Management Update Component', () => {
  let comp: ContratoUpdateComponent;
  let fixture: ComponentFixture<ContratoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contratoFormService: ContratoFormService;
  let contratoService: ContratoService;
  let clienteService: ClienteService;
  let veiculoService: VeiculoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ContratoUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ContratoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContratoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contratoFormService = TestBed.inject(ContratoFormService);
    contratoService = TestBed.inject(ContratoService);
    clienteService = TestBed.inject(ClienteService);
    veiculoService = TestBed.inject(VeiculoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call cliente query and add missing value', () => {
      const contrato: IContrato = { id: 456 };
      const cliente: ICliente = { id: 31318 };
      contrato.cliente = cliente;

      const clienteCollection: ICliente[] = [{ id: 22131 }];
      jest.spyOn(clienteService, 'query').mockReturnValue(of(new HttpResponse({ body: clienteCollection })));
      const expectedCollection: ICliente[] = [cliente, ...clienteCollection];
      jest.spyOn(clienteService, 'addClienteToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contrato });
      comp.ngOnInit();

      expect(clienteService.query).toHaveBeenCalled();
      expect(clienteService.addClienteToCollectionIfMissing).toHaveBeenCalledWith(clienteCollection, cliente);
      expect(comp.clientesCollection).toEqual(expectedCollection);
    });

    it('Should call veiculo query and add missing value', () => {
      const contrato: IContrato = { id: 456 };
      const veiculo: IVeiculo = { id: 19264 };
      contrato.veiculo = veiculo;

      const veiculoCollection: IVeiculo[] = [{ id: 2879 }];
      jest.spyOn(veiculoService, 'query').mockReturnValue(of(new HttpResponse({ body: veiculoCollection })));
      const expectedCollection: IVeiculo[] = [veiculo, ...veiculoCollection];
      jest.spyOn(veiculoService, 'addVeiculoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contrato });
      comp.ngOnInit();

      expect(veiculoService.query).toHaveBeenCalled();
      expect(veiculoService.addVeiculoToCollectionIfMissing).toHaveBeenCalledWith(veiculoCollection, veiculo);
      expect(comp.veiculosCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contrato: IContrato = { id: 456 };
      const cliente: ICliente = { id: 26258 };
      contrato.cliente = cliente;
      const veiculo: IVeiculo = { id: 10933 };
      contrato.veiculo = veiculo;

      activatedRoute.data = of({ contrato });
      comp.ngOnInit();

      expect(comp.clientesCollection).toContain(cliente);
      expect(comp.veiculosCollection).toContain(veiculo);
      expect(comp.contrato).toEqual(contrato);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContrato>>();
      const contrato = { id: 123 };
      jest.spyOn(contratoFormService, 'getContrato').mockReturnValue(contrato);
      jest.spyOn(contratoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contrato });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contrato }));
      saveSubject.complete();

      // THEN
      expect(contratoFormService.getContrato).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contratoService.update).toHaveBeenCalledWith(expect.objectContaining(contrato));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContrato>>();
      const contrato = { id: 123 };
      jest.spyOn(contratoFormService, 'getContrato').mockReturnValue({ id: null });
      jest.spyOn(contratoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contrato: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contrato }));
      saveSubject.complete();

      // THEN
      expect(contratoFormService.getContrato).toHaveBeenCalled();
      expect(contratoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContrato>>();
      const contrato = { id: 123 };
      jest.spyOn(contratoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contrato });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contratoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCliente', () => {
      it('Should forward to clienteService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(clienteService, 'compareCliente');
        comp.compareCliente(entity, entity2);
        expect(clienteService.compareCliente).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareVeiculo', () => {
      it('Should forward to veiculoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(veiculoService, 'compareVeiculo');
        comp.compareVeiculo(entity, entity2);
        expect(veiculoService.compareVeiculo).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
