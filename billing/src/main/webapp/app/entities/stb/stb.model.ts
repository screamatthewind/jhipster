import { BaseEntity } from './../../shared';

export class Stb implements BaseEntity {
    constructor(
        public id?: number,
        public mac?: string,
        public ipAddress?: string,
        public enabled?: boolean,
        public addDate?: any,
        public customerId?: number,
        public tariffId?: number,
    ) {
        this.enabled = false;
    }
}
