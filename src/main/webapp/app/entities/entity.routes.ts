import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'veiculo',
    data: { pageTitle: 'Veiculos' },
    loadChildren: () => import('./veiculo/veiculo.routes'),
  },
  {
    path: 'cliente',
    data: { pageTitle: 'Clientes' },
    loadChildren: () => import('./cliente/cliente.routes'),
  },
  {
    path: 'endereco',
    data: { pageTitle: 'Enderecos' },
    loadChildren: () => import('./endereco/endereco.routes'),
  },
  {
    path: 'contrato',
    data: { pageTitle: 'Contratoes' },
    loadChildren: () => import('./contrato/contrato.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
