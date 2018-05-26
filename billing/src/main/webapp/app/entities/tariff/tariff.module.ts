import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BillingSharedModule } from '../../shared';
import {
    TariffService,
    TariffPopupService,
    TariffComponent,
    TariffDetailComponent,
    TariffDialogComponent,
    TariffPopupComponent,
    TariffDeletePopupComponent,
    TariffDeleteDialogComponent,
    tariffRoute,
    tariffPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tariffRoute,
    ...tariffPopupRoute,
];

@NgModule({
    imports: [
        BillingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TariffComponent,
        TariffDetailComponent,
        TariffDialogComponent,
        TariffDeleteDialogComponent,
        TariffPopupComponent,
        TariffDeletePopupComponent,
    ],
    entryComponents: [
        TariffComponent,
        TariffDialogComponent,
        TariffPopupComponent,
        TariffDeleteDialogComponent,
        TariffDeletePopupComponent,
    ],
    providers: [
        TariffService,
        TariffPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BillingTariffModule {}
