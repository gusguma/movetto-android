package com.movetto.dtos;

public class ShipmentDto extends ServiceDto {

    public ShipmentDto(CustomerDto customer, PartnerDto partner, VehicleDto vehicle) {
        super(customer, partner, vehicle);
    }
}
