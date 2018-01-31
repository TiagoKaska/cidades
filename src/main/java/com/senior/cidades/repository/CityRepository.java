package com.senior.cidades.repository;

import com.senior.cidades.domain.City;
import org.springframework.data.repository.query.Param;
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


    @Query("select c.name from City c where c.uf = :uf")
    List<String> selectNamesFromCityByUfEqual(@Param("uf") String uf);

    int countDistinctByUfEquals(String uf);

    @Query("select count(id) from City")
    int countAll();


}
