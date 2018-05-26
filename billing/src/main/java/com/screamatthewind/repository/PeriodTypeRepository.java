package com.screamatthewind.repository;

import com.screamatthewind.domain.PeriodType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PeriodType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeriodTypeRepository extends JpaRepository<PeriodType, Long> {

}
