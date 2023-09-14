import {Component} from '@angular/core';
import {CategoryService} from "../../services/category/category.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {CategoryModel} from "../../models/category.model";

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.css']
})
export class CategoryFormComponent {
  categoryForm: FormGroup;
  submitted: boolean = false;
  errors = {
    name: ''
  };

  constructor(private categoryService: CategoryService,
              private snackBar: MatSnackBar,
              private formBuilder: FormBuilder) {
    this.categoryForm = this.formBuilder.group({
      name: ['', Validators.required]
    });
  }

  submitForm() {
    this.submitted = true;
    const formData = this.categoryForm.value;

    const newCategory: CategoryModel = {
      id: 0,
      name: formData.name
    };

    this.categoryService.saveCategory(newCategory)
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
          this.errors = err.error;
        }
      });
  }

}
