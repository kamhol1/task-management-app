import {Component, OnInit} from '@angular/core';
import {TaskService} from "../../services/task/task.service";
import {TaskWriteModel} from "../../models/task/task-write.model";
import {NgForm} from "@angular/forms";
import {Router} from "@angular/router";
import {CategoryService} from "../../services/category/category.service";
import {CategoryModel} from "../../models/category/category.model";

@Component({
  selector: 'app-task-add',
  templateUrl: './task-add.component.html',
  styleUrls: ['./task-add.component.css']
})
export class TaskAddComponent implements OnInit {
  task!: TaskWriteModel;
  categories: CategoryModel[] = [];

  constructor(private taskService: TaskService,
              private categoryService: CategoryService,
              private router: Router) { }

  ngOnInit(): void {
    this.task = {
      title: "",
      description: "",
      category: 0,
      status: "NEW",
      priority: "",
      targetTime: ""
    };

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

  onSubmit(form: NgForm) {
    if (form.valid) {
      this.taskService.saveTask(this.task)
        .subscribe({
          next: res => {
            console.log(res);
            this.goToTaskList();
          },
          error: err => {
            console.log(err);
          }
        });
    }
  }

  goToTaskList(): void {
    this.router.navigate(['/']);
  }

}
