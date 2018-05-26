import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Template } from './template.model';
import { TemplatePopupService } from './template-popup.service';
import { TemplateService } from './template.service';

@Component({
    selector: 'jhi-template-dialog',
    templateUrl: './template-dialog.component.html'
})
export class TemplateDialogComponent implements OnInit {

    template: Template;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private templateService: TemplateService,
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
        if (this.template.id !== undefined) {
            this.subscribeToSaveResponse(
                this.templateService.update(this.template));
        } else {
            this.subscribeToSaveResponse(
                this.templateService.create(this.template));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Template>>) {
        result.subscribe((res: HttpResponse<Template>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Template) {
        this.eventManager.broadcast({ name: 'templateListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-template-popup',
    template: ''
})
export class TemplatePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private templatePopupService: TemplatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.templatePopupService
                    .open(TemplateDialogComponent as Component, params['id']);
            } else {
                this.templatePopupService
                    .open(TemplateDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
