import { BaseEntity } from './../../shared';

export class Channel implements BaseEntity {
    constructor(
        public id?: number,
        public number?: number,
        public name?: string,
        public link?: string,
        public epgCorrection?: number,
        public xmltvId?: number,
        public addDate?: any,
        public servicePackages?: BaseEntity[],
        public genreId?: number,
    ) {
    }
}
