import { BaseEntity } from './../../shared';

export class ServicePackage implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public price?: number,
        public enabled?: boolean,
        public addDate?: any,
        public channelId?: number,
        public tariffs?: BaseEntity[],
    ) {
        this.enabled = false;
    }
}
