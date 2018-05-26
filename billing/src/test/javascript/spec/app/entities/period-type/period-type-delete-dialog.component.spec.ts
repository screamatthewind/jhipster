/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BillingTestModule } from '../../../test.module';
import { PeriodTypeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/period-type/period-type-delete-dialog.component';
import { PeriodTypeService } from '../../../../../../main/webapp/app/entities/period-type/period-type.service';

describe('Component Tests', () => {

    describe('PeriodType Management Delete Component', () => {
        let comp: PeriodTypeDeleteDialogComponent;
        let fixture: ComponentFixture<PeriodTypeDeleteDialogComponent>;
        let service: PeriodTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BillingTestModule],
                declarations: [PeriodTypeDeleteDialogComponent],
                providers: [
                    PeriodTypeService
                ]
            })
            .overrideTemplate(PeriodTypeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PeriodTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PeriodTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
