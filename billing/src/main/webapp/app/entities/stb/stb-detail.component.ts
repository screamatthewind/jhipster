import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Stb } from './stb.model';
import { StbService } from './stb.service';

@Component({
    selector: 'jhi-stb-detail',
    templateUrl: './stb-detail.component.html'
})
export class StbDetailComponent implements OnInit, OnDestroy {

    stb: Stb;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private stbService: StbService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStbs();
    }

    load(id) {
        this.stbService.find(id)
            .subscribe((stbResponse: HttpResponse<Stb>) => {
                this.stb = stbResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStbs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'stbListModification',
            (response) => this.load(this.stb.id)
        );
    }
}
