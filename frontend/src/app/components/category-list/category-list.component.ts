import {Component, OnInit} from '@angular/core';
import {CategoryModel} from "../../models/category/category.model";
import {CategoryService} from "../../services/category/category.service";
import {CategoryWriteModel} from "../../models/category/category-write.model";

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {
  categories: CategoryModel[] = [];

  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.getCategories();
  }

  private getCategories() {
    this.categoryService.getCategories()
      .subscribe({
        next: categories => {
          this.categories = categories;
        },
        error: err => {
          console.log(err);
        }
      });
  }

  updateCategory(id: number, category: CategoryModel) {
    this.categoryService.updateCategory(id, category)
      .subscribe({
        next: res => {
          console.log(res);
          location.reload();
        },
        error: err => {
          console.log(err);
        }
      })
  }

  hideCategory(id: number) {
    const confirmDelete = confirm("Are you sure want to delete this category?");

    if (confirmDelete) {
      this.categoryService.hideCategory(id)
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
