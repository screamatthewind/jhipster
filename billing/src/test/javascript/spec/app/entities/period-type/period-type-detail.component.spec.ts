/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BillingTestModule } from '../../../test.module';
import { PeriodTypeDetailComponent } from '../../../../../../main/webapp/app/entities/period-type/period-type-detail.component';
import { PeriodTypeService } from '../../../../../../main/webapp/app/entities/period-type/period-type.service';
import { PeriodType } from '../../../../../../main/webapp/app/entities/period-type/period-type.model';

describe('Component Tests', () => {

    describe('PeriodType Management Detail Component', () => {
        let comp: PeriodTypeDetailComponent;
        let fixture: ComponentFixture<PeriodTypeDetailComponent>;
        let service: PeriodTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BillingTestModule],
                declarations: [PeriodTypeDetailComponent],
                providers: [
                    PeriodTypeService
                ]
            })
            .overrideTemplate(PeriodTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PeriodTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PeriodType(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.periodType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
