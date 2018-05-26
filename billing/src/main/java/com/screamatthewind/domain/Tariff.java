package com.screamatthewind.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * The Tariff entity.
 */
@ApiModel(description = "The Tariff entity.")
@Entity
@Table(name = "tariff")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tariff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Float price;

    @Column(name = "period")
    private Integer period;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "add_date")
    private ZonedDateTime addDate;

    @ManyToOne
    private PeriodType periodType;

    @ManyToOne
    private ServicePackage servicePackage;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Tariff name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public Tariff price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getPeriod() {
        return period;
    }

    public Tariff period(Integer period) {
        this.period = period;
        return this;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Tariff enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public ZonedDateTime getAddDate() {
        return addDate;
    }

    public Tariff addDate(ZonedDateTime addDate) {
        this.addDate = addDate;
        return this;
    }

    public void setAddDate(ZonedDateTime addDate) {
        this.addDate = addDate;
    }

    public PeriodType getPeriodType() {
        return periodType;
    }

    public Tariff periodType(PeriodType periodType) {
        this.periodType = periodType;
        return this;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public Tariff servicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
        return this;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tariff tariff = (Tariff) o;
        if (tariff.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tariff.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tariff{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", period=" + getPeriod() +
            ", enabled='" + isEnabled() + "'" +
            ", addDate='" + getAddDate() + "'" +
            "}";
    }
}
