/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BillingTestModule } from '../../../test.module';
import { ServicePackageDialogComponent } from '../../../../../../main/webapp/app/entities/service-package/service-package-dialog.component';
import { ServicePackageService } from '../../../../../../main/webapp/app/entities/service-package/service-package.service';
import { ServicePackage } from '../../../../../../main/webapp/app/entities/service-package/service-package.model';
import { ChannelService } from '../../../../../../main/webapp/app/entities/channel';

describe('Component Tests', () => {

    describe('ServicePackage Management Dialog Component', () => {
        let comp: ServicePackageDialogComponent;
        let fixture: ComponentFixture<ServicePackageDialogComponent>;
        let service: ServicePackageService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BillingTestModule],
                declarations: [ServicePackageDialogComponent],
                providers: [
                    ChannelService,
                    ServicePackageService
                ]
            })
            .overrideTemplate(ServicePackageDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ServicePackageDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServicePackageService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ServicePackage(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.servicePackage = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'servicePackageListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ServicePackage();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.servicePackage = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'servicePackageListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
