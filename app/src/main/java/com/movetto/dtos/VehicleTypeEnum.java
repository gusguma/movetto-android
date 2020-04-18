package com.movetto.dtos;

public enum VehicleTypeEnum {
    BIKE,MOTORCYCLE,CAR,VAN;

    public String vehicleTypeName(){
        return "VEHICLE_TYPE_" + this.toString();
    }
}
