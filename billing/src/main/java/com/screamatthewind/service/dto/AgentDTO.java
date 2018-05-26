package com.screamatthewind.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Agent entity.
 */
public class AgentDTO implements Serializable {

    private Long id;

    @NotNull
    private String accountNumber;

    @NotNull
    private String name;

    @NotNull
    private String address1;

    private String address2;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String zip;

    @NotNull
    private String phone;

    private String email;

    private String username;

    private String password;

    private Boolean enabled;

    private Float credit;

    private ZonedDateTime addDate;

    private TemplateDTO templateDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public ZonedDateTime getAddDate() {
        return addDate;
    }

    public void setAddDate(ZonedDateTime addDate) {
        this.addDate = addDate;
    }

    public TemplateDTO getTemplateDTO() {
        return templateDTO;
    }

    public void setTemplateDTO(TemplateDTO templateDTO) {
        this.templateDTO = templateDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AgentDTO agentDTO = (AgentDTO) o;
        if(agentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AgentDTO{" +
            "id=" + getId() +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", name='" + getName() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", zip='" + getZip() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", credit=" + getCredit() +
            ", addDate='" + getAddDate() + "'" +
            "}";
    }
}
