import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { StbComponent } from './stb.component';
import { StbDetailComponent } from './stb-detail.component';
import { StbPopupComponent } from './stb-dialog.component';
import { StbDeletePopupComponent } from './stb-delete-dialog.component';

export const stbRoute: Routes = [
    {
        path: 'stb',
        component: StbComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stbs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'stb/:id',
        component: StbDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stbs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stbPopupRoute: Routes = [
    {
        path: 'stb-new',
        component: StbPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stbs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'stb/:id/edit',
        component: StbPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stbs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'stb/:id/delete',
        component: StbDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stbs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
