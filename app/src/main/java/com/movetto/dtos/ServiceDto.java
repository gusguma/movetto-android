package com.movetto.dtos;

import java.time.LocalDateTime;

public abstract class ServiceDto {

    private int id;
    private DirectionDto startDirection;
    private DirectionDto endDirection;
    private CustomerDto customer;
    private PartnerDto partner;
    private VehicleDto vehicle;

    private LocalDateTime registrationDate;
    private boolean active;

    public ServiceDto(CustomerDto customer, PartnerDto partner, VehicleDto vehicle){
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

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public PartnerDto getPartner() {
        return partner;
    }

    public void setPartner(PartnerDto partner) {
        this.partner = partner;
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

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
