import { Injectable } from '@angular/core';
import {AuthService} from "../services/auth/auth.service";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor{

  constructor(private authService: AuthService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getAuthToken();
    let headers = {};

    if (token !== null) {
      headers = {"Authorization": "Bearer " + token};
    }

    request = request.clone({ setHeaders: headers });
    return next.handle(request);
  }
}
