import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BillingSharedModule } from '../../shared';
import {
    StbService,
    StbPopupService,
    StbComponent,
    StbDetailComponent,
    StbDialogComponent,
    StbPopupComponent,
    StbDeletePopupComponent,
    StbDeleteDialogComponent,
    stbRoute,
    stbPopupRoute,
} from './';

const ENTITY_STATES = [
    ...stbRoute,
    ...stbPopupRoute,
];

@NgModule({
    imports: [
        BillingSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        StbComponent,
        StbDetailComponent,
        StbDialogComponent,
        StbDeleteDialogComponent,
        StbPopupComponent,
        StbDeletePopupComponent,
    ],
    entryComponents: [
        StbComponent,
        StbDialogComponent,
        StbPopupComponent,
        StbDeleteDialogComponent,
        StbDeletePopupComponent,
    ],
    providers: [
        StbService,
        StbPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BillingStbModule {}
