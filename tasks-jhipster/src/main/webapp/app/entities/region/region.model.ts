import { BaseEntity } from './../../shared';

export class Region implements BaseEntity {
    constructor(
        public id?: number,
        public regionId?: number,
        public regionName?: string,
    ) {
    }
}
