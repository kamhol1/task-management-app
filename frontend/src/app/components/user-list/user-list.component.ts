import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user/user.service";
import {UserModel} from "../../models/user.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: UserModel[] = [];

  constructor(private userService: UserService,
              private snackBar: MatSnackBar,
              private router: Router) {
  }

  ngOnInit(): void {
    this.userService.getUsers()
      .subscribe({
        next: users => {
          this.users = users;
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
      });
  }

  updateUser(user: UserModel) {
    const confirmUpdate = confirm('Are you sure you want to update the user #' + user.id + '?');

    if (confirmUpdate) {
      this.userService.updateUser(user)
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
            this.router.navigate(['./users'])
          },
          error: err => {
            this.snackBar.open(err.error.username,
              'OK',
              {
                verticalPosition: 'top',
                panelClass: ['app-notification-error'],
                duration: 5000
              });
          }
        })
    }
  }

  toggleUserEnabled(user: UserModel) {
    const message = user.enabled ?
      'Are you sure you want to disable the user "' + user.username + '"?' :
      'Are you sure you want to enable the user "' + user.username + '"?'

    const confirmToggle = confirm(message);

    if (confirmToggle) {
      this.userService.toggleUserEnabled(user.id)
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
            this.router.navigate(['./users'])
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
  }
}
