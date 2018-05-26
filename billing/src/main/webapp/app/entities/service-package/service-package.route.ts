import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ServicePackageComponent } from './service-package.component';
import { ServicePackageDetailComponent } from './service-package-detail.component';
import { ServicePackagePopupComponent } from './service-package-dialog.component';
import { ServicePackageDeletePopupComponent } from './service-package-delete-dialog.component';

export const servicePackageRoute: Routes = [
    {
        path: 'service-package',
        component: ServicePackageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ServicePackages'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'service-package/:id',
        component: ServicePackageDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ServicePackages'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const servicePackagePopupRoute: Routes = [
    {
        path: 'service-package-new',
        component: ServicePackagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ServicePackages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'service-package/:id/edit',
        component: ServicePackagePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ServicePackages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'service-package/:id/delete',
        component: ServicePackageDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ServicePackages'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
