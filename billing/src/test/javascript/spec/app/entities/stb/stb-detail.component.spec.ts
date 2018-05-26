/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BillingTestModule } from '../../../test.module';
import { StbDetailComponent } from '../../../../../../main/webapp/app/entities/stb/stb-detail.component';
import { StbService } from '../../../../../../main/webapp/app/entities/stb/stb.service';
import { Stb } from '../../../../../../main/webapp/app/entities/stb/stb.model';

describe('Component Tests', () => {

    describe('Stb Management Detail Component', () => {
        let comp: StbDetailComponent;
        let fixture: ComponentFixture<StbDetailComponent>;
        let service: StbService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BillingTestModule],
                declarations: [StbDetailComponent],
                providers: [
                    StbService
                ]
            })
            .overrideTemplate(StbDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StbDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StbService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Stb(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.stb).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
