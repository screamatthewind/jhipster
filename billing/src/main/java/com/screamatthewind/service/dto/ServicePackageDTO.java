package com.screamatthewind.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ServicePackage entity.
 */
public class ServicePackageDTO implements Serializable {

    private Long id;

    private String name;

    private Float price;

    private Boolean enabled;

    private ZonedDateTime addDate;

    private Long channelId;

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

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServicePackageDTO servicePackageDTO = (ServicePackageDTO) o;
        if(servicePackageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), servicePackageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServicePackageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", enabled='" + isEnabled() + "'" +
            ", addDate='" + getAddDate() + "'" +
            "}";
    }
}
