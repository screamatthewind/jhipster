/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BillingTestModule } from '../../../test.module';
import { ServicePackageDetailComponent } from '../../../../../../main/webapp/app/entities/service-package/service-package-detail.component';
import { ServicePackageService } from '../../../../../../main/webapp/app/entities/service-package/service-package.service';
import { ServicePackage } from '../../../../../../main/webapp/app/entities/service-package/service-package.model';

describe('Component Tests', () => {

    describe('ServicePackage Management Detail Component', () => {
        let comp: ServicePackageDetailComponent;
        let fixture: ComponentFixture<ServicePackageDetailComponent>;
        let service: ServicePackageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BillingTestModule],
                declarations: [ServicePackageDetailComponent],
                providers: [
                    ServicePackageService
                ]
            })
            .overrideTemplate(ServicePackageDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ServicePackageDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServicePackageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ServicePackage(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.servicePackage).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
