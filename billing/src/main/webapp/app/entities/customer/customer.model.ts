import { BaseEntity } from './../../shared';

export class Customer implements BaseEntity {
    constructor(
        public id?: number,
        public accountNumber?: string,
        public firstName?: string,
        public lastName?: string,
        public address1?: string,
        public address2?: string,
        public city?: string,
        public state?: string,
        public zip?: string,
        public phone?: string,
        public email?: string,
        public username?: string,
        public password?: string,
        public enabled?: boolean,
        public addDate?: any,
        public agentId?: number,
    ) {
        this.enabled = false;
    }
}
