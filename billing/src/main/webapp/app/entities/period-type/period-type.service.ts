import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PeriodType } from './period-type.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PeriodType>;

@Injectable()
export class PeriodTypeService {

    private resourceUrl =  SERVER_API_URL + 'api/period-types';

    constructor(private http: HttpClient) { }

    create(periodType: PeriodType): Observable<EntityResponseType> {
        const copy = this.convert(periodType);
        return this.http.post<PeriodType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(periodType: PeriodType): Observable<EntityResponseType> {
        const copy = this.convert(periodType);
        return this.http.put<PeriodType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PeriodType>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PeriodType[]>> {
        const options = createRequestOption(req);
        return this.http.get<PeriodType[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PeriodType[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PeriodType = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PeriodType[]>): HttpResponse<PeriodType[]> {
        const jsonResponse: PeriodType[] = res.body;
        const body: PeriodType[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PeriodType.
     */
    private convertItemFromServer(periodType: PeriodType): PeriodType {
        const copy: PeriodType = Object.assign({}, periodType);
        return copy;
    }

    /**
     * Convert a PeriodType to a JSON which can be sent to the server.
     */
    private convert(periodType: PeriodType): PeriodType {
        const copy: PeriodType = Object.assign({}, periodType);
        return copy;
    }
}
