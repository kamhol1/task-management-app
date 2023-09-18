import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import jwt_decode from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  userData = {
    firstName: '',
    lastName: '',
    username: '',
    role: ''
  };

  constructor(private http: HttpClient) {
    this.userData.firstName = this.getJwtData().firstName;
    this.userData.lastName = this.getJwtData().lastName;
    this.userData.username = this.getJwtData().sub;
    this.userData.role = this.getJwtData().role;
  }

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

  getJwtData(): any {
    const token = this.getAuthToken();
    return token != null ? jwt_decode(token) : [];
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
