import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CategoryModel} from "../../models/category/category.model";
import {CategoryWriteModel} from "../../models/category/category-write.model";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private apiUrl = 'http://localhost:8080/api/categories';

  constructor(private http: HttpClient) { }

  getCategories(): Observable<CategoryModel[]> {
    return this.http.get<CategoryModel[]>(this.apiUrl);
  }

  saveCategory(category: CategoryWriteModel): Observable<CategoryModel> {
    return this.http.post<CategoryModel>(this.apiUrl, category);
  }

  updateCategory(id: number, category: CategoryWriteModel) {
    return this.http.put(this.apiUrl + '/' + id, category);
  }

  hideCategory(id: number) {
    return this.http.patch(this.apiUrl + '/' + id + '/hide', id);
  }
}
