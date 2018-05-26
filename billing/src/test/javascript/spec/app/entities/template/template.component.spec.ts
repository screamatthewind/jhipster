/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BillingTestModule } from '../../../test.module';
import { TemplateComponent } from '../../../../../../main/webapp/app/entities/template/template.component';
import { TemplateService } from '../../../../../../main/webapp/app/entities/template/template.service';
import { Template } from '../../../../../../main/webapp/app/entities/template/template.model';

describe('Component Tests', () => {

    describe('Template Management Component', () => {
        let comp: TemplateComponent;
        let fixture: ComponentFixture<TemplateComponent>;
        let service: TemplateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BillingTestModule],
                declarations: [TemplateComponent],
                providers: [
                    TemplateService
                ]
            })
            .overrideTemplate(TemplateComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TemplateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TemplateService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Template(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.templates[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
