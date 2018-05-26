import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PeriodTypeComponent } from './period-type.component';
import { PeriodTypeDetailComponent } from './period-type-detail.component';
import { PeriodTypePopupComponent } from './period-type-dialog.component';
import { PeriodTypeDeletePopupComponent } from './period-type-delete-dialog.component';

export const periodTypeRoute: Routes = [
    {
        path: 'period-type',
        component: PeriodTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'period-type/:id',
        component: PeriodTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const periodTypePopupRoute: Routes = [
    {
        path: 'period-type-new',
        component: PeriodTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'period-type/:id/edit',
        component: PeriodTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'period-type/:id/delete',
        component: PeriodTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PeriodTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
