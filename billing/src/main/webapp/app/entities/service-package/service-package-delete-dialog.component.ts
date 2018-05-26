import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ServicePackage } from './service-package.model';
import { ServicePackagePopupService } from './service-package-popup.service';
import { ServicePackageService } from './service-package.service';

@Component({
    selector: 'jhi-service-package-delete-dialog',
    templateUrl: './service-package-delete-dialog.component.html'
})
export class ServicePackageDeleteDialogComponent {

    servicePackage: ServicePackage;

    constructor(
        private servicePackageService: ServicePackageService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.servicePackageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'servicePackageListModification',
                content: 'Deleted an servicePackage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-service-package-delete-popup',
    template: ''
})
export class ServicePackageDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private servicePackagePopupService: ServicePackagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.servicePackagePopupService
                .open(ServicePackageDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
