package com.movetto.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "make",
        "model",
        "description"
})
public class VanDto extends VehicleTypeDto {

    @JsonProperty("make")
    private String make;
    @JsonProperty("model")
    private String model;
    @JsonProperty("description")
    private String description;

    public VanDto() {
        setMaxVolume(1.5);
        setMaxWeight(160.0);
        setMaxLenght(200.0);
        setMaxWidth(200.0);
        setMaxHigh(200.0);
        this.make = "";
        this.model = "";
        this.description = "";
        this.setPlacesAvailable(4);
        this.setVehicleTypeEnum(VehicleTypeEnum.VAN);
    }

    public VanDto (String registration, String make, String model, String description){
        this();
        this.setRegistration(registration);
        this.make = make;
        this.model = model;
        this.description = description;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "VanDto{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
