import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ServicePackage } from './service-package.model';
import { ServicePackageService } from './service-package.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-service-package',
    templateUrl: './service-package.component.html'
})
export class ServicePackageComponent implements OnInit, OnDestroy {
servicePackages: ServicePackage[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private servicePackageService: ServicePackageService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.servicePackageService.query().subscribe(
            (res: HttpResponse<ServicePackage[]>) => {
                this.servicePackages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInServicePackages();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ServicePackage) {
        return item.id;
    }
    registerChangeInServicePackages() {
        this.eventSubscriber = this.eventManager.subscribe('servicePackageListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
