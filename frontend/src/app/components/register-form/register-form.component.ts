import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth/auth.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent {
  registerForm: FormGroup;
  submitted: boolean = false;
  errors = {
    username: '',
    password: '',
    passwordConfirmation: ''
  };

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder,
              private snackBar: MatSnackBar) {
    this.registerForm = this.formBuilder.group({
      firstName: [''],
      lastName: [''],
      username: ['', [Validators.required, Validators.minLength]],
      password: ['', [Validators.required, Validators.minLength]],
      passwordConfirmation: ['', Validators.required]
    })
  }

  submitRegister(): void {
    this.submitted = true;
    const formData = this.registerForm.value;

    this.authService.register({
      firstName: formData.firstName,
      lastName: formData.lastName,
      username: formData.username,
      password: formData.password,
      passwordConfirmation: formData.passwordConfirmation
    }).subscribe({
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
