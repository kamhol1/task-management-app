import {Component} from '@angular/core';
import {AuthService} from "../../services/auth/auth.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  loggedIn: boolean = false;
  admin: boolean = false;

  constructor(private authService: AuthService) {
    this.admin = authService.userData.role == 'ADMIN';
    this.loggedIn = authService.isLoggedIn();
  }

  isLoggedIn(): boolean {
    return this.loggedIn;
  }

  isAdmin(): boolean {
    return this.admin;
  }
}
