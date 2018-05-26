import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Stb } from './stb.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Stb>;

@Injectable()
export class StbService {

    private resourceUrl =  SERVER_API_URL + 'api/stbs';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(stb: Stb): Observable<EntityResponseType> {
        const copy = this.convert(stb);
        return this.http.post<Stb>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(stb: Stb): Observable<EntityResponseType> {
        const copy = this.convert(stb);
        return this.http.put<Stb>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Stb>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Stb[]>> {
        const options = createRequestOption(req);
        return this.http.get<Stb[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Stb[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Stb = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Stb[]>): HttpResponse<Stb[]> {
        const jsonResponse: Stb[] = res.body;
        const body: Stb[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Stb.
     */
    private convertItemFromServer(stb: Stb): Stb {
        const copy: Stb = Object.assign({}, stb);
        copy.addDate = this.dateUtils
            .convertDateTimeFromServer(stb.addDate);
        return copy;
    }

    /**
     * Convert a Stb to a JSON which can be sent to the server.
     */
    private convert(stb: Stb): Stb {
        const copy: Stb = Object.assign({}, stb);

        copy.addDate = this.dateUtils.toDate(stb.addDate);
        return copy;
    }
}
