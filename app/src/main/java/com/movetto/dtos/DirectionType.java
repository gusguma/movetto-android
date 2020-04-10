package com.movetto.dtos;

public enum DirectionType {

    CUSTOMER,PARTNER,START,FINISH;

    public String directionName() {
        return "DIRECTION_" + this.toString();
    }
}
