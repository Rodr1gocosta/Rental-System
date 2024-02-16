import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { VeiculoDetailComponent } from './veiculo-detail.component';

describe('Veiculo Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VeiculoDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: VeiculoDetailComponent,
              resolve: { veiculo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(VeiculoDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load veiculo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', VeiculoDetailComponent);

      // THEN
      expect(instance.veiculo).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
