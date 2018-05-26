import { BaseEntity } from './../../shared';

export class Department implements BaseEntity {
    constructor(
        public id?: number,
        public departmentId?: number,
        public departmentName?: string,
        public locationId?: number,
        public employees?: BaseEntity[],
    ) {
    }
}
