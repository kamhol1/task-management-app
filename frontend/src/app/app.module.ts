import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TaskListComponent } from './components/task-list/task-list.component';
import {HttpClientModule} from "@angular/common/http";
import {StringReplacePipe} from "./pipes/string-replace.pipe";
import { TaskDetailsComponent } from './components/task-details/task-details.component';
import {FormsModule} from "@angular/forms";
import { NoteFormComponent } from './components/note-form/note-form.component';
import { NoteListComponent } from './components/note-list/note-list.component';
import { TaskAddComponent } from './components/task-add/task-add.component';
import {NavbarComponent} from "./components/navbar/navbar.component";
import { CategoryListComponent } from './components/category-list/category-list.component';
import { CategoryFormComponent } from './components/category-form/category-form.component';

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
    CategoryFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
