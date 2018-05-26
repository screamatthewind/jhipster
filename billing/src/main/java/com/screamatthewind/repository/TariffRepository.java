package com.screamatthewind.repository;

import com.screamatthewind.domain.Tariff;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tariff entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {

}
