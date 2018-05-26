import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TariffComponent } from './tariff.component';
import { TariffDetailComponent } from './tariff-detail.component';
import { TariffPopupComponent } from './tariff-dialog.component';
import { TariffDeletePopupComponent } from './tariff-delete-dialog.component';

export const tariffRoute: Routes = [
    {
        path: 'tariff',
        component: TariffComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tariffs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tariff/:id',
        component: TariffDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tariffs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tariffPopupRoute: Routes = [
    {
        path: 'tariff-new',
        component: TariffPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tariffs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tariff/:id/edit',
        component: TariffPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tariffs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tariff/:id/delete',
        component: TariffDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Tariffs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
