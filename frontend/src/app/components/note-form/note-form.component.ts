import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NoteService} from "../../services/note/note.service";
import {ActivatedRoute, ParamMap} from "@angular/router";
import {NoteModel} from "../../models/note.model";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-note-form',
  templateUrl: './note-form.component.html',
  styleUrls: ['./note-form.component.css']
})
export class NoteFormComponent implements OnInit {
  taskId!: number;
  noteForm: FormGroup;

  constructor(private noteService: NoteService,
              private route: ActivatedRoute,
              private snackBar: MatSnackBar,
              private formBuilder: FormBuilder) {
    this.noteForm = this.formBuilder.group({
      content: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe({
      next: (params: ParamMap) => {
        const id = params.get('id');
        if (id) this.taskId = parseInt(id);
      }
    });
  }

  submitForm() {
    const formData = this.noteForm.value;
    const newNote: NoteModel = {
      id: 0,
      content: formData.content,
      task: this.taskId,
      createdOn: ''
    };

    this.noteService.saveNote(newNote)
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
}
