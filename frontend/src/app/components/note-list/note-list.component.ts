import {Component, Input} from '@angular/core';
import {NoteModel} from "../../models/note.model";
import {NgForm} from "@angular/forms";
import {NoteService} from "../../services/note/note.service";

@Component({
  selector: 'app-note-list',
  templateUrl: './note-list.component.html',
  styleUrls: ['./note-list.component.css']
})
export class NoteListComponent {
  @Input()
  notes: NoteModel[] = [];
  editMode: boolean = false;
  note: NoteModel = {
    id: 0,
    content: '',
    task: 0,
    createdOn: ''
  };

  constructor(private noteService: NoteService) { }

  editNote(id: number) {
    this.editMode = true;

    this.noteService.getNote(id)
      .subscribe({
        next: note => {
          this.note = note;
        },
        error: err => {
          console.log(err);
        }
      });
  }

  listNotes() {
    this.editMode = false;
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      this.noteService.updateNote(this.note.id, this.note)
        .subscribe({
          next: res => {
            console.log(res);
            location.reload();
          },
          error: err => {
            console.log(err);
          }
        });
    }
  }
}
