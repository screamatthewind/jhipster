import { BaseEntity } from './../../shared';

export class Job implements BaseEntity {
    constructor(
        public id?: number,
        public jobId?: number,
        public jobTitle?: string,
        public minSalary?: number,
        public maxSalary?: number,
        public tasks?: BaseEntity[],
        public employeeId?: number,
    ) {
    }
}
