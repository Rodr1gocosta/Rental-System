import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ContratoDetailComponent } from './contrato-detail.component';

describe('Contrato Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContratoDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ContratoDetailComponent,
              resolve: { contrato: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ContratoDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load contrato on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ContratoDetailComponent);

      // THEN
      expect(instance.contrato).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
