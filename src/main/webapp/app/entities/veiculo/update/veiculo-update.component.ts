import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { Classe } from 'app/entities/enumerations/classe.model';
import { Grupo } from 'app/entities/enumerations/grupo.model';
import { Situacao } from 'app/entities/enumerations/situacao.model';
import { VeiculoService } from '../service/veiculo.service';
import { IVeiculo } from '../veiculo.model';
import { VeiculoFormService, VeiculoFormGroup } from './veiculo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-veiculo-update',
  templateUrl: './veiculo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class VeiculoUpdateComponent implements OnInit {
  isSaving = false;
  veiculo: IVeiculo | null = null;
  classeValues = Object.keys(Classe);
  grupoValues = Object.keys(Grupo);
  situacaoValues = Object.keys(Situacao);

  editForm: VeiculoFormGroup = this.veiculoFormService.createVeiculoFormGroup();

  constructor(
    protected veiculoService: VeiculoService,
    protected veiculoFormService: VeiculoFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ veiculo }) => {
      this.veiculo = veiculo;
      if (veiculo) {
        this.updateForm(veiculo);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const veiculo = this.veiculoFormService.getVeiculo(this.editForm);
    if (veiculo.id !== null) {
      this.subscribeToSaveResponse(this.veiculoService.update(veiculo));
    } else {
      this.subscribeToSaveResponse(this.veiculoService.create(veiculo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVeiculo>>): void {
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

  protected updateForm(veiculo: IVeiculo): void {
    this.veiculo = veiculo;
    this.veiculoFormService.resetForm(this.editForm, veiculo);
  }
}
