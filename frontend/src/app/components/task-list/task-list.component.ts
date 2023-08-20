import {Component, OnInit} from '@angular/core';
import {TaskModel} from "../../models/task.model";
import {TaskService} from "../../services/task/task.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  tasks: TaskModel[] = [];
  page: number = 0;
  size: number = 10;
  totalPages!: number;
  totalElements!: number;
  numberOfElements!: number;
  offset!: number;
  sortField = "id";
  sortOrder = "desc";

  constructor(private taskService: TaskService,
              private router: Router) {}

  ngOnInit(): void {
    this.getTasks(this.page, this.size, this.sortField, this.sortOrder);
  }

  getTasks(page: number, size: number, sortField: string, sortOrder: string) {
    this.taskService.getTasks(page, size, sortField, sortOrder)
      .subscribe({
      next: (data) => {
        this.tasks = data.content;
        this.totalPages = data.totalPages;
        this.totalElements = data.totalElements;
        this.numberOfElements = data.numberOfElements;
        this.offset = data.pageable.offset;
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
      this.getTasks(this.page, this.size, this.sortField, this.sortOrder);
    }
  }

  nextPage() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.getTasks(this.page, this.size, this.sortField, this.sortOrder);
    }
  }

  goToTask(taskId: number) {
    this.router.navigate(['task', taskId])
  }

  sortData(sortField: string) {
    this.sortOrder == 'asc' && this.sortField == sortField ? this.sortOrder = 'desc' : this.sortOrder = 'asc';
    this.sortField = sortField;
    this.getTasks(this.page, this.size, this.sortField, this.sortOrder);
  }
}
