import {Component} from '@angular/core';
import {CategoryService} from "../../services/category/category.service";
import {NgForm} from "@angular/forms";
import {CategoryWriteModel} from "../../models/category/category-write.model";

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.css']
})
export class CategoryFormComponent {
  category: CategoryWriteModel = {
    name: ''
  };

  constructor(private categoryService: CategoryService) { }

  onSubmit(form: NgForm) {
    if (form.valid) {
      this.categoryService.saveCategory(this.category)
        .subscribe({
          next: res => {
            console.log(res);
            location.reload();
          },
          error: err => {
            console.log(err);
          }
        });
    }
  }

}
