import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarVerticalComponent } from './navbar-vertical.component';

describe('NavbarVerticalComponent', () => {
  let component: NavbarVerticalComponent;
  let fixture: ComponentFixture<NavbarVerticalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavbarVerticalComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(NavbarVerticalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // Teste que será ignorado
  xit('should create', () => {
    expect(component).toBeTruthy();
  });
});
