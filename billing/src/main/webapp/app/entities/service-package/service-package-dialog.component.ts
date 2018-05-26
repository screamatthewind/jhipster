import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ServicePackage } from './service-package.model';
import { ServicePackagePopupService } from './service-package-popup.service';
import { ServicePackageService } from './service-package.service';
import { Channel, ChannelService } from '../channel';

@Component({
    selector: 'jhi-service-package-dialog',
    templateUrl: './service-package-dialog.component.html'
})
export class ServicePackageDialogComponent implements OnInit {

    servicePackage: ServicePackage;
    isSaving: boolean;

    channels: Channel[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private servicePackageService: ServicePackageService,
        private channelService: ChannelService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.channelService.query()
            .subscribe((res: HttpResponse<Channel[]>) => { this.channels = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.servicePackage.id !== undefined) {
            this.subscribeToSaveResponse(
                this.servicePackageService.update(this.servicePackage));
        } else {
            this.subscribeToSaveResponse(
                this.servicePackageService.create(this.servicePackage));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ServicePackage>>) {
        result.subscribe((res: HttpResponse<ServicePackage>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ServicePackage) {
        this.eventManager.broadcast({ name: 'servicePackageListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackChannelById(index: number, item: Channel) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-service-package-popup',
    template: ''
})
export class ServicePackagePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private servicePackagePopupService: ServicePackagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.servicePackagePopupService
                    .open(ServicePackageDialogComponent as Component, params['id']);
            } else {
                this.servicePackagePopupService
                    .open(ServicePackageDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
