package com.screamatthewind.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Stb entity.
 */
@ApiModel(description = "Stb entity.")
@Entity
@Table(name = "stb")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stb implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "mac", nullable = false)
    private String mac;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "add_date")
    private ZonedDateTime addDate;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Tariff tariff;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public Stb mac(String mac) {
        this.mac = mac;
        return this;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Stb ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Stb enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public ZonedDateTime getAddDate() {
        return addDate;
    }

    public Stb addDate(ZonedDateTime addDate) {
        this.addDate = addDate;
        return this;
    }

    public void setAddDate(ZonedDateTime addDate) {
        this.addDate = addDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Stb customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public Stb tariff(Tariff tariff) {
        this.tariff = tariff;
        return this;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
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
        Stb stb = (Stb) o;
        if (stb.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stb.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Stb{" +
            "id=" + getId() +
            ", mac='" + getMac() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", addDate='" + getAddDate() + "'" +
            "}";
    }
}
