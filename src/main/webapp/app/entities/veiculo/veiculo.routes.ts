import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { VeiculoComponent } from './list/veiculo.component';
import { VeiculoDetailComponent } from './detail/veiculo-detail.component';
import { VeiculoUpdateComponent } from './update/veiculo-update.component';
import VeiculoResolve from './route/veiculo-routing-resolve.service';

const veiculoRoute: Routes = [
  {
    path: '',
    component: VeiculoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: VeiculoDetailComponent,
    resolve: {
      veiculo: VeiculoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: VeiculoUpdateComponent,
    resolve: {
      veiculo: VeiculoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: VeiculoUpdateComponent,
    resolve: {
      veiculo: VeiculoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default veiculoRoute;
