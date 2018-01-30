package com.senior.cidades.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A City.
 */
@Entity
@Table(name = "city")
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ibge_id")
    private Long ibgeId;

    @Column(name = "name")
    private String name;

    @Column(name = "uf")
    private String uf;

    @Column(name = "capital")
    private Boolean capital;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "no_acents")
    private String noAcents;

    @Column(name = "alternative_names")
    private String alternativeNames;

    @Column(name = "micro_region")
    private String microRegion;

    @Column(name = "meso_region")
    private String mesoRegion;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIbgeId() {
        return ibgeId;
    }

    public City ibgeId(Long ibgeId) {
        this.ibgeId = ibgeId;
        return this;
    }

    public void setIbgeId(Long ibgeId) {
        this.ibgeId = ibgeId;
    }

    public String getName() {
        return name;
    }

    public City name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUf() {
        return uf;
    }

    public City uf(String uf) {
        this.uf = uf;
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Boolean isCapital() {
        return capital;
    }

    public City capital(Boolean capital) {
        this.capital = capital;
        return this;
    }

    public void setCapital(Boolean capital) {
        this.capital = capital;
    }

    public Double getLon() {
        return lon;
    }

    public City lon(Double lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public City lat(Double lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getNoAcents() {
        return noAcents;
    }

    public City noAcents(String noAcents) {
        this.noAcents = noAcents;
        return this;
    }

    public void setNoAcents(String noAcents) {
        this.noAcents = noAcents;
    }

    public String getAlternativeNames() {
        return alternativeNames;
    }

    public City alternativeNames(String alternativeNames) {
        this.alternativeNames = alternativeNames;
        return this;
    }

    public void setAlternativeNames(String alternativeNames) {
        this.alternativeNames = alternativeNames;
    }

    public String getMicroRegion() {
        return microRegion;
    }

    public City microRegion(String microRegion) {
        this.microRegion = microRegion;
        return this;
    }

    public void setMicroRegion(String microRegion) {
        this.microRegion = microRegion;
    }

    public String getMesoRegion() {
        return mesoRegion;
    }

    public City mesoRegion(String mesoRegion) {
        this.mesoRegion = mesoRegion;
        return this;
    }

    public void setMesoRegion(String mesoRegion) {
        this.mesoRegion = mesoRegion;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        City city = (City) o;
        if (city.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), city.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "City{" +
            "id=" + getId() +
            ", ibgeId='" + getIbgeId() + "'" +
            ", name='" + getName() + "'" +
            ", uf='" + getUf() + "'" +
            ", capital='" + isCapital() + "'" +
            ", lon='" + getLon() + "'" +
            ", lat='" + getLat() + "'" +
            ", noAcents='" + getNoAcents() + "'" +
            ", alternativeNames='" + getAlternativeNames() + "'" +
            ", microRegion='" + getMicroRegion() + "'" +
            ", mesoRegion='" + getMesoRegion() + "'" +
            "}";
    }
}
