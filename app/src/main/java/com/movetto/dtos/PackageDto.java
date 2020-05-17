package com.movetto.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "weight",
        "width",
        "lenght",
        "high",
        "pricePackage",
        "weightVolume",
        "status",
        "registrationDate",
        "active",

})
public class PackageDto {

    @JsonProperty("id")
    private int id;
    @JsonProperty("weight")
    private double weight;
    @JsonProperty("width")
    private double width;
    @JsonProperty("lenght")
    private double lenght;
    @JsonProperty("high")
    private double high;
    @JsonProperty("pricePackage")
    private double pricePackage;
    @JsonProperty("weightVolume")
    private double weightVolume;
    @JsonProperty("status")
    private PackageStatus status;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("registrationDate")
    private LocalDateTime registrationDate;
    @JsonProperty("active")
    private boolean active;

    public PackageDto() {
        this.status = PackageStatus.SAVED;
        this.weightVolume = 333;
        this.registrationDate = LocalDateTime.now();
        this.active = true;
    }

    public PackageDto(double weight, double width, double lenght, double high){
        this();
        this.weight = weight;
        this.width = width;
        this.lenght = lenght;
        this.high = high;
        setPackagePrice();
    }

    public double getPackageVolume(){
        return (width * lenght * high) / 1000000;
    }

    public double getPackageWeightVolume(){
        return getPackageVolume() * weightVolume;
    }

    public void setPackagePrice(){
        if (getPackageWeightVolume() > weight){
            pricePackage = 1.00 * getPackageWeightVolume();
        } else {
            pricePackage = 1.00 * weight;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLenght() {
        return lenght;
    }

    public void setLenght(double lenght) {
        this.lenght = lenght;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getPricePackage() {
        return pricePackage;
    }

    public void setPricePackage(double pricePackage) {
        this.pricePackage = pricePackage;
    }

    public double getWeightVolume() {
        return weightVolume;
    }

    public void setWeightVolume(double weightVolume) {
        this.weightVolume = weightVolume;
    }

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "PackageDto{" +
                "id=" + id +
                ", weight=" + weight +
                ", width=" + width +
                ", lenght=" + lenght +
                ", high=" + high +
                ", pricePackage=" + pricePackage +
                ", weightVolume=" + weightVolume +
                ", status=" + status +
                ", registrationDate=" + registrationDate +
                ", active=" + active +
                '}';
    }
}
