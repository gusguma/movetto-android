package com.movetto.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "partnerId",
        "driverId",
        "vehicles"
})
public class PartnerDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("partnerId")
    private String partnerId;
    @JsonProperty("driverId")
    private String driverId;
    @JsonProperty("vehicles")
    private Set<VehicleDto> vehicles;

    public PartnerDto() {
        //Empty for Framework
        vehicles = new HashSet<VehicleDto>();
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public Set<VehicleDto> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<VehicleDto> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public String toString() {
        return "PartnerDto{" +
                "partnerId='" + partnerId + '\'' +
                ", driverId='" + driverId + '\'' +
                ", vehicles=" + vehicles +
                '}';
    }
}
