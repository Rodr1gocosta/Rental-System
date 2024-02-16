import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContrato } from '../contrato.model';
import { ContratoService } from '../service/contrato.service';

export const contratoResolve = (route: ActivatedRouteSnapshot): Observable<null | IContrato> => {
  const id = route.params['id'];
  if (id) {
    return inject(ContratoService)
      .find(id)
      .pipe(
        mergeMap((contrato: HttpResponse<IContrato>) => {
          if (contrato.body) {
            return of(contrato.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default contratoResolve;
