package com.movetto.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "service"
})
public class ChargeDto extends TransactionDto {

    @JsonProperty("service")
    private ServiceDto service;

    public ChargeDto() {
        super();
    }

    public ChargeDto(double amount, ServiceDto service) {
        super(amount);
        this.service = service;
    }

    public ServiceDto getService() {
        return service;
    }

    public void setService(ServiceDto service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "ChargeDto{" +
                "service=" + service +
                '}';
    }
}
