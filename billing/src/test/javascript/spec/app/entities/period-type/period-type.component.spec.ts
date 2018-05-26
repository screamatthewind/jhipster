/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BillingTestModule } from '../../../test.module';
import { PeriodTypeComponent } from '../../../../../../main/webapp/app/entities/period-type/period-type.component';
import { PeriodTypeService } from '../../../../../../main/webapp/app/entities/period-type/period-type.service';
import { PeriodType } from '../../../../../../main/webapp/app/entities/period-type/period-type.model';

describe('Component Tests', () => {

    describe('PeriodType Management Component', () => {
        let comp: PeriodTypeComponent;
        let fixture: ComponentFixture<PeriodTypeComponent>;
        let service: PeriodTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BillingTestModule],
                declarations: [PeriodTypeComponent],
                providers: [
                    PeriodTypeService
                ]
            })
            .overrideTemplate(PeriodTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PeriodTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PeriodType(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.periodTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
