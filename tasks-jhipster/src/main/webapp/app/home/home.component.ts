import {Component, OnInit} from '@angular/core';
import {Post} from '../entities/post/post.model';
import {PostService} from '../entities/post/post.service';
import {HttpResponse, HttpErrorResponse} from '@angular/common/http';
import {JhiAlertService} from 'ng-jhipster';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: [
    'home.css'
  ]

})

export class HomeComponent implements OnInit {

  posts: Post[];

  constructor(
    private postService: PostService,
    private jhiAlertService: JhiAlertService
  ) {}

  loadAll() {
    this.postService.query().subscribe(
      (res: HttpResponse<Post[]>) => {
        this.posts = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }

  ngOnInit() {
    this.loadAll();
  }
}
