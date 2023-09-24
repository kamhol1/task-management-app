import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserModel} from "../../models/user.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) { }

  getUsersAsListItems(): Observable<UserModel[]> {
    return this.http.get<UserModel[]>(this.apiUrl + '/list');
  }

  getUsers(): Observable<UserModel[]> {
    return this.http.get<UserModel[]>(this.apiUrl);
  }

  updateUser(user: UserModel): Observable<any> {
    return this.http.put(this.apiUrl + '/' + user.id, user);
  }

  toggleUserEnabled(id: number): Observable<any> {
    return this.http.patch(this.apiUrl + '/' + id + '/toggle', id)
  }
}
