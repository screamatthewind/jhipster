/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BillingTestModule } from '../../../test.module';
import { StbComponent } from '../../../../../../main/webapp/app/entities/stb/stb.component';
import { StbService } from '../../../../../../main/webapp/app/entities/stb/stb.service';
import { Stb } from '../../../../../../main/webapp/app/entities/stb/stb.model';

describe('Component Tests', () => {

    describe('Stb Management Component', () => {
        let comp: StbComponent;
        let fixture: ComponentFixture<StbComponent>;
        let service: StbService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BillingTestModule],
                declarations: [StbComponent],
                providers: [
                    StbService
                ]
            })
            .overrideTemplate(StbComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StbComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StbService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Stb(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.stbs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
