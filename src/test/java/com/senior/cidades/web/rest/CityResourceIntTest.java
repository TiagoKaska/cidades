package com.senior.cidades.web.rest;

import com.senior.cidades.CidadesApp;

import com.senior.cidades.domain.City;
import com.senior.cidades.repository.CityRepository;
import com.senior.cidades.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CityResource REST controller.
 *
 * @see CityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CidadesApp.class)
public class CityResourceIntTest {

    private static final Long DEFAULT_IBGE_ID = 1L;
    private static final Long UPDATED_IBGE_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UF = "AAAAAAAAAA";
    private static final String UPDATED_UF = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CAPITAL = false;
    private static final Boolean UPDATED_CAPITAL = true;

    private static final Double DEFAULT_LON = 1D;
    private static final Double UPDATED_LON = 2D;

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;

    private static final String DEFAULT_NO_ACENTS = "AAAAAAAAAA";
    private static final String UPDATED_NO_ACENTS = "BBBBBBBBBB";

    private static final String DEFAULT_ALTERNATIVE_NAMES = "AAAAAAAAAA";
    private static final String UPDATED_ALTERNATIVE_NAMES = "BBBBBBBBBB";

    private static final String DEFAULT_MICRO_REGION = "AAAAAAAAAA";
    private static final String UPDATED_MICRO_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_MESO_REGION = "AAAAAAAAAA";
    private static final String UPDATED_MESO_REGION = "BBBBBBBBBB";

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCityMockMvc;

    private City city;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CityResource cityResource = new CityResource(cityRepository);
        this.restCityMockMvc = MockMvcBuilders.standaloneSetup(cityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static City createEntity(EntityManager em) {
        City city = new City()
            .ibgeId(DEFAULT_IBGE_ID)
            .name(DEFAULT_NAME)
            .uf(DEFAULT_UF)
            .capital(DEFAULT_CAPITAL)
            .lon(DEFAULT_LON)
            .lat(DEFAULT_LAT)
            .noAcents(DEFAULT_NO_ACENTS)
            .alternativeNames(DEFAULT_ALTERNATIVE_NAMES)
            .microRegion(DEFAULT_MICRO_REGION)
            .mesoRegion(DEFAULT_MESO_REGION);
        return city;
    }

    @Before
    public void initTest() {
        city = createEntity(em);
    }

    @Test
    @Transactional
    public void createCity() throws Exception {
        int databaseSizeBeforeCreate = cityRepository.findAll().size();

        // Create the City
        restCityMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(city)))
            .andExpect(status().isCreated());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeCreate + 1);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getIbgeId()).isEqualTo(DEFAULT_IBGE_ID);
        assertThat(testCity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCity.getUf()).isEqualTo(DEFAULT_UF);
        assertThat(testCity.isCapital()).isEqualTo(DEFAULT_CAPITAL);
        assertThat(testCity.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testCity.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testCity.getNoAcents()).isEqualTo(DEFAULT_NO_ACENTS);
        assertThat(testCity.getAlternativeNames()).isEqualTo(DEFAULT_ALTERNATIVE_NAMES);
        assertThat(testCity.getMicroRegion()).isEqualTo(DEFAULT_MICRO_REGION);
        assertThat(testCity.getMesoRegion()).isEqualTo(DEFAULT_MESO_REGION);
    }

    @Test
    @Transactional
    public void createCityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cityRepository.findAll().size();

        // Create the City with an existing ID
        city.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(city)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCities() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList
        restCityMockMvc.perform(get("/api/cities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(city.getId().intValue())))
            .andExpect(jsonPath("$.[*].ibgeId").value(hasItem(DEFAULT_IBGE_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF.toString())))
            .andExpect(jsonPath("$.[*].capital").value(hasItem(DEFAULT_CAPITAL.booleanValue())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].noAcents").value(hasItem(DEFAULT_NO_ACENTS.toString())))
            .andExpect(jsonPath("$.[*].alternativeNames").value(hasItem(DEFAULT_ALTERNATIVE_NAMES.toString())))
            .andExpect(jsonPath("$.[*].microRegion").value(hasItem(DEFAULT_MICRO_REGION.toString())))
            .andExpect(jsonPath("$.[*].mesoRegion").value(hasItem(DEFAULT_MESO_REGION.toString())));
    }

    @Test
    @Transactional
    public void getCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get the city
        restCityMockMvc.perform(get("/api/cities/{id}", city.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(city.getId().intValue()))
            .andExpect(jsonPath("$.ibgeId").value(DEFAULT_IBGE_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.uf").value(DEFAULT_UF.toString()))
            .andExpect(jsonPath("$.capital").value(DEFAULT_CAPITAL.booleanValue()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.doubleValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.noAcents").value(DEFAULT_NO_ACENTS.toString()))
            .andExpect(jsonPath("$.alternativeNames").value(DEFAULT_ALTERNATIVE_NAMES.toString()))
            .andExpect(jsonPath("$.microRegion").value(DEFAULT_MICRO_REGION.toString()))
            .andExpect(jsonPath("$.mesoRegion").value(DEFAULT_MESO_REGION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCity() throws Exception {
        // Get the city
        restCityMockMvc.perform(get("/api/cities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Update the city
        City updatedCity = cityRepository.findOne(city.getId());
        updatedCity
            .ibgeId(UPDATED_IBGE_ID)
            .name(UPDATED_NAME)
            .uf(UPDATED_UF)
            .capital(UPDATED_CAPITAL)
            .lon(UPDATED_LON)
            .lat(UPDATED_LAT)
            .noAcents(UPDATED_NO_ACENTS)
            .alternativeNames(UPDATED_ALTERNATIVE_NAMES)
            .microRegion(UPDATED_MICRO_REGION)
            .mesoRegion(UPDATED_MESO_REGION);

        restCityMockMvc.perform(put("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCity)))
            .andExpect(status().isOk());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getIbgeId()).isEqualTo(UPDATED_IBGE_ID);
        assertThat(testCity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCity.getUf()).isEqualTo(UPDATED_UF);
        assertThat(testCity.isCapital()).isEqualTo(UPDATED_CAPITAL);
        assertThat(testCity.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testCity.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testCity.getNoAcents()).isEqualTo(UPDATED_NO_ACENTS);
        assertThat(testCity.getAlternativeNames()).isEqualTo(UPDATED_ALTERNATIVE_NAMES);
        assertThat(testCity.getMicroRegion()).isEqualTo(UPDATED_MICRO_REGION);
        assertThat(testCity.getMesoRegion()).isEqualTo(UPDATED_MESO_REGION);
    }

    @Test
    @Transactional
    public void updateNonExistingCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Create the City

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCityMockMvc.perform(put("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(city)))
            .andExpect(status().isCreated());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);
        int databaseSizeBeforeDelete = cityRepository.findAll().size();

        // Get the city
        restCityMockMvc.perform(delete("/api/cities/{id}", city.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(City.class);
        City city1 = new City();
        city1.setId(1L);
        City city2 = new City();
        city2.setId(city1.getId());
        assertThat(city1).isEqualTo(city2);
        city2.setId(2L);
        assertThat(city1).isNotEqualTo(city2);
        city1.setId(null);
        assertThat(city1).isNotEqualTo(city2);
    }
}
