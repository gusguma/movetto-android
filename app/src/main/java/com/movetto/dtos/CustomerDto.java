package com.movetto.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customerId"
})
public class CustomerDto {

    @JsonProperty("customerId")
    private String customerId;

    public CustomerDto() {
        //Empty for Serializer.
    }

    public CustomerDto(UserDto user) {
        this.customerId = user.getCustomer().customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
