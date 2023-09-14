import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth/auth.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {

  loginForm: FormGroup;
  submitted: boolean = false;
  errors = {
    username: '',
    password: ''
  };

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder,
              private snackBar: MatSnackBar) {
    if (authService.isLoggedIn()) {
      window.location.href = '/';
    }

    this.loginForm = this.formBuilder.group({
      username: ["", Validators.required],
      password: ["", Validators.required]
    })
  }

  submitLogin(): void {
    this.submitted = true;
    const formData = this.loginForm.value;

    this.authService.login({username: formData.username, password: formData.password})
      .subscribe({
        next: res => {
          this.authService.setAuthToken(res.token);
          window.location.href = '/';
        },
        error: err => {
          this.errors = err.error;
          if (err.error.message) {
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
