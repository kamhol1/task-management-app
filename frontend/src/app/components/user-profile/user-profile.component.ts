import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth/auth.service";
import {UserModel} from "../../models/user.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Observable} from "rxjs";
import {UserService} from "../../services/user/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  user: UserModel = {
    id: 0,
    username: '',
    firstName: '',
    lastName: '',
    role: '',
  };

  currentPassword: string = '';
  newPassword: string = '';
  newPasswordConfirm: string = '';

  constructor(private authService: AuthService,
              private userService: UserService,
              private snackBar: MatSnackBar,
              private router: Router) { }

  ngOnInit(): void {
    this.authService.getAuthenticatedUser()
      .subscribe({
        next: user => {
          this.user = user;
        },
        error: err => {
          this.snackBar.open(err.error.message,
            'OK',
            {
              verticalPosition: 'top',
              panelClass: ['app-notification-error'],
              duration: 5000
            });
        }
      })
  }

  changePassword(): void {
    this.userService.changePassword(this.user.id, this.currentPassword, this.newPassword, this.newPasswordConfirm)
      .subscribe({
        next: res => {
          this.snackBar.open(res.message,
            'OK',
            {
              verticalPosition: 'top',
              panelClass: ['app-notification-success'],
              duration: 5000
            });
          this.router.routeReuseStrategy.shouldReuseRoute = () => false;
          this.router.onSameUrlNavigation = 'reload';
          this.router.navigate(['./profile'])
        },
        error: err => {
          if (err.error.newPassword) {
            this.snackBar.open(err.error.newPassword,
              'OK',
              {
                verticalPosition: 'top',
                panelClass: ['app-notification-error'],
                duration: 5000
              });
          }
          else if (err.error.message) {
            this.snackBar.open(err.error.message,
              'OK',
              {
                verticalPosition: 'top',
                panelClass: ['app-notification-error'],
                duration: 5000
              });
          }
        }
      })
  }

}
