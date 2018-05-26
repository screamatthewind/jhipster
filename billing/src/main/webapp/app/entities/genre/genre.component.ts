import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Genre } from './genre.model';
import { GenreService } from './genre.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-genre',
    templateUrl: './genre.component.html'
})
export class GenreComponent implements OnInit, OnDestroy {
genres: Genre[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private genreService: GenreService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.genreService.query().subscribe(
            (res: HttpResponse<Genre[]>) => {
                this.genres = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInGenres();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Genre) {
        return item.id;
    }
    registerChangeInGenres() {
        this.eventSubscriber = this.eventManager.subscribe('genreListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
