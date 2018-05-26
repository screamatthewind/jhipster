import { BaseEntity } from './../../shared';

export class JobHistory implements BaseEntity {
    constructor(
        public id?: number,
        public startDate?: any,
        public endDate?: any,
        public departmentId?: number,
        public jobId?: number,
        public employeeId?: number,
    ) {
    }
}
