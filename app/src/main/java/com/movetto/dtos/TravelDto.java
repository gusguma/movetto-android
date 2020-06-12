package com.movetto.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "people",
        "distance",
        "minimumPrice",
        "priceHour",
        "priceKm",
        "priceTravel",
        "start",
        "end",
        "status"
})
public class TravelDto extends ServiceDto implements Serializable {
    @JsonProperty("people")
    private int people;
    @JsonProperty("distance")
    private double distance;
    @JsonProperty("minimumPrice")
    private double minimumPrice;
    @JsonProperty("priceHour")
    private double priceHour;
    @JsonProperty("priceKm")
    private double priceKm;
    @JsonProperty("priceTravel")
    private double priceTravel;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("start")
    private LocalDateTime start;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("end")
    private LocalDateTime end;
    @JsonProperty("status")
    private TravelStatus status;

    public TravelDto(){
        super();
        this.minimumPrice = 2.40;
        this.priceHour = 20.50;
        this.priceKm = 1.05;
        this.people = 1;
        this.status = TravelStatus.SAVED;
    }

    public TravelDto(UserDto customer,DirectionDto startDirection, DirectionDto endDirection) {
        super(customer, startDirection, endDirection);
        this.minimumPrice = 2.40;
        this.priceHour = 20.50;
        this.priceKm = 1.05;
        this.people = 1;
        this.status = TravelStatus.SAVED;
    }

    public long getTravelDuration(){
        return Duration.between(start, end).toHours();
    }

    private double getTravelPriceHours(){
        return getTravelDuration() * priceHour;
    }

    private double getTravelPriceDistance(){
        return distance * priceKm;
    }

    public void setTravelPrice(){
        priceTravel = Math.max(getTravelPriceDistance(), getTravelPriceHours());
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public double getPriceHour() {
        return priceHour;
    }

    public void setPriceHour(double priceHour) {
        this.priceHour = priceHour;
    }

    public double getPriceKm() {
        return priceKm;
    }

    public void setPriceKm(double priceKm) {
        this.priceKm = priceKm;
    }

    public double getPriceTravel() {
        return priceTravel;
    }

    public void setPriceTravel(double priceTravel) {
        this.priceTravel = priceTravel;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public TravelStatus getStatus() {
        return status;
    }

    public void setStatus(TravelStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TravelDto{" +
                "people=" + people +
                ", distance=" + distance +
                ", minimumPrice=" + minimumPrice +
                ", priceHour=" + priceHour +
                ", priceKm=" + priceKm +
                ", priceTravel=" + priceTravel +
                ", start=" + start +
                ", end=" + end +
                ", status=" + status +
                '}';
    }
}
