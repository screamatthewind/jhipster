import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PeriodType } from './period-type.model';
import { PeriodTypePopupService } from './period-type-popup.service';
import { PeriodTypeService } from './period-type.service';

@Component({
    selector: 'jhi-period-type-delete-dialog',
    templateUrl: './period-type-delete-dialog.component.html'
})
export class PeriodTypeDeleteDialogComponent {

    periodType: PeriodType;

    constructor(
        private periodTypeService: PeriodTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.periodTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'periodTypeListModification',
                content: 'Deleted an periodType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-period-type-delete-popup',
    template: ''
})
export class PeriodTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private periodTypePopupService: PeriodTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.periodTypePopupService
                .open(PeriodTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
