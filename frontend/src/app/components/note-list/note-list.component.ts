import {Component, Input} from '@angular/core';
import {NoteReadModel} from "../../models/note/note-read.model";

@Component({
  selector: 'app-note-list',
  templateUrl: './note-list.component.html',
  styleUrls: ['./note-list.component.css']
})
export class NoteListComponent {
  @Input()
  notes: NoteReadModel[] = [];
}
