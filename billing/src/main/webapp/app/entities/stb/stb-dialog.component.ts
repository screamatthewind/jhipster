import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Stb } from './stb.model';
import { StbPopupService } from './stb-popup.service';
import { StbService } from './stb.service';
import { Customer, CustomerService } from '../customer';
import { Tariff, TariffService } from '../tariff';

@Component({
    selector: 'jhi-stb-dialog',
    templateUrl: './stb-dialog.component.html'
})
export class StbDialogComponent implements OnInit {

    stb: Stb;
    isSaving: boolean;

    customers: Customer[];

    tariffs: Tariff[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private stbService: StbService,
        private customerService: CustomerService,
        private tariffService: TariffService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.customerService.query()
            .subscribe((res: HttpResponse<Customer[]>) => { this.customers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tariffService.query()
            .subscribe((res: HttpResponse<Tariff[]>) => { this.tariffs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.stb.id !== undefined) {
            this.subscribeToSaveResponse(
                this.stbService.update(this.stb));
        } else {
            this.subscribeToSaveResponse(
                this.stbService.create(this.stb));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Stb>>) {
        result.subscribe((res: HttpResponse<Stb>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Stb) {
        this.eventManager.broadcast({ name: 'stbListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCustomerById(index: number, item: Customer) {
        return item.id;
    }

    trackTariffById(index: number, item: Tariff) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-stb-popup',
    template: ''
})
export class StbPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private stbPopupService: StbPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.stbPopupService
                    .open(StbDialogComponent as Component, params['id']);
            } else {
                this.stbPopupService
                    .open(StbDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
