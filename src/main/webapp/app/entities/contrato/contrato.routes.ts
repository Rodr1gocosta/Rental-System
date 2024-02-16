import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ContratoComponent } from './list/contrato.component';
import { ContratoDetailComponent } from './detail/contrato-detail.component';
import { ContratoUpdateComponent } from './update/contrato-update.component';
import ContratoResolve from './route/contrato-routing-resolve.service';

const contratoRoute: Routes = [
  {
    path: '',
    component: ContratoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContratoDetailComponent,
    resolve: {
      contrato: ContratoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContratoUpdateComponent,
    resolve: {
      contrato: ContratoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContratoUpdateComponent,
    resolve: {
      contrato: ContratoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default contratoRoute;
