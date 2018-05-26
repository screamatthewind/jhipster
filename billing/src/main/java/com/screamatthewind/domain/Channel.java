package com.screamatthewind.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The Channel entity.
 */
@ApiModel(description = "The Channel entity.")
@Entity
@Table(name = "channel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_number", nullable = false)
    private Integer number;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "jhi_link", nullable = false)
    private String link;

    @Column(name = "epg_correction")
    private Integer epgCorrection;

    @Column(name = "xmltv_id")
    private Integer xmltvId;

    @Column(name = "add_date")
    private ZonedDateTime addDate;

    @OneToMany(mappedBy = "channel")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServicePackage> servicePackages = new HashSet<>();

    @ManyToOne
    private Genre genre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public Channel number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public Channel name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public Channel link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getEpgCorrection() {
        return epgCorrection;
    }

    public Channel epgCorrection(Integer epgCorrection) {
        this.epgCorrection = epgCorrection;
        return this;
    }

    public void setEpgCorrection(Integer epgCorrection) {
        this.epgCorrection = epgCorrection;
    }

    public Integer getXmltvId() {
        return xmltvId;
    }

    public Channel xmltvId(Integer xmltvId) {
        this.xmltvId = xmltvId;
        return this;
    }

    public void setXmltvId(Integer xmltvId) {
        this.xmltvId = xmltvId;
    }

    public ZonedDateTime getAddDate() {
        return addDate;
    }

    public Channel addDate(ZonedDateTime addDate) {
        this.addDate = addDate;
        return this;
    }

    public void setAddDate(ZonedDateTime addDate) {
        this.addDate = addDate;
    }

    public Set<ServicePackage> getServicePackages() {
        return servicePackages;
    }

    public Channel servicePackages(Set<ServicePackage> servicePackages) {
        this.servicePackages = servicePackages;
        return this;
    }

    public Channel addServicePackage(ServicePackage servicePackage) {
        this.servicePackages.add(servicePackage);
        servicePackage.setChannel(this);
        return this;
    }

    public Channel removeServicePackage(ServicePackage servicePackage) {
        this.servicePackages.remove(servicePackage);
        servicePackage.setChannel(null);
        return this;
    }

    public void setServicePackages(Set<ServicePackage> servicePackages) {
        this.servicePackages = servicePackages;
    }

    public Genre getGenre() {
        return genre;
    }

    public Channel genre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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
        Channel channel = (Channel) o;
        if (channel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), channel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Channel{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", name='" + getName() + "'" +
            ", link='" + getLink() + "'" +
            ", epgCorrection=" + getEpgCorrection() +
            ", xmltvId=" + getXmltvId() +
            ", addDate='" + getAddDate() + "'" +
            "}";
    }
}
