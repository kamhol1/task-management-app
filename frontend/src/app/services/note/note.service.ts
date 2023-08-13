import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {NoteReadModel} from "../../models/note/note-read.model";
import {NoteWriteModel} from "../../models/note/note-write.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  private apiUrl = 'http://localhost:8080/api/notes';

  constructor(private http: HttpClient) { }

  saveNote(note: NoteWriteModel): Observable<NoteWriteModel> {
    return this.http.post<NoteWriteModel>(this.apiUrl, note);
  }
}
