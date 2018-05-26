package com.screamatthewind.repository;

import com.screamatthewind.domain.Stb;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Stb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StbRepository extends JpaRepository<Stb, Long> {

}
