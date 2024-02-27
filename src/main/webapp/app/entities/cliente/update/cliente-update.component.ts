import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEndereco } from 'app/entities/endereco/endereco.model';
import { EnderecoService } from 'app/entities/endereco/service/endereco.service';
import { ICliente } from '../cliente.model';
import { ClienteService } from '../service/cliente.service';
import { ClienteFormService, ClienteFormGroup } from './cliente-form.service';
import dayjs from 'dayjs/esm';

@Component({
  standalone: true,
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html',
  styleUrl: './cliente-update.component.scss',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ClienteUpdateComponent implements OnInit {
  isSaving = false;
  cliente: ICliente | null = null;

  enderecosCollection: IEndereco[] = [];

  editForm: ClienteFormGroup = this.clienteFormService.createClienteFormGroup();

  constructor(
    protected clienteService: ClienteService,
    protected clienteFormService: ClienteFormService,
    protected enderecoService: EnderecoService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareEndereco = (o1: IEndereco | null, o2: IEndereco | null): boolean => this.enderecoService.compareEndereco(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.cliente = cliente;
      if (cliente) {
        this.updateForm(cliente);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cliente = this.clienteFormService.getCliente(this.editForm);

    cliente.estadoEmissaoHabilitacao = cliente.estadoEmissaoHabilitacao ? this.convertToDayjs(cliente.estadoEmissaoHabilitacao) : null;
    cliente.validadeHabilitacao = cliente.validadeHabilitacao ? this.convertToDayjs(cliente.validadeHabilitacao) : null;

    if (cliente.id !== null) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  errorRequiredField(controlName: string): string {
    const control = this.editForm.get(controlName);

    if (control?.hasError('required')) {
      return 'Campo é obrigatório!';
    }
    return 'Campo inválido!';
  }

  errorRequiredFieldEndereco(controlName: string): string {
    const enderecoControl = this.editForm.get('endereco')?.get(controlName);

    if (enderecoControl?.hasError('required')) {
      return 'Campo é obrigatório!';
    }
    return 'Campo inválido!';
  }

  errorValidEmail(): string | boolean {
    if (this.editForm.get('email')?.hasError('required')) {
      return 'Campo obrigatório!';
    } else if (this.editForm.get('email')?.hasError('pattern')) {
      return 'E-mail inválido';
    }
    return false;
  }

  errorValidNHabilitacao(): string {
    const nHabilidacaoControl = this.editForm.get('nHabilidacao');

    if (nHabilidacaoControl?.hasError('required')) {
      return 'Campo obrigatório!';
    } else if (nHabilidacaoControl?.hasError('pattern')) {
      return 'O número da CNH deve conter exatamente 11 dígitos.';
    }
    return 'Campo Inválido!';
  }

  convertToDayjs(date: any): dayjs.Dayjs {
    return dayjs(date);
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>): void {
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

  protected updateForm(cliente: ICliente): void {
    this.cliente = cliente;
    this.clienteFormService.resetForm(this.editForm, cliente);

    this.enderecosCollection = this.enderecoService.addEnderecoToCollectionIfMissing<IEndereco>(this.enderecosCollection, cliente.endereco);
  }

  protected loadRelationshipsOptions(): void {
    this.enderecoService
      .query({ filter: 'cliente-is-null' })
      .pipe(map((res: HttpResponse<IEndereco[]>) => res.body ?? []))
      .pipe(
        map((enderecos: IEndereco[]) =>
          this.enderecoService.addEnderecoToCollectionIfMissing<IEndereco>(enderecos, this.cliente?.endereco),
        ),
      )
      .subscribe((enderecos: IEndereco[]) => (this.enderecosCollection = enderecos));
  }
}
