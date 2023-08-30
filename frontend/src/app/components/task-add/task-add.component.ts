import {Component, OnInit} from '@angular/core';
import {TaskService} from "../../services/task/task.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
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
  taskForm: FormGroup;
  submitted: boolean = false;
  errors = {
    title: '',
    category: '',
    priority: ''
  };
  categories: CategoryModel[] = [];

  constructor(private taskService: TaskService,
              private categoryService: CategoryService,
              private router: Router,
              private snackBar: MatSnackBar,
              private formBuilder: FormBuilder) {
    this.taskForm = this.formBuilder.group({
      title: ['', Validators.required],
      description: [''],
      category: [null, Validators.required],
      priority: [null, Validators.required],
      targetTime: ['']
    });
  }

  ngOnInit(): void {
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

  submitForm() {
    this.submitted = true;
    const formData = this.taskForm.value;

    const newTask: TaskModel = {
      id: 0,
      title: formData.title,
      description: formData.description,
      category: formData.category,
      priority: formData.priority,
      targetTime: formData.targetTime,
      status: 'NEW'
    };

    this.taskService.saveTask(newTask)
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
          this.errors = err.error;
        }
      });
  }

  goToTaskList(): void {
    this.router.navigate(['/']);
  }

}
