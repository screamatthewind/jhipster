import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PeriodType } from './period-type.model';
import { PeriodTypeService } from './period-type.service';

@Component({
    selector: 'jhi-period-type-detail',
    templateUrl: './period-type-detail.component.html'
})
export class PeriodTypeDetailComponent implements OnInit, OnDestroy {

    periodType: PeriodType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private periodTypeService: PeriodTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPeriodTypes();
    }

    load(id) {
        this.periodTypeService.find(id)
            .subscribe((periodTypeResponse: HttpResponse<PeriodType>) => {
                this.periodType = periodTypeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPeriodTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'periodTypeListModification',
            (response) => this.load(this.periodType.id)
        );
    }
}
