package com.movetto.dtos;

import java.time.LocalDateTime;
import java.util.HashMap;
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
        "displayName",
        "email",
        "uid",
        "phone",
        "customer",
        "partner",
        "roles",
        "directions",
        "vehicles",
        "registrationDate",
        "active"
})
public class UserDto {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("uid")
    private String uid;
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
    @JsonProperty("vehicles")
    private Set<VehicleDto> vehicles = null;
    @JsonProperty("registrationDate")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime registrationDate;
    @JsonProperty("active")
    private Boolean active;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public UserDto() {
        //Empty for Serializer.
    }

    public UserDto(String displayName, String email, String uid, String phone) {
        this.displayName = displayName;
        this.email = email;
        this.uid = uid;
        this.phone = phone;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("displayName")
    public String getDisplayName() {
        return displayName;
    }

    @JsonProperty("displayName")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("uid")
    public String getUid() {
        return uid;
    }

    @JsonProperty("uid")
    public void setUid(String uid) {
        this.uid = uid;
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

    @JsonProperty("vehicles")
    public Set<VehicleDto> getVehicles() {
        return vehicles;
    }

    @JsonProperty("vehicles")
    public void setVehicles(Set<VehicleDto> vehicles) {
        this.vehicles = vehicles;
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
    public Boolean isActive() {
        return active;
    }

    @JsonProperty("active")
    public void setActive(Boolean active) {
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

}
