import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Tariff } from './tariff.model';
import { TariffService } from './tariff.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-tariff',
    templateUrl: './tariff.component.html'
})
export class TariffComponent implements OnInit, OnDestroy {
tariffs: Tariff[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tariffService: TariffService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.tariffService.query().subscribe(
            (res: HttpResponse<Tariff[]>) => {
                this.tariffs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTariffs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Tariff) {
        return item.id;
    }
    registerChangeInTariffs() {
        this.eventSubscriber = this.eventManager.subscribe('tariffListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
