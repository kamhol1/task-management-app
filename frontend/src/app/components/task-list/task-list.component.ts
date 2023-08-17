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


  constructor(private taskService: TaskService,
              private router: Router) {}

  ngOnInit(): void {
    this.getTasks(this.page);
  }

  getTasks(page: number) {
    this.taskService.getTasks(page)
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
      this.getTasks(this.page);
    }
  }

  nextPage() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.getTasks(this.page);
    }
  }

  goToTask(taskId: number) {
    this.router.navigate(['task', taskId])
  }
}
