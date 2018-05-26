import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Tariff } from './tariff.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Tariff>;

@Injectable()
export class TariffService {

    private resourceUrl =  SERVER_API_URL + 'api/tariffs';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(tariff: Tariff): Observable<EntityResponseType> {
        const copy = this.convert(tariff);
        return this.http.post<Tariff>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(tariff: Tariff): Observable<EntityResponseType> {
        const copy = this.convert(tariff);
        return this.http.put<Tariff>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Tariff>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Tariff[]>> {
        const options = createRequestOption(req);
        return this.http.get<Tariff[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Tariff[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Tariff = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Tariff[]>): HttpResponse<Tariff[]> {
        const jsonResponse: Tariff[] = res.body;
        const body: Tariff[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Tariff.
     */
    private convertItemFromServer(tariff: Tariff): Tariff {
        const copy: Tariff = Object.assign({}, tariff);
        copy.addDate = this.dateUtils
            .convertDateTimeFromServer(tariff.addDate);
        return copy;
    }

    /**
     * Convert a Tariff to a JSON which can be sent to the server.
     */
    private convert(tariff: Tariff): Tariff {
        const copy: Tariff = Object.assign({}, tariff);

        copy.addDate = this.dateUtils.toDate(tariff.addDate);
        return copy;
    }
}
