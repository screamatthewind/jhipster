import { BaseEntity } from './../../shared';

export class Tariff implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public price?: number,
        public period?: number,
        public enabled?: boolean,
        public addDate?: any,
        public periodTypeId?: number,
        public servicePackageId?: number,
    ) {
        this.enabled = false;
    }
}
