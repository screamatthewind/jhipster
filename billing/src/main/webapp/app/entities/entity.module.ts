import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BillingAgentModule } from './agent/agent.module';
import { BillingTemplateModule } from './template/template.module';
import { BillingCustomerModule } from './customer/customer.module';
import { BillingPaymentModule } from './payment/payment.module';
import { BillingStbModule } from './stb/stb.module';
import { BillingChannelModule } from './channel/channel.module';
import { BillingGenreModule } from './genre/genre.module';
import { BillingTariffModule } from './tariff/tariff.module';
import { BillingPeriodTypeModule } from './period-type/period-type.module';
import { BillingServicePackageModule } from './service-package/service-package.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BillingAgentModule,
        BillingTemplateModule,
        BillingCustomerModule,
        BillingPaymentModule,
        BillingStbModule,
        BillingChannelModule,
        BillingGenreModule,
        BillingTariffModule,
        BillingPeriodTypeModule,
        BillingServicePackageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BillingEntityModule {}
