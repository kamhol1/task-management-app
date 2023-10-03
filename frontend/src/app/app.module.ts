import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {TaskListComponent} from './components/task-list/task-list.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {StringReplacePipe} from "./pipes/string-replace.pipe";
import {TaskDetailsComponent} from './components/task-details/task-details.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NoteFormComponent} from './components/note-form/note-form.component';
import {NoteListComponent} from './components/note-list/note-list.component';
import {TaskAddComponent} from './components/task-add/task-add.component';
import {NavbarComponent} from "./components/navbar/navbar.component";
import {CategoryListComponent} from './components/category-list/category-list.component';
import {CategoryFormComponent} from './components/category-form/category-form.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {AuthComponent} from './components/auth/auth.component';
import {LoginFormComponent} from './components/login-form/login-form.component';
import {RegisterFormComponent} from './components/register-form/register-form.component';
import {TokenInterceptorService} from "./interceptors/token-interceptor.service";
import {UserListComponent} from './components/user-list/user-list.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';

@NgModule({
  declarations: [
    AppComponent,
    TaskListComponent,
    StringReplacePipe,
    TaskDetailsComponent,
    NoteFormComponent,
    NoteListComponent,
    TaskAddComponent,
    NavbarComponent,
    CategoryListComponent,
    CategoryFormComponent,
    AuthComponent,
    LoginFormComponent,
    RegisterFormComponent,
    UserListComponent,
    UserProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatSnackBarModule,
    ReactiveFormsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
