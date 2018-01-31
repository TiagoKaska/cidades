package com.senior.cidades.repository;

import com.senior.cidades.domain.City;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Map;


/**
 * Spring Data JPA repository for the City entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findAllByCapitalTrueOrderByName();

    City findCitiesByIbgeIdEquals(Long ibgeId);

}
