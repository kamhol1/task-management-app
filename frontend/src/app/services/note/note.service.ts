import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {NoteModel} from "../../models/note.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  private apiUrl = 'http://localhost:8080/api/notes';

  constructor(private http: HttpClient) { }

  getNote(id: number): Observable<any> {
    return this.http.get(this.apiUrl + '/' + id);
  }

  saveNote(note: NoteModel): Observable<any> {
    return this.http.post(this.apiUrl, note);
  }

  updateNote(id: number, note: NoteModel): Observable<any> {
    return this.http.put(this.apiUrl + '/' + id, note);
  }

  deleteNote(id: number): Observable<any> {
    return this.http.delete(this.apiUrl + '/' + id);
  }
}
