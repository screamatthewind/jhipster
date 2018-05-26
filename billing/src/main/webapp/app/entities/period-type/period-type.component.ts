import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PeriodType } from './period-type.model';
import { PeriodTypeService } from './period-type.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-period-type',
    templateUrl: './period-type.component.html'
})
export class PeriodTypeComponent implements OnInit, OnDestroy {
periodTypes: PeriodType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private periodTypeService: PeriodTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.periodTypeService.query().subscribe(
            (res: HttpResponse<PeriodType[]>) => {
                this.periodTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPeriodTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PeriodType) {
        return item.id;
    }
    registerChangeInPeriodTypes() {
        this.eventSubscriber = this.eventManager.subscribe('periodTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
