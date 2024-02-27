import { Component, OnInit } from '@angular/core';
import { RouterOutlet, Router } from '@angular/router';

import { AccountService } from 'app/core/auth/account.service';
import { AppPageTitleStrategy } from 'app/app-page-title-strategy';
import FooterComponent from '../footer/footer.component';
import PageRibbonComponent from '../profiles/page-ribbon.component';
import { NavbarVerticalComponent } from '../navbar-vertical/navbar-vertical.component';
import { CommonModule } from '@angular/common';
import { Account } from 'app/core/auth/account.model';

interface SideNavToggle {
  collapsed: boolean;
}

@Component({
  selector: 'jhi-main',
  standalone: true,
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss',
  providers: [AppPageTitleStrategy],
  imports: [RouterOutlet, FooterComponent, NavbarVerticalComponent, PageRibbonComponent, CommonModule],
})
export default class MainComponent implements OnInit {
  account: Account | null = null;
  collapsed = false;

  constructor(
    private router: Router,
    private appPageTitleStrategy: AppPageTitleStrategy,
    private accountService: AccountService,
  ) {}

  ngOnInit(): void {
    // try to log in automatically
    this.accountService.identity().subscribe();
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
  }

  toggleSideNav(data: SideNavToggle): void {
    this.collapsed = data.collapsed;
    if (this.collapsed) {
      document.body.classList.add('body-trimmed');
    } else {
      document.body.classList.remove('body-trimmed');
    }
  }
}
