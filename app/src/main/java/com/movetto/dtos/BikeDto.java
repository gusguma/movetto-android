package com.movetto.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "make",
        "description"
})
public class BikeDto extends VehicleTypeDto {

    @JsonProperty("make")
    private String make;
    @JsonProperty("description")
    private String description;

    public BikeDto() {
        //Empty for Serializer.
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
