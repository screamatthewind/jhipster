import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PeriodType } from './period-type.model';
import { PeriodTypePopupService } from './period-type-popup.service';
import { PeriodTypeService } from './period-type.service';

@Component({
    selector: 'jhi-period-type-dialog',
    templateUrl: './period-type-dialog.component.html'
})
export class PeriodTypeDialogComponent implements OnInit {

    periodType: PeriodType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private periodTypeService: PeriodTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.periodType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.periodTypeService.update(this.periodType));
        } else {
            this.subscribeToSaveResponse(
                this.periodTypeService.create(this.periodType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PeriodType>>) {
        result.subscribe((res: HttpResponse<PeriodType>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PeriodType) {
        this.eventManager.broadcast({ name: 'periodTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-period-type-popup',
    template: ''
})
export class PeriodTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private periodTypePopupService: PeriodTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.periodTypePopupService
                    .open(PeriodTypeDialogComponent as Component, params['id']);
            } else {
                this.periodTypePopupService
                    .open(PeriodTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
