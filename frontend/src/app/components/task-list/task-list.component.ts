import {Component, OnInit} from '@angular/core';
import {TaskModel} from "../../models/task.model";
import {TaskService} from "../../services/task/task.service";
import {Router} from "@angular/router";
import {debounceTime, distinctUntilChanged, Subject} from "rxjs";
import {TaskFilters} from "../../models/task.filters";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {
  tasks: TaskModel[] = [];
  page: number = 0;
  size: number = 10;
  totalPages!: number;
  totalElements!: number;
  numberOfElements!: number;
  offset!: number;
  sortField = "id";
  sortOrder = "desc";
  idFilter = "";
  titleFilter = "";
  statusFilter = "";
  priorityFilter = "";
  userFilter = "";
  searchTerms = new Subject<string>();

  constructor(private taskService: TaskService,
              private router: Router,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe({
      next: (filters: string) => {
        const parsedFilters: TaskFilters = JSON.parse(filters);
        this.getTasks(
          this.page,
          this.size,
          this.sortField,
          this.sortOrder,
          parsedFilters.idFilter,
          parsedFilters.titleFilter,
          parsedFilters.statusFilter,
          parsedFilters.priorityFilter,
          parsedFilters.userFilter
        );
      },
      error: err => {
        console.log(err);
      }
    });

    this.getTasks(
      this.page,
      this.size,
      this.sortField,
      this.sortOrder,
      this.idFilter,
      this.titleFilter,
      this.statusFilter,
      this.priorityFilter
    );
  }

  getTasks(
    page: number,
    size: number,
    sortField: string,
    sortOrder: string,
    idFilter?: string,
    titleFilter?: string,
    statusFilter?: string,
    priorityFilter?: string,
    userFilter?: string
  ) {
    this.taskService.getTasks(page, size, sortField, sortOrder, idFilter, titleFilter, statusFilter, priorityFilter, userFilter)
      .subscribe({
        next: data => {
          this.tasks = data.content;
          this.totalPages = data.totalPages;
          this.totalElements = data.totalElements;
          this.numberOfElements = data.numberOfElements;
          this.offset = data.pageable.offset;
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

  previousPage() {
    if (this.page > 0) {
      this.page--;
      this.getTasks(
        this.page,
        this.size,
        this.sortField,
        this.sortOrder,
        this.idFilter,
        this.titleFilter,
        this.statusFilter,
        this.priorityFilter,
        this.userFilter
      );
    }
  }

  nextPage() {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.getTasks(
        this.page,
        this.size,
        this.sortField,
        this.sortOrder,
        this.idFilter,
        this.titleFilter,
        this.statusFilter,
        this.priorityFilter,
        this.userFilter
      );
    }
  }

  goToTask(taskId: number) {
    this.router.navigate(['task', taskId])
  }

  sortData(sortField: string) {
    this.sortOrder == 'asc' && this.sortField == sortField ? this.sortOrder = 'desc' : this.sortOrder = 'asc';
    this.sortField = sortField;
    this.getTasks(
      this.page,
      this.size,
      this.sortField,
      this.sortOrder,
      this.idFilter,
      this.titleFilter,
      this.statusFilter,
      this.priorityFilter,
      this.userFilter
    );
  }

  filterTasks() {
    const filters: TaskFilters = {
      idFilter: this.idFilter,
      titleFilter: this.titleFilter,
      statusFilter: this.statusFilter,
      priorityFilter: this.priorityFilter,
      userFilter: this.userFilter
    };

    this.searchTerms.next(JSON.stringify(filters));
  }
}
