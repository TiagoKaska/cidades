package com.senior.cidades.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.senior.cidades.domain.City;

import com.senior.cidades.repository.CityRepository;
import com.senior.cidades.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing City.
 */
@RestController
@RequestMapping("/api")
public class CityResource {

    private final Logger log = LoggerFactory.getLogger(CityResource.class);

    private static final String ENTITY_NAME = "city";

    private final CityRepository cityRepository;
    public CityResource(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    /**
     * POST  /cities : Create a new city.
     *
     * @param city the city to create
     * @return the ResponseEntity with status 201 (Created) and with body the new city, or with status 400 (Bad Request) if the city has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cities")
    @Timed
    public ResponseEntity<City> createCity(@RequestBody City city) throws URISyntaxException {
        log.debug("REST request to save City : {}", city);
        if (city.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new city cannot already have an ID")).body(null);
        }
        City result = cityRepository.save(city);
        return ResponseEntity.created(new URI("/api/cities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cities : Updates an existing city.
     *
     * @param city the city to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated city,
     * or with status 400 (Bad Request) if the city is not valid,
     * or with status 500 (Internal Server Error) if the city couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cities")
    @Timed
    public ResponseEntity<City> updateCity(@RequestBody City city) throws URISyntaxException {
        log.debug("REST request to update City : {}", city);
        if (city.getId() == null) {
            return createCity(city);
        }
        City result = cityRepository.save(city);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, city.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cities : get all the cities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cities in body
     */
    @GetMapping("/cities")
    @Timed
    public List<City> getAllCities() {
        log.debug("REST request to get all Cities");
        return cityRepository.findAll();
        }

    /**
     * GET  /cities/:id : get the "id" city.
     *
     * @param id the id of the city to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the city, or with status 404 (Not Found)
     */
    @GetMapping("/cities/{id}")
    @Timed
    public ResponseEntity<City> getCity(@PathVariable Long id) {
        log.debug("REST request to get City : {}", id);
        City city = cityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(city));
    }

    /**
     * GET  /cities/capitais : get the capitais city.
     *
     * * @return the ResponseEntity with status 200 (OK) and with body the city, or with status 404 (Not Found)
     */
    @GetMapping("/cities/capitals")
    public List<City> getAllCapitais()
    {
        log.debug("REST request Cities that are capitals");
        return cityRepository.findAllByCapitalTrueOrderByName();
    }

    /**
     * GET  /cities/ibge : get the cities by ibge id.
     *
     * @param ibge
     * * @return the ResponseEntity with status 200 (OK) and with body the city, or with status 404 (Not Found)
     */
    @GetMapping("/cities/ibge/{ibge}")
    public ResponseEntity<City> getByIbgeEquals(@PathVariable Long ibge){
        log.debug("Rest request get City ibgeId : {}", ibge);
        City city = cityRepository.findCitiesByIbgeIdEquals(ibge);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(city));
    }


    /**
     * GET  /cities/uf : get the cities from uf.
     *
     * @param uf
     * * @return the ResponseEntity with status 200 (OK) and with body the city, or with status 404 (Not Found)
     */
    @GetMapping("/cities/uf/{uf}")
    public List<String> getCitiesByUfEqual(@PathVariable("uf") String uf){
        log.debug("Rest get city by uf :{}", uf);
        return cityRepository.selectNamesFromCityByUfEqual(uf);
    }

    /**
     * GET  /cities/uf : get count cities from uf.
     *
     * @param uf
     * * @return the ResponseEntity with status 200 (OK) and with body the city, or with status 404 (Not Found)
     */
    @GetMapping("/cities/count/{uf}")
    public int getCountDistinctCitiesByUfEqual(@PathVariable("uf") String uf){
        log.debug("Rest get city by uf :{}", uf);
        return cityRepository.countDistinctByUfEquals(uf);
    }

    @GetMapping("/cities/count/all")
    public int getCountAll(){
        log.debug("Rest get count all");
        return  cityRepository.countAll();
    }



    /**
     * DELETE  /cities/:id : delete the "id" city.
     *
     * @param id the id of the city to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        log.debug("REST request to delete City : {}", id);
        cityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
