/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BillingTestModule } from '../../../test.module';
import { ServicePackageComponent } from '../../../../../../main/webapp/app/entities/service-package/service-package.component';
import { ServicePackageService } from '../../../../../../main/webapp/app/entities/service-package/service-package.service';
import { ServicePackage } from '../../../../../../main/webapp/app/entities/service-package/service-package.model';

describe('Component Tests', () => {

    describe('ServicePackage Management Component', () => {
        let comp: ServicePackageComponent;
        let fixture: ComponentFixture<ServicePackageComponent>;
        let service: ServicePackageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BillingTestModule],
                declarations: [ServicePackageComponent],
                providers: [
                    ServicePackageService
                ]
            })
            .overrideTemplate(ServicePackageComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ServicePackageComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServicePackageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ServicePackage(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.servicePackages[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
