package com.screamatthewind.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The ServicePackages entity.
 */
@ApiModel(description = "The ServicePackages entity.")
@Entity
@Table(name = "service_package")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ServicePackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Float price;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "add_date")
    private ZonedDateTime addDate;

    @ManyToOne
    private Channel channel;

    @OneToMany(mappedBy = "servicePackage")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tariff> tariffs = new HashSet<>();

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

    public ServicePackage name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public ServicePackage price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public ServicePackage enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public ZonedDateTime getAddDate() {
        return addDate;
    }

    public ServicePackage addDate(ZonedDateTime addDate) {
        this.addDate = addDate;
        return this;
    }

    public void setAddDate(ZonedDateTime addDate) {
        this.addDate = addDate;
    }

    public Channel getChannel() {
        return channel;
    }

    public ServicePackage channel(Channel channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Set<Tariff> getTariffs() {
        return tariffs;
    }

    public ServicePackage tariffs(Set<Tariff> tariffs) {
        this.tariffs = tariffs;
        return this;
    }

    public ServicePackage addTariff(Tariff tariff) {
        this.tariffs.add(tariff);
        tariff.setServicePackage(this);
        return this;
    }

    public ServicePackage removeTariff(Tariff tariff) {
        this.tariffs.remove(tariff);
        tariff.setServicePackage(null);
        return this;
    }

    public void setTariffs(Set<Tariff> tariffs) {
        this.tariffs = tariffs;
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
        ServicePackage servicePackage = (ServicePackage) o;
        if (servicePackage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), servicePackage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServicePackage{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", enabled='" + isEnabled() + "'" +
            ", addDate='" + getAddDate() + "'" +
            "}";
    }
}
