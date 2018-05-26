/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BillingTestModule } from '../../../test.module';
import { TemplateDetailComponent } from '../../../../../../main/webapp/app/entities/template/template-detail.component';
import { TemplateService } from '../../../../../../main/webapp/app/entities/template/template.service';
import { Template } from '../../../../../../main/webapp/app/entities/template/template.model';

describe('Component Tests', () => {

    describe('Template Management Detail Component', () => {
        let comp: TemplateDetailComponent;
        let fixture: ComponentFixture<TemplateDetailComponent>;
        let service: TemplateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BillingTestModule],
                declarations: [TemplateDetailComponent],
                providers: [
                    TemplateService
                ]
            })
            .overrideTemplate(TemplateDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TemplateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TemplateService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Template(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.template).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
