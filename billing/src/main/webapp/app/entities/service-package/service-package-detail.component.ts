import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ServicePackage } from './service-package.model';
import { ServicePackageService } from './service-package.service';

@Component({
    selector: 'jhi-service-package-detail',
    templateUrl: './service-package-detail.component.html'
})
export class ServicePackageDetailComponent implements OnInit, OnDestroy {

    servicePackage: ServicePackage;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private servicePackageService: ServicePackageService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInServicePackages();
    }

    load(id) {
        this.servicePackageService.find(id)
            .subscribe((servicePackageResponse: HttpResponse<ServicePackage>) => {
                this.servicePackage = servicePackageResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInServicePackages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'servicePackageListModification',
            (response) => this.load(this.servicePackage.id)
        );
    }
}
