import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { RouterModule } from '@angular/router';
import { VERSION } from 'app/app.constants';
import SharedModule from 'app/shared/shared.module';

interface SideNavToggle {
  collapsed: boolean;
}

@Component({
  selector: 'jhi-navbar-vertical',
  standalone: true,
  imports: [RouterModule, SharedModule],
  templateUrl: './navbar-vertical.component.html',
  styleUrl: './navbar-vertical.component.scss',
})
export class NavbarVerticalComponent implements OnInit {
  version = '';

  @Output() toggleSideNav: EventEmitter<SideNavToggle> = new EventEmitter();
  collapsed = false;

  constructor() {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }

  ngOnInit(): void {
    const body = document.querySelector('body');
    const sidebar = body?.querySelector('.sidebar');
    const toggle = body?.querySelector('.toggle');
    const search = body?.querySelector('.search');
    const modeText = body?.querySelector('.mode-text');
    const modeSwitch = body?.querySelector('.toggle-switch');
    const contratos = body?.querySelector('.contratos');
    const submenuToggle = body?.querySelector('#submenu-toggle');
    const submenu = body?.querySelector('#submenu');
    const toggleIcon = body?.querySelector('#toggle-icon');

    if (modeSwitch) {
      modeSwitch.addEventListener('click', () => {
        sidebar?.classList.toggle('dark');

        if (sidebar?.classList.contains('dark')) {
          if (modeText) {
            modeText.innerHTML = 'Tema Escuro';
          }
        } else {
          if (modeText) {
            modeText.innerHTML = 'Tema Claro';
          }
        }
      });
    }
    if (toggle) {
      toggle.addEventListener('click', () => {
        sidebar?.classList.toggle('close');
        this.toggleCollapse();
      });
    }

    if (search) {
      search.addEventListener('click', () => {
        const sidebarIsOpen = sidebar?.classList.contains('close');

        if (sidebarIsOpen) {
          sidebar?.classList.remove('close');
          this.toggleCollapse();
        }
      });
    }
    if (contratos) {
      contratos.addEventListener('click', () => {
        const sidebarIsOpen = sidebar?.classList.contains('close');

        if (sidebarIsOpen) {
          sidebar?.classList.remove('close');
          this.toggleCollapse();
        }
      });
    }
    if (submenuToggle && submenu && toggleIcon) {
      submenuToggle.addEventListener('click', () => {
        submenu.classList.toggle('show-submenu');
        toggleIcon.classList.toggle('rotate-icon');
      });
    }
  }

  toggleCollapse(): void {
    this.collapsed = !this.collapsed;
    this.toggleSideNav.emit({ collapsed: this.collapsed });
  }
}
