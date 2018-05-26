import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterTasksTaskModule } from './task/task.module';
import { JhipsterTasksPostModule } from './post/post.module';
import { JhipsterTasksDepartmentModule } from './department/department.module';
import { JhipsterTasksJobHistoryModule } from './job-history/job-history.module';
import { JhipsterTasksJobModule } from './job/job.module';
import { JhipsterTasksEmployeeModule } from './employee/employee.module';
import { JhipsterTasksLocationModule } from './location/location.module';
import { JhipsterTasksCountryModule } from './country/country.module';
import { JhipsterTasksRegionModule } from './region/region.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterTasksTaskModule,
        JhipsterTasksPostModule,
        JhipsterTasksDepartmentModule,
        JhipsterTasksJobHistoryModule,
        JhipsterTasksJobModule,
        JhipsterTasksEmployeeModule,
        JhipsterTasksLocationModule,
        JhipsterTasksCountryModule,
        JhipsterTasksRegionModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterTasksEntityModule {}
