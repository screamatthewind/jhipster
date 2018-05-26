import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Template } from './template.model';
import { TemplatePopupService } from './template-popup.service';
import { TemplateService } from './template.service';

@Component({
    selector: 'jhi-template-delete-dialog',
    templateUrl: './template-delete-dialog.component.html'
})
export class TemplateDeleteDialogComponent {

    template: Template;

    constructor(
        private templateService: TemplateService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.templateService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'templateListModification',
                content: 'Deleted an template'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-template-delete-popup',
    template: ''
})
export class TemplateDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private templatePopupService: TemplatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.templatePopupService
                .open(TemplateDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
