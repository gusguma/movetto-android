package com.movetto.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "maxVolume",
        "maxWeight",
        "maxLenght",
        "maxWidth",
        "maxHigh",
        "placesAvailable",
        "vehicleTypeEnum"
})
public abstract class VehicleTypeDto extends VehicleDto {
    @JsonProperty("maxVolume")
    private double maxVolume; // m3
    @JsonProperty("maxWeight")
    private double maxWeight; // Kg
    @JsonProperty("maxLenght")
    private double maxLenght; // cm
    @JsonProperty("maxWidth")
    private double maxWidth; // cm
    @JsonProperty("maxHigh")
    private double maxHigh; // cm
    @JsonProperty("placesAvailable")
    private int placesAvailable;
    @JsonProperty("vehicleTypeEnum")
    private VehicleTypeEnum vehicleTypeEnum;


    public VehicleTypeDto() {
        //Empty for Serializer.
    }

    public VehicleTypeDto(UserDto user) {
        super();
    }

    public double getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(double maxVolume) {
        this.maxVolume = maxVolume;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public double getMaxLenght() {
        return maxLenght;
    }

    public void setMaxLenght(double maxLenght) {
        this.maxLenght = maxLenght;
    }

    public double getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(double maxWidth) {
        this.maxWidth = maxWidth;
    }

    public double getMaxHigh() {
        return maxHigh;
    }

    public void setMaxHigh(double maxHigh) {
        this.maxHigh = maxHigh;
    }

    public VehicleTypeEnum getVehicleTypeEnum() {
        return vehicleTypeEnum;
    }

    public void setVehicleTypeEnum(VehicleTypeEnum vehicleTypeEnum) {
        this.vehicleTypeEnum = vehicleTypeEnum;
    }

    public int getPlacesAvailable() {
        return placesAvailable;
    }

    public void setPlacesAvailable(int placesAvailable) {
        this.placesAvailable = placesAvailable;
    }

    @Override
    public String toString() {
        return "VehicleTypeDto{" +
                "maxVolume=" + maxVolume +
                ", maxWeight=" + maxWeight +
                ", maxLenght=" + maxLenght +
                ", maxWidth=" + maxWidth +
                ", maxHigh=" + maxHigh +
                ", placesAvailable=" + placesAvailable +
                ", vehicleTypeEnum=" + vehicleTypeEnum +
                '}';
    }
}
