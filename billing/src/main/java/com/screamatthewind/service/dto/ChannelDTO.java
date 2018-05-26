package com.screamatthewind.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Channel entity.
 */
public class ChannelDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer number;

    @NotNull
    private String name;

    @NotNull
    private String link;

    private Integer epgCorrection;

    private Integer xmltvId;

    private ZonedDateTime addDate;

    private Long genreId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getEpgCorrection() {
        return epgCorrection;
    }

    public void setEpgCorrection(Integer epgCorrection) {
        this.epgCorrection = epgCorrection;
    }

    public Integer getXmltvId() {
        return xmltvId;
    }

    public void setXmltvId(Integer xmltvId) {
        this.xmltvId = xmltvId;
    }

    public ZonedDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(ZonedDateTime addDate) {
        this.addDate = addDate;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChannelDTO channelDTO = (ChannelDTO) o;
        if(channelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), channelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChannelDTO{" +
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
