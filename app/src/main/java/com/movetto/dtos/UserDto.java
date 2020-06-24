package com.movetto.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "phone",
        "customer",
        "partner",
        "roles",
        "directions",
        "registrationDate",
        "active"
})
public class UserDto extends UserMinimumDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("customer")
    private CustomerDto customer;
    @JsonProperty("partner")
    private PartnerDto partner;

    @JsonProperty("roles")
    private Set<Role> roles = null;
    @JsonProperty("directions")
    private Set<DirectionDto> directions = null;

    @JsonProperty("registrationDate")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime registrationDate;
    @JsonProperty("active")
    private boolean active;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public UserDto() {
        this.active = true;
        this.roles = new HashSet<Role>();
        this.directions = new HashSet<DirectionDto>();
    }

    public UserDto(String displayName, String email, String uid, String phone) {
        super(displayName,email,uid);
        this.phone = phone;
    }

    @JsonProperty("phone")
    public Object getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonProperty("customer")
    public CustomerDto getCustomer() {
        return customer;
    }

    @JsonProperty("customer")
    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    @JsonProperty("partner")
    public PartnerDto getPartner() {
        return partner;
    }

    @JsonProperty("partner")
    public void setPartner(PartnerDto partner) {
        this.partner = partner;
    }

    @JsonProperty("roles")
    public Set<Role> getRoles() {
        return roles;
    }

    @JsonProperty("roles")
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @JsonProperty("directions")
    public Set<DirectionDto> getDirections() {
        return directions;
    }

    @JsonProperty("directions")
    public void setDirections(Set<DirectionDto> directions) {
        this.directions = directions;
    }

    @JsonProperty("registrationDate")
    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    @JsonProperty("registrationDate")
    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @JsonProperty("active")
    public boolean isActive() {
        return active;
    }

    @JsonProperty("active")
    public void setActive(boolean active) {
        this.active = active;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + getId() +
                ", displayName='" + getDisplayName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", uid='" + getUid() + '\'' +
                ", phone='" + phone + '\'' +
                ", customer=" + customer +
                ", partner=" + partner +
                ", roles=" + roles +
                ", directions=" + directions +
                ", registrationDate=" + registrationDate +
                ", active=" + active +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
