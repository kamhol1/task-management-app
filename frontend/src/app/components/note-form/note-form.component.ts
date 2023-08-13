import {Component, OnInit} from '@angular/core';
import {NgForm} from "@angular/forms";
import {NoteService} from "../../services/note/note.service";
import {NoteWriteModel} from "../../models/note/note-write.model";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";

@Component({
  selector: 'app-note-form',
  templateUrl: './note-form.component.html',
  styleUrls: ['./note-form.component.css']
})
export class NoteFormComponent implements OnInit {
  note: NoteWriteModel = {
    content: '',
    task: 0
  };

  constructor(private noteService: NoteService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe({
      next: (params: ParamMap) => {
        const id = params.get('id');
        if (id) this.note.task = parseInt(id);
      }
    });
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      this.noteService.saveNote(this.note)
        .subscribe({
          next: res => {
            console.log(res);
            location.reload(); // TODO: Refresh only note-list, not the whole page
          },
          error: err => {
            console.log(err);
          }
        });
    }
  }
}
