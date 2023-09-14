import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  getAuthToken(): string | null {
    return window.localStorage.getItem("auth_token");
  }

  setAuthToken(token: string | null): void {
    if (token !== null) {
      window.localStorage.setItem("auth_token", token);
    } else {
      window.localStorage.removeItem("auth_token")
    }
  }

  login(credentials: any): Observable<any> {
    return this.http.post("http://localhost:8080/login", credentials);
  }

  register(data: any): Observable<any> {
    return this.http.post("http://localhost:8080/register", data);
  }

  logout() {
    window.localStorage.removeItem("auth_token");
  }

  isLoggedIn(): boolean {
    return !!this.getAuthToken();
  }
}
