/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterTasksTestModule } from '../../../test.module';
import { JobHistoryDialogComponent } from '../../../../../../main/webapp/app/entities/job-history/job-history-dialog.component';
import { JobHistoryService } from '../../../../../../main/webapp/app/entities/job-history/job-history.service';
import { JobHistory } from '../../../../../../main/webapp/app/entities/job-history/job-history.model';
import { DepartmentService } from '../../../../../../main/webapp/app/entities/department';
import { JobService } from '../../../../../../main/webapp/app/entities/job';
import { EmployeeService } from '../../../../../../main/webapp/app/entities/employee';

describe('Component Tests', () => {

    describe('JobHistory Management Dialog Component', () => {
        let comp: JobHistoryDialogComponent;
        let fixture: ComponentFixture<JobHistoryDialogComponent>;
        let service: JobHistoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTasksTestModule],
                declarations: [JobHistoryDialogComponent],
                providers: [
                    DepartmentService,
                    JobService,
                    EmployeeService,
                    JobHistoryService
                ]
            })
            .overrideTemplate(JobHistoryDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JobHistoryDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobHistoryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new JobHistory(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.jobHistory = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'jobHistoryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new JobHistory();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.jobHistory = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'jobHistoryListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
