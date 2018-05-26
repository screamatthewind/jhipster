import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Tariff } from './tariff.model';
import { TariffService } from './tariff.service';

@Component({
    selector: 'jhi-tariff-detail',
    templateUrl: './tariff-detail.component.html'
})
export class TariffDetailComponent implements OnInit, OnDestroy {

    tariff: Tariff;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tariffService: TariffService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTariffs();
    }

    load(id) {
        this.tariffService.find(id)
            .subscribe((tariffResponse: HttpResponse<Tariff>) => {
                this.tariff = tariffResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTariffs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tariffListModification',
            (response) => this.load(this.tariff.id)
        );
    }
}
