import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {TaskModel} from "../../models/task/task.model";
import {HttpClient, HttpParams} from "@angular/common/http";
import {TaskDetailsModel} from "../../models/task/task-details.model";
import {TaskWriteModel} from "../../models/task/task-write.model";

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private apiUrl = 'http://localhost:8080/api/tasks';

  constructor(private http: HttpClient) { }

  getTasks(page: number): Observable<any> {
    let params = new HttpParams()
      .set('page', page);

    return this.http.get<TaskModel[]>(this.apiUrl + '/active', { params });
  }

  getTask(taskId: number): Observable<TaskDetailsModel> {
    return this.http.get<TaskDetailsModel>(this.apiUrl + '/' + taskId);
  }

  saveTask(task: TaskWriteModel): Observable<any> {
    return this.http.post(this.apiUrl, task);
  }

  updateTask(taskId: number, task: TaskWriteModel): Observable<any> {
    return this.http.put(this.apiUrl + '/' + taskId, task)
  }
}
