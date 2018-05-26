import { BaseEntity } from './../../shared';

export class PeriodType implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
