import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Template } from './template.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Template>;

@Injectable()
export class TemplateService {

    private resourceUrl =  SERVER_API_URL + 'api/templates';

    constructor(private http: HttpClient) { }

    create(template: Template): Observable<EntityResponseType> {
        const copy = this.convert(template);
        return this.http.post<Template>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(template: Template): Observable<EntityResponseType> {
        const copy = this.convert(template);
        return this.http.put<Template>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Template>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Template[]>> {
        const options = createRequestOption(req);
        return this.http.get<Template[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Template[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Template = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Template[]>): HttpResponse<Template[]> {
        const jsonResponse: Template[] = res.body;
        const body: Template[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Template.
     */
    private convertItemFromServer(template: Template): Template {
        const copy: Template = Object.assign({}, template);
        return copy;
    }

    /**
     * Convert a Template to a JSON which can be sent to the server.
     */
    private convert(template: Template): Template {
        const copy: Template = Object.assign({}, template);
        return copy;
    }
}
