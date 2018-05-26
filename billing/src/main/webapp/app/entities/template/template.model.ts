import { BaseEntity } from './../../shared';

export class Template implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
    ) {
    }
}
