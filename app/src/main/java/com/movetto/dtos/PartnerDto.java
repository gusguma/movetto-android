package com.movetto.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "partnerId",
        "driverId"
})
public class PartnerDto {

    @JsonProperty("partnerId")
    private String partnerId;
    @JsonProperty("driverId")
    private String driverId;

    public PartnerDto(UserDto user) {
        this.partnerId = user.getPartner().partnerId;
        this.driverId = user.getPartner().driverId;
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
}
