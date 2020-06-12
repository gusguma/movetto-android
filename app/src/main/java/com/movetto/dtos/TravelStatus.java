package com.movetto.dtos;

public enum TravelStatus {

    SAVED, PAID, ACCEPTED, TRANSIT, DETAINED, FINISHED, DELETED;

    public String shipmentStatusName(){
        return "SHIPMENT_STATUS_" + this.toString();
    }
}
