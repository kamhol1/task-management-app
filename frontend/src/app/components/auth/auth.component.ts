import {Component, EventEmitter, Output} from '@angular/core';
import {AuthService} from "../../services/auth/auth.service";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent {

  loggedIn: boolean = false;

  constructor(private authService: AuthService) {
    this.loggedIn = authService.isLoggedIn();
  }

  logout() {
    this.authService.logout();
    window.location.href = '/login';
  }
}
