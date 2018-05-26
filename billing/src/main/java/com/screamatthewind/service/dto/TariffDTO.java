package com.screamatthewind.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Tariff entity.
 */
public class TariffDTO implements Serializable {

    private Long id;

    private String name;

    private Float price;

    private Integer period;

    private Boolean enabled;

    private ZonedDateTime addDate;

    private Long periodTypeId;

    private Long servicePackageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public ZonedDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(ZonedDateTime addDate) {
        this.addDate = addDate;
    }

    public Long getPeriodTypeId() {
        return periodTypeId;
    }

    public void setPeriodTypeId(Long periodTypeId) {
        this.periodTypeId = periodTypeId;
    }

    public Long getServicePackageId() {
        return servicePackageId;
    }

    public void setServicePackageId(Long servicePackageId) {
        this.servicePackageId = servicePackageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TariffDTO tariffDTO = (TariffDTO) o;
        if(tariffDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tariffDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TariffDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", period=" + getPeriod() +
            ", enabled='" + isEnabled() + "'" +
            ", addDate='" + getAddDate() + "'" +
            "}";
    }
}
