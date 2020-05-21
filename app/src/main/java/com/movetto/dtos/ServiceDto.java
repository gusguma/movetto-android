package com.movetto.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "description",
        "price",
        "customer",
        "partner",
        "startDirection",
        "endDirection",
        "vehicle",
        "registrationDate",
        "active"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TravelDto.class, name = "Travel"),
        @JsonSubTypes.Type(value = ShipmentDto.class, name = "Shipment")
})
public abstract class ServiceDto implements Serializable {
    @JsonProperty("id")
    private int id;
    @JsonProperty("description")
    private String description;
    @JsonProperty("price")
    private double price;
    @JsonProperty("customer")
    private UserDto customer;
    @JsonProperty("partner")
    private UserDto partner;
    @JsonProperty("startDirection")
    private DirectionDto startDirection;
    @JsonProperty("endDirection")
    private DirectionDto endDirection;
    @JsonProperty("vehicle")
    private VehicleDto vehicle;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("registrationDate")
    private LocalDateTime registrationDate;
    @JsonProperty("active")
    private boolean active;

    public ServiceDto() {
        this.registrationDate = LocalDateTime.now();
        this.active = true;
    }

    public ServiceDto(UserDto customer
            ,DirectionDto startDirection,DirectionDto endDirection){
        this();
        this.customer = customer;
        this.startDirection = startDirection;
        this.endDirection = endDirection;
    }

    public ServiceDto(UserDto customer, UserDto partner, VehicleDto vehicle){
        this.customer = customer;
        this.partner = partner;
        this.vehicle = vehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public UserDto getCustomer() {
        return customer;
    }

    public void setCustomer(UserDto customer) {
        this.customer = customer;
    }

    public UserDto getPartner() {
        return partner;
    }

    public void setPartner(UserDto partner) {
        this.partner = partner;
    }

    public DirectionDto getStartDirection() {
        return startDirection;
    }

    public void setStartDirection(DirectionDto startDirection) {
        this.startDirection = startDirection;
    }

    public DirectionDto getEndDirection() {
        return endDirection;
    }

    public void setEndDirection(DirectionDto endDirection) {
        this.endDirection = endDirection;
    }

    public VehicleDto getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDto vehicle) {
        this.vehicle = vehicle;
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
        return "ServiceDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", customer=" + customer +
                ", partner=" + partner +
                ", startDirection=" + startDirection +
                ", endDirection=" + endDirection +
                ", vehicle=" + vehicle +
                ", registrationDate=" + registrationDate +
                ", active=" + active +
                '}';
    }
}
