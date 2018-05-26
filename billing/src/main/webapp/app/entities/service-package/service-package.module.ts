import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BillingSharedModule } from '../../shared';
import {
    ServicePackageService,
    ServicePackagePopupService,
    ServicePackageComponent,
    ServicePackageDetailComponent,
    ServicePackageDialogComponent,
    ServicePackagePopupComponent,
    ServicePackageDeletePopupComponent,
    ServicePackageDeleteDialogComponent,
    servicePackageRoute,
    servicePackagePopupRoute,
} from './';

const ENTITY_STATES = [
    ...servicePackageRoute,
    ...servicePackagePopupRoute,
];

@NgModule({
    imports: [
        BillingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ServicePackageComponent,
        ServicePackageDetailComponent,
        ServicePackageDialogComponent,
        ServicePackageDeleteDialogComponent,
        ServicePackagePopupComponent,
        ServicePackageDeletePopupComponent,
    ],
    entryComponents: [
        ServicePackageComponent,
        ServicePackageDialogComponent,
        ServicePackagePopupComponent,
        ServicePackageDeleteDialogComponent,
        ServicePackageDeletePopupComponent,
    ],
    providers: [
        ServicePackageService,
        ServicePackagePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BillingServicePackageModule {}
