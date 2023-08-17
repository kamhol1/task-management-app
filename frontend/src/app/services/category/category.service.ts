import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CategoryModel} from "../../models/category.model";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private apiUrl = 'http://localhost:8080/api/categories';

  constructor(private http: HttpClient) { }

  getNotHiddenCategories(): Observable<CategoryModel[]> {
    return this.http.get<CategoryModel[]>(this.apiUrl + '/active');
  }

  saveCategory(category: CategoryModel): Observable<any> {
    return this.http.post(this.apiUrl, category);
  }

  updateCategory(category: CategoryModel): Observable<any> {
    return this.http.put(this.apiUrl + '/' + category.id, category);
  }

  hideCategory(category: CategoryModel): Observable<any> {
    return this.http.patch(this.apiUrl + '/' + category.id + '/hide', category.id);
  }
}
