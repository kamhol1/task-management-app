import {Component, OnInit} from '@angular/core';
import {TaskDetailsModel} from "../../models/task/task-details.model";
import {ActivatedRoute, ParamMap, Router} from "@angular/router";
import {TaskService} from "../../services/task/task.service";
import {NgForm} from "@angular/forms";
import {TaskWriteModel} from "../../models/task/task-write.model";
import {CategoryModel} from "../../models/category/category.model";
import {CategoryService} from "../../services/category/category.service";

@Component({
  selector: 'app-task-details',
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.css']
})
export class TaskDetailsComponent implements OnInit {
  task!: TaskDetailsModel;
  categories: CategoryModel[] = [];

  constructor(private taskService: TaskService,
              private categoryService: CategoryService,
              private route: ActivatedRoute,
              private router: Router) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe({
      next: (params: ParamMap) => {
        const id = params.get('id');
        if (id) this.getTask(id);
      }
    });

    this.categoryService.getCategories()
      .subscribe({
        next: categories => {
          this.categories = categories;
        },
        error: error => {
          console.log(error);
        }
      });
  }

  getTask(taskId: any): void {
    this.taskService.getTask(taskId)
      .subscribe({
        next: (task: TaskDetailsModel) => {
          this.task = task;
        },
        error: (error) => {
          console.log(error);
        }
      });
  }

  goToTaskList(): void {
    this.router.navigate(['/']);
  }

  onSubmit(form: NgForm) {
    if (form.valid) {
      const toUpdate: TaskWriteModel = {
        title: this.task.title,
        description: this.task.description,
        category: this.task.categoryId,
        status: this.task.status,
        priority: this.task.priority,
        targetTime: this.task.targetTime
      };

      this.taskService.updateTask(this.task.id, toUpdate)
        .subscribe({
          next: response => {
            console.log(response);
          },
          error: error => {
            console.log(error);
          }
        });
    }
  }
}
