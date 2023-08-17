import {Component, OnInit} from '@angular/core';
import {CategoryService} from "../../services/category/category.service";
import {NgForm} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {CategoryModel} from "../../models/category.model";

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.css']
})
export class CategoryFormComponent {
  category: CategoryModel = {
    id: 0,
    name: ''
  };

  constructor(private categoryService: CategoryService,
              private snackBar: MatSnackBar) { }

  onSubmit(form: NgForm) {
    if (form.valid) {
      this.categoryService.saveCategory(this.category)
        .subscribe({
          next: res => {
            this.snackBar.open(res.message,
              'OK',
              {
                verticalPosition: 'top',
                panelClass: ['app-notification-success'],
                duration: 5000
              });
            location.reload();
          },
          error: err => {
            console.log(err);
          }
        });
    }
  }

}
