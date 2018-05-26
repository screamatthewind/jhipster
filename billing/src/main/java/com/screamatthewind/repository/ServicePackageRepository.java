package com.screamatthewind.repository;

import com.screamatthewind.domain.ServicePackage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ServicePackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServicePackageRepository extends JpaRepository<ServicePackage, Long> {

}
