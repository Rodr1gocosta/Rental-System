import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICliente } from 'app/entities/cliente/cliente.model';
import { ClienteService } from 'app/entities/cliente/service/cliente.service';
import { IVeiculo } from 'app/entities/veiculo/veiculo.model';
import { VeiculoService } from 'app/entities/veiculo/service/veiculo.service';
import { ContratoService } from '../service/contrato.service';
import { IContrato } from '../contrato.model';
import { ContratoFormService, ContratoFormGroup } from './contrato-form.service';

@Component({
  standalone: true,
  selector: 'jhi-contrato-update',
  templateUrl: './contrato-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ContratoUpdateComponent implements OnInit {
  isSaving = false;
  contrato: IContrato | null = null;

  clientesCollection: ICliente[] = [];
  veiculosCollection: IVeiculo[] = [];

  editForm: ContratoFormGroup = this.contratoFormService.createContratoFormGroup();

  constructor(
    protected contratoService: ContratoService,
    protected contratoFormService: ContratoFormService,
    protected clienteService: ClienteService,
    protected veiculoService: VeiculoService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareCliente = (o1: ICliente | null, o2: ICliente | null): boolean => this.clienteService.compareCliente(o1, o2);

  compareVeiculo = (o1: IVeiculo | null, o2: IVeiculo | null): boolean => this.veiculoService.compareVeiculo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contrato }) => {
      this.contrato = contrato;
      if (contrato) {
        this.updateForm(contrato);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contrato = this.contratoFormService.getContrato(this.editForm);
    if (contrato.id !== null) {
      this.subscribeToSaveResponse(this.contratoService.update(contrato));
    } else {
      this.subscribeToSaveResponse(this.contratoService.create(contrato));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContrato>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(contrato: IContrato): void {
    this.contrato = contrato;
    this.contratoFormService.resetForm(this.editForm, contrato);

    this.clientesCollection = this.clienteService.addClienteToCollectionIfMissing<ICliente>(this.clientesCollection, contrato.cliente);
    this.veiculosCollection = this.veiculoService.addVeiculoToCollectionIfMissing<IVeiculo>(this.veiculosCollection, contrato.veiculo);
  }

  protected loadRelationshipsOptions(): void {
    this.clienteService
      .query({ filter: 'contrato-is-null' })
      .pipe(map((res: HttpResponse<ICliente[]>) => res.body ?? []))
      .pipe(map((clientes: ICliente[]) => this.clienteService.addClienteToCollectionIfMissing<ICliente>(clientes, this.contrato?.cliente)))
      .subscribe((clientes: ICliente[]) => (this.clientesCollection = clientes));

    this.veiculoService
      .query({ filter: 'contrato-is-null' })
      .pipe(map((res: HttpResponse<IVeiculo[]>) => res.body ?? []))
      .pipe(map((veiculos: IVeiculo[]) => this.veiculoService.addVeiculoToCollectionIfMissing<IVeiculo>(veiculos, this.contrato?.veiculo)))
      .subscribe((veiculos: IVeiculo[]) => (this.veiculosCollection = veiculos));
  }
}
