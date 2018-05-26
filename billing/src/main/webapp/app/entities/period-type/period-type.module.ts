import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BillingSharedModule } from '../../shared';
import {
    PeriodTypeService,
    PeriodTypePopupService,
    PeriodTypeComponent,
    PeriodTypeDetailComponent,
    PeriodTypeDialogComponent,
    PeriodTypePopupComponent,
    PeriodTypeDeletePopupComponent,
    PeriodTypeDeleteDialogComponent,
    periodTypeRoute,
    periodTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...periodTypeRoute,
    ...periodTypePopupRoute,
];

@NgModule({
    imports: [
        BillingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PeriodTypeComponent,
        PeriodTypeDetailComponent,
        PeriodTypeDialogComponent,
        PeriodTypeDeleteDialogComponent,
        PeriodTypePopupComponent,
        PeriodTypeDeletePopupComponent,
    ],
    entryComponents: [
        PeriodTypeComponent,
        PeriodTypeDialogComponent,
        PeriodTypePopupComponent,
        PeriodTypeDeleteDialogComponent,
        PeriodTypeDeletePopupComponent,
    ],
    providers: [
        PeriodTypeService,
        PeriodTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BillingPeriodTypeModule {}
