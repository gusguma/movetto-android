package com.movetto.dtos;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import org.intellij.lang.annotations.Pattern;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "street",
        "postalCode",
        "city",
        "state",
        "country",
        "directionType",
        "coordinate",
        "registrationDate",
        "active"
})
public class DirectionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private int id;
    @JsonProperty("street")
    private String street;
    @Pattern(com.movetto.dtos.validations.Pattern.POSTAL_CODE)
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("country")
    private String country;
    @NonNull
    @JsonProperty("directionType")
    private DirectionType directionType;
    @JsonProperty("coordinate")
    private CoordinateDto coordinate;

    @JsonProperty("registrationDate")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime registrationDate;
    @JsonProperty("active")
    private boolean active;

    public DirectionDto() {
        this.coordinate = new CoordinateDto();
        this.registrationDate = LocalDateTime.now();
        this.active = true;
    }

    public DirectionDto(DirectionType directionType) {
        this();
        this.directionType = directionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public DirectionType getDirectionType() {
        return directionType;
    }

    public void setDirectionType(@NonNull DirectionType directionType) {
        this.directionType = directionType;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public CoordinateDto getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(CoordinateDto coordinate) {
        this.coordinate = coordinate;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "DirectionDto{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", directionType=" + directionType +
                ", coordinate=" + coordinate +
                ", registrationDate=" + registrationDate +
                ", active=" + active +
                '}';
    }
}
