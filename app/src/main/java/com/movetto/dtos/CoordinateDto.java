package com.movetto.dtos;

import java.io.Serializable;

public class CoordinateDto implements Serializable {

    private double latitude;
    private double longitude;

    public CoordinateDto(){
        latitude = 0.0;
        longitude = 0.0;
    }

    public CoordinateDto(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "CoordinateDto{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
