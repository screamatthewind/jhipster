import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ServicePackage } from './service-package.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ServicePackage>;

@Injectable()
export class ServicePackageService {

    private resourceUrl =  SERVER_API_URL + 'api/service-packages';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(servicePackage: ServicePackage): Observable<EntityResponseType> {
        const copy = this.convert(servicePackage);
        return this.http.post<ServicePackage>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(servicePackage: ServicePackage): Observable<EntityResponseType> {
        const copy = this.convert(servicePackage);
        return this.http.put<ServicePackage>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ServicePackage>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ServicePackage[]>> {
        const options = createRequestOption(req);
        return this.http.get<ServicePackage[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ServicePackage[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ServicePackage = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ServicePackage[]>): HttpResponse<ServicePackage[]> {
        const jsonResponse: ServicePackage[] = res.body;
        const body: ServicePackage[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ServicePackage.
     */
    private convertItemFromServer(servicePackage: ServicePackage): ServicePackage {
        const copy: ServicePackage = Object.assign({}, servicePackage);
        copy.addDate = this.dateUtils
            .convertDateTimeFromServer(servicePackage.addDate);
        return copy;
    }

    /**
     * Convert a ServicePackage to a JSON which can be sent to the server.
     */
    private convert(servicePackage: ServicePackage): ServicePackage {
        const copy: ServicePackage = Object.assign({}, servicePackage);

        copy.addDate = this.dateUtils.toDate(servicePackage.addDate);
        return copy;
    }
}
