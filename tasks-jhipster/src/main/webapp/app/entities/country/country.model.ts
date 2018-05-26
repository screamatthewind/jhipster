import { BaseEntity } from './../../shared';

export class Country implements BaseEntity {
    constructor(
        public id?: number,
        public countryId?: number,
        public countryName?: string,
        public regionId?: number,
    ) {
    }
}
