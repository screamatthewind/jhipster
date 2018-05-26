import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Template } from './template.model';
import { TemplateService } from './template.service';

@Component({
    selector: 'jhi-template-detail',
    templateUrl: './template-detail.component.html'
})
export class TemplateDetailComponent implements OnInit, OnDestroy {

    template: Template;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private templateService: TemplateService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTemplates();
    }

    load(id) {
        this.templateService.find(id)
            .subscribe((templateResponse: HttpResponse<Template>) => {
                this.template = templateResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTemplates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'templateListModification',
            (response) => this.load(this.template.id)
        );
    }
}
