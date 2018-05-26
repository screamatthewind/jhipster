import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Agent } from './agent.model';
import { AgentService } from './agent.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-agent',
    templateUrl: './agent.component.html'
})
export class AgentComponent implements OnInit, OnDestroy {
agents: Agent[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private agentService: AgentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.agentService.query().subscribe(
            (res: HttpResponse<Agent[]>) => {
                this.agents = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAgents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Agent) {
        return item.id;
    }
    registerChangeInAgents() {
        this.eventSubscriber = this.eventManager.subscribe('agentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
