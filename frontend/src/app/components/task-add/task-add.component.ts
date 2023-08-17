import {Component, OnInit} from '@angular/core';
import {TaskService} from "../../services/task/task.service";
import {NgForm} from "@angular/forms";
import {Router} from "@angular/router";
import {CategoryService} from "../../services/category/category.service";
import {CategoryModel} from "../../models/category.model";
import {MatSnackBar} from "@angular/material/snack-bar";
import {TaskModel} from "../../models/task.model";

@Component({
  selector: 'app-task-add',
  templateUrl: './task-add.component.html',
  styleUrls: ['./task-add.component.css']
})
export class TaskAddComponent implements OnInit {
  task!: TaskModel;
  categories: CategoryModel[] = [];

  constructor(private taskService: TaskService,
              private categoryService: CategoryService,
              private router: Router,
              private snackBar: MatSnackBar) { }

  ngOnInit(): void {
    this.task = {
      id: 0,
      title: '',
      description: '',
      category: 0,
      status: 'NEW',
      priority: '',
      targetTime: ''
    };

    this.categoryService.getNotHiddenCategories()
      .subscribe({
        next: categories => {
          this.categories = categories;
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

  onSubmit(form: NgForm) {
    if (form.valid) {
      this.taskService.saveTask(this.task)
        .subscribe({
          next: res => {
            this.snackBar.open(res.message,
              'OK',
              {
                verticalPosition: 'top',
                panelClass: ['app-notification-success'],
                duration: 5000
              });
            this.goToTaskList();
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

  goToTaskList(): void {
    this.router.navigate(['/']);
  }

}
