import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IVeiculo } from '../veiculo.model';
import { VeiculoService } from '../service/veiculo.service';

export const veiculoResolve = (route: ActivatedRouteSnapshot): Observable<null | IVeiculo> => {
  const id = route.params['id'];
  if (id) {
    return inject(VeiculoService)
      .find(id)
      .pipe(
        mergeMap((veiculo: HttpResponse<IVeiculo>) => {
          if (veiculo.body) {
            return of(veiculo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default veiculoResolve;
