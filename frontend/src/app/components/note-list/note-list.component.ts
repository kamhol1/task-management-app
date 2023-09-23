import {Component, Input} from '@angular/core';
import {NoteModel} from "../../models/note.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NoteService} from "../../services/note/note.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {AuthService} from "../../services/auth/auth.service";

@Component({
  selector: 'app-note-list',
  templateUrl: './note-list.component.html',
  styleUrls: ['./note-list.component.css']
})
export class NoteListComponent {
  @Input()
  notes: NoteModel[] = [];
  editMode: boolean = false;
  isOwner: boolean = false;
  noteForm!: FormGroup;
  note!: NoteModel;

  constructor(private noteService: NoteService,
              private authService: AuthService,
              private snackBar: MatSnackBar,
              private formBuilder: FormBuilder) {
    this.noteForm = this.formBuilder.group({
      content: ''
    });
  }

  editNote(id: number) {
    this.editMode = true;

    this.noteService.getNote(id)
      .subscribe({
        next: note => {
          this.note = note;
          this.isOwner = this.note.username == this.authService.userData.username;

          this.noteForm = this.formBuilder.group({
            content: [note.content, Validators.required]
          });
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

  listNotes() {
    this.editMode = false;
  }

  submitForm() {
    const formData = this.noteForm.value;
    this.note.content = formData.content;

    this.noteService.updateNote(this.note.id, this.note)
      .subscribe({
        next: () => {
          location.reload();
        },
        error: err => {
          this.snackBar.open(err.error.content,
            'OK',
            {
              verticalPosition: 'top',
              panelClass: ['app-notification-error'],
              duration: 5000
            });
        }
      });
  }

  deleteNote(id: number) {
    const confirmDelete = confirm('Are you sure you want to delete this note?');

    if (confirmDelete) {
      this.noteService.deleteNote(id)
        .subscribe({
          next: res => {
            this.snackBar.open(res.message,
              'OK',
              {
                verticalPosition: 'top',
                panelClass: ['app-notification-success'],
                duration: 5000
              });
            location.reload();
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
  }
}
