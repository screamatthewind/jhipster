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
 * Agent entity.
 */
@ApiModel(description = "Agent entity.")
@Entity
@Table(name = "agent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "address_1", nullable = false)
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    @NotNull
    @Column(name = "state", nullable = false)
    private String state;

    @NotNull
    @Column(name = "zip", nullable = false)
    private String zip;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "credit")
    private Float credit;

    @Column(name = "add_date")
    private ZonedDateTime addDate;

    @ManyToOne
    private Template template;

    
    public Agent() {}
    
    public Agent(String accountNumber, String name, String address1, String address2, String city, String state,
			String zip, String phone, String email, String username, String password, Boolean enabled, Float credit,
			Template template) {
    	
		super();
		this.accountNumber = accountNumber;
		this.name = name;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.credit = credit;
		this.template = template;
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Agent accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public Agent name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public Agent address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public Agent address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public Agent city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Agent state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public Agent zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public Agent phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public Agent email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public Agent username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Agent password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public Agent enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Float getCredit() {
        return credit;
    }

    public Agent credit(Float credit) {
        this.credit = credit;
        return this;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public ZonedDateTime getAddDate() {
        return addDate;
    }

    public Agent addDate(ZonedDateTime addDate) {
        this.addDate = addDate;
        return this;
    }

    public void setAddDate(ZonedDateTime addDate) {
        this.addDate = addDate;
    }

    public Template getTemplate() {
        return template;
    }

    public Agent template(Template template) {
        this.template = template;
        return this;
    }

    public void setTemplate(Template template) {
        this.template = template;
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
        Agent agent = (Agent) o;
        if (agent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Agent{" +
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
