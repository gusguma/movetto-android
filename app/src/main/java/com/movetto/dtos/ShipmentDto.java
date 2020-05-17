package com.movetto.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "shipmentDatetimeLimit",
        "minimumPrice",
        "priceShipment",
        "status",
        "packages",
        "destinationUser"
})
public class ShipmentDto extends ServiceDto {
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("shipmentDatetimeLimit")
    private LocalDateTime shipmentDatetimeLimit;
    @JsonProperty("minimumPrice")
    private double minimumPrice;
    @JsonProperty("priceShipment")
    private double priceShipment;
    @JsonProperty("status")
    private ShipmentStatus status;
    @JsonProperty("packages")
    private Set<PackageDto> packages;
    @JsonProperty("destinationUser")
    private UserDto destinationUser;

    public ShipmentDto() {
        super();
        this.minimumPrice = 5.00;
        this.status = ShipmentStatus.SAVED;
    }

    public ShipmentDto(UserDto customer,DirectionDto start,DirectionDto finish, UserDto destinationUser) {
        super(customer,start,finish);
        this.destinationUser = destinationUser;
        this.minimumPrice = 5.00;
        this.status = ShipmentStatus.SAVED;
        this.shipmentDatetimeLimit = LocalDateTime.now().plusDays(5);
        this.packages = new HashSet<>();
        setShipmentPrice();
    }

    public LocalDateTime getShipmentDatetimeLimit() {
        return shipmentDatetimeLimit;
    }

    public void setShipmentDatetimeLimit(LocalDateTime shipmentDatetimeLimit) {
        this.shipmentDatetimeLimit = shipmentDatetimeLimit;
    }
    public void setShipmentPrice(){
        priceShipment = this.getMinimumPrice() + this.getPackages().stream()
                .mapToDouble(PackageDto::getPricePackage)
                .sum();
    }
    public double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public double getPriceShipment() {
        return priceShipment;
    }

    public void setPriceShipment(double priceShipment) {
        this.priceShipment = priceShipment;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public Set<PackageDto> getPackages() {
        return packages;
    }

    public void setPackages(Set<PackageDto> packages) {
        this.packages = packages;
    }

    public UserDto getDestinationUser() {
        return destinationUser;
    }

    public void setDestinationUser(UserDto destinationUser) {
        this.destinationUser = destinationUser;
    }

    @Override
    public String toString() {
        return "ShipmentDto{" +
                "shipmentDatetimeLimit=" + shipmentDatetimeLimit +
                ", minimumPrice=" + minimumPrice +
                ", priceShipment=" + priceShipment +
                ", status=" + status +
                ", packages=" + packages +
                ", destinationUser=" + destinationUser +
                '}';
    }
}
