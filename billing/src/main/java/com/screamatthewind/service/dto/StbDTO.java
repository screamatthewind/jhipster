package com.screamatthewind.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Stb entity.
 */
public class StbDTO implements Serializable {

    private Long id;

    @NotNull
    private String mac;

    private String ipAddress;

    private Boolean enabled;

    private ZonedDateTime addDate;

    private Long customerId;

    private Long tariffId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getTariffId() {
        return tariffId;
    }

    public void setTariffId(Long tariffId) {
        this.tariffId = tariffId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StbDTO stbDTO = (StbDTO) o;
        if(stbDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stbDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StbDTO{" +
            "id=" + getId() +
            ", mac='" + getMac() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", addDate='" + getAddDate() + "'" +
            "}";
    }
}
