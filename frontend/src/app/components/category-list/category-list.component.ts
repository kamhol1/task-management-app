import {Component, OnInit} from '@angular/core';
import {CategoryModel} from "../../models/category.model";
import {CategoryService} from "../../services/category/category.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {
  categories: CategoryModel[] = [];

  constructor(private categoryService: CategoryService,
              private snackBar: MatSnackBar,
              private router: Router) { }

  ngOnInit(): void {
    this.getCategories();
  }

  private getCategories() {
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

  updateCategory(category: CategoryModel) {
    this.categoryService.updateCategory(category)
      .subscribe({
        next: res => {
          this.snackBar.open(res.message,
            'OK',
            {
              verticalPosition: 'top',
              panelClass: ['app-notification-success'],
              duration: 5000
            });
          this.router.routeReuseStrategy.shouldReuseRoute = () => false;
          this.router.onSameUrlNavigation = 'reload';
          this.router.navigate(['./categories'])
        },
        error: err => {
          this.snackBar.open(err.error.name,
            'OK',
            {
              verticalPosition: 'top',
              panelClass: ['app-notification-error'],
              duration: 5000
            });
        }
      })
  }

  hideCategory(category: CategoryModel) {
    const confirmDelete = confirm('Are you sure you want to delete "' + category.name + '" category?');

    if (confirmDelete) {
      this.categoryService.hideCategory(category)
        .subscribe({
          next: res => {
            this.snackBar.open(res.message,
              'OK',
              {
                verticalPosition: 'top',
                panelClass: ['app-notification-success'],
                duration: 5000
              });
            this.router.routeReuseStrategy.shouldReuseRoute = () => false;
            this.router.onSameUrlNavigation = 'reload';
            this.router.navigate(['./categories'])
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
