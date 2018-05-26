import { BaseEntity } from './../../shared';

export class Task implements BaseEntity {
    constructor(
        public id?: number,
        public taskId?: number,
        public title?: string,
        public description?: string,
        public jobs?: BaseEntity[],
    ) {
    }
}
