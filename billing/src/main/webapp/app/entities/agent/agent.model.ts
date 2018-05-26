import { BaseEntity } from './../../shared';

export class Agent implements BaseEntity {
    constructor(
        public id?: number,
        public accountNumber?: string,
        public name?: string,
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
        public credit?: number,
        public addDate?: any,
        public templateId?: number,
    ) {
        this.enabled = false;
    }
}
