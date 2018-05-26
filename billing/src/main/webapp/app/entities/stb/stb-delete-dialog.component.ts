import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Stb } from './stb.model';
import { StbPopupService } from './stb-popup.service';
import { StbService } from './stb.service';

@Component({
    selector: 'jhi-stb-delete-dialog',
    templateUrl: './stb-delete-dialog.component.html'
})
export class StbDeleteDialogComponent {

    stb: Stb;

    constructor(
        private stbService: StbService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stbService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'stbListModification',
                content: 'Deleted an stb'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-stb-delete-popup',
    template: ''
})
export class StbDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private stbPopupService: StbPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.stbPopupService
                .open(StbDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
