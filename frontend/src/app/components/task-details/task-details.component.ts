import {Component, OnInit} from '@angular/core';
import {TaskDetailsModel} from "../../models/task-details.model";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {TaskService} from "../../services/task/task.service";
import {NgForm} from "@angular/forms";
import {CategoryModel} from "../../models/category.model";
import {CategoryService} from "../../services/category/category.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {TaskModel} from "../../models/task.model";

@Component({
  selector: 'app-task-details',
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.css']
})
export class TaskDetailsComponent implements OnInit {
  task: TaskDetailsModel = {
    id: 0,
    title: '',
    description: '',
    categoryId: 0,
    categoryName: '',
    status: '',
    priority: '',
    targetTime: '',
    createdOn: '',
    notes: []
  };
  categories: CategoryModel[] = [];

  constructor(private taskService: TaskService,
              private categoryService: CategoryService,
              private route: ActivatedRoute,
              private router: Router,
              private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe({
      next: (params: ParamMap) => {
        const id = params.get('id');
        if (id) this.getTask(id);
      }
    });

    this.categoryService.getNotHiddenCategories()
      .subscribe({
        next: categories => {
          this.categories = categories;
        },
        error: err => {
          console.log(err);
        }
      });
  }

  getTask(taskId: any): void {
    this.taskService.getTask(taskId)
      .subscribe({
        next: (task: TaskDetailsModel) => {
          this.task = task;
        },
        error: (err) => {
          this.snackBar.open(err.error.message,
            'OK',
            {
              verticalPosition: 'top',
              panelClass: ['app-notification-error'],
              duration: 5000
            });
          this.goToTaskList();
        }
      });
  }

  goToTaskList(): void {
    this.router.navigate(['/']);
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      const toUpdate: TaskModel = {
        id: this.task.id,
        title: this.task.title,
        description: this.task.description,
        category: this.task.categoryId,
        status: this.task.status,
        priority: this.task.priority,
        targetTime: this.task.targetTime
      };

      this.taskService.updateTask(this.task.id, toUpdate)
        .subscribe({
          next: res => {
            this.snackBar.open(res.message,
              'OK',
              {
                verticalPosition: 'top',
                panelClass: ['app-notification-success'],
                duration: 5000
              });
          },
          error: err => {
            this.snackBar.open(err.error.message,
              'OK',
              {
                verticalPosition: 'top',
                panelClass: ['app-notification-error'],
                duration: 5000
              });
          }
        });
    }
  }
}
