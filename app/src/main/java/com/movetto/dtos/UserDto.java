package com.movetto.dtos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
    private Object phone;
    @JsonProperty("customer")
    private Object customer;
    @JsonProperty("partner")
    private Object partner;
    @JsonProperty("roles")
    private List<String> roles = null;
    @JsonProperty("directions")
    private List<Object> directions = null;
    @JsonProperty("vehicles")
    private List<Object> vehicles = null;
    @JsonProperty("registrationDate")
    private String registrationDate;
    @JsonProperty("active")
    private Boolean active;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public UserDto() {
    }

    /**
     *
     * @param uid
     * @param partner
     * @param directions
     * @param phone
     * @param displayName
     * @param roles
     * @param registrationDate
     * @param vehicles
     * @param active
     * @param id
     * @param email
     * @param customer
     */
    public UserDto(Integer id, String displayName, String email, String uid, Object phone, Object customer, Object partner, List<String> roles, List<Object> directions, List<Object> vehicles, String registrationDate, Boolean active) {
        super();
        this.id = id;
        this.displayName = displayName;
        this.email = email;
        this.uid = uid;
        this.phone = phone;
        this.customer = customer;
        this.partner = partner;
        this.roles = roles;
        this.directions = directions;
        this.vehicles = vehicles;
        this.registrationDate = registrationDate;
        this.active = active;
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
    public void setPhone(Object phone) {
        this.phone = phone;
    }

    @JsonProperty("customer")
    public Object getCustomer() {
        return customer;
    }

    @JsonProperty("customer")
    public void setCustomer(Object customer) {
        this.customer = customer;
    }

    @JsonProperty("partner")
    public Object getPartner() {
        return partner;
    }

    @JsonProperty("partner")
    public void setPartner(Object partner) {
        this.partner = partner;
    }

    @JsonProperty("roles")
    public List<String> getRoles() {
        return roles;
    }

    @JsonProperty("roles")
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @JsonProperty("directions")
    public List<Object> getDirections() {
        return directions;
    }

    @JsonProperty("directions")
    public void setDirections(List<Object> directions) {
        this.directions = directions;
    }

    @JsonProperty("vehicles")
    public List<Object> getVehicles() {
        return vehicles;
    }

    @JsonProperty("vehicles")
    public void setVehicles(List<Object> vehicles) {
        this.vehicles = vehicles;
    }

    @JsonProperty("registrationDate")
    public String getRegistrationDate() {
        return registrationDate;
    }

    @JsonProperty("registrationDate")
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    @JsonProperty("active")
    public Boolean getActive() {
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
