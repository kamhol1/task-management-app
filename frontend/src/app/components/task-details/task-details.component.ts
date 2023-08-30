import {Component, OnInit} from '@angular/core';
import {TaskDetailsModel} from "../../models/task-details.model";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {TaskService} from "../../services/task/task.service";
import {FormBuilder, FormGroup, NgForm, Validators} from "@angular/forms";
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
  taskForm: FormGroup;
  categories: CategoryModel[] = [];
  submitted: boolean = false;
  errors = {
    title: '',
    category: '',
    priority: ''
  };

  constructor(private taskService: TaskService,
              private categoryService: CategoryService,
              private route: ActivatedRoute,
              private router: Router,
              private snackBar: MatSnackBar,
              private formBuilder: FormBuilder) {
    this.taskForm = this.formBuilder.group({
      title: '',
      description: '',
      category: 0,
      status: '',
      priority: '',
      targetTime: ''
    });
  }

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
          this.taskForm = this.formBuilder.group({
            title: [task.title, Validators.required],
            description: task.description,
            category: [task.categoryId, Validators.required],
            status: task.status,
            priority: [task.priority, Validators.required],
            targetTime: task.targetTime
          });
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

  submitForm() {
    this.submitted = true;

    const formData = this.taskForm.value;
    const toUpdate: TaskModel = {
      id: this.task.id,
      title: formData.title,
      description: formData.description,
      category: formData.category,
      status: formData.status,
      priority: formData.priority,
      targetTime: formData.targetTime
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
          this.errors = err.error;
        }
      });
  }
}
