import { BaseEntity } from './../../shared';

export class Payment implements BaseEntity {
    constructor(
        public id?: number,
        public amount?: number,
        public addDate?: any,
        public customerId?: number,
    ) {
    }
}
