import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Tariff } from './tariff.model';
import { TariffPopupService } from './tariff-popup.service';
import { TariffService } from './tariff.service';
import { PeriodType, PeriodTypeService } from '../period-type';
import { ServicePackage, ServicePackageService } from '../service-package';

@Component({
    selector: 'jhi-tariff-dialog',
    templateUrl: './tariff-dialog.component.html'
})
export class TariffDialogComponent implements OnInit {

    tariff: Tariff;
    isSaving: boolean;

    periodtypes: PeriodType[];

    servicepackages: ServicePackage[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private tariffService: TariffService,
        private periodTypeService: PeriodTypeService,
        private servicePackageService: ServicePackageService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.periodTypeService.query()
            .subscribe((res: HttpResponse<PeriodType[]>) => { this.periodtypes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.servicePackageService.query()
            .subscribe((res: HttpResponse<ServicePackage[]>) => { this.servicepackages = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tariff.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tariffService.update(this.tariff));
        } else {
            this.subscribeToSaveResponse(
                this.tariffService.create(this.tariff));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Tariff>>) {
        result.subscribe((res: HttpResponse<Tariff>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Tariff) {
        this.eventManager.broadcast({ name: 'tariffListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPeriodTypeById(index: number, item: PeriodType) {
        return item.id;
    }

    trackServicePackageById(index: number, item: ServicePackage) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-tariff-popup',
    template: ''
})
export class TariffPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tariffPopupService: TariffPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tariffPopupService
                    .open(TariffDialogComponent as Component, params['id']);
            } else {
                this.tariffPopupService
                    .open(TariffDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
