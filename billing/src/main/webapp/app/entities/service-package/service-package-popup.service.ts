import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { ServicePackage } from './service-package.model';
import { ServicePackageService } from './service-package.service';

@Injectable()
export class ServicePackagePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private servicePackageService: ServicePackageService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.servicePackageService.find(id)
                    .subscribe((servicePackageResponse: HttpResponse<ServicePackage>) => {
                        const servicePackage: ServicePackage = servicePackageResponse.body;
                        servicePackage.addDate = this.datePipe
                            .transform(servicePackage.addDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.servicePackageModalRef(component, servicePackage);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.servicePackageModalRef(component, new ServicePackage());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    servicePackageModalRef(component: Component, servicePackage: ServicePackage): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.servicePackage = servicePackage;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
