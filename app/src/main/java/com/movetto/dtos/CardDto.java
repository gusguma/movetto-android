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
        "cardNumber",
        "expireMonth",
        "expireYear",
        "cvc",
        "user",
        "registrationDate",
        "active"
})
public class CardDto {
    @JsonProperty("id")
    private int id;
    @JsonProperty("cardNumber")
    private String cardNumber;
    @JsonProperty("expireMonth")
    private String expireMonth;
    @JsonProperty("expireYear")
    private String expireYear;
    @JsonProperty("cvc")
    private String cvc;
    @JsonProperty("user")
    private UserDto user;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("registrationDate")
    private LocalDateTime registrationDate;
    @JsonProperty("active")
    private boolean active;

    public CardDto (){
        this.registrationDate = LocalDateTime.now();
        this.active = true;
    }

    public CardDto (String cardNumber, String expireMonth,
                    String expireYear, String cvc, UserDto user){
        this();
        this.cardNumber = cardNumber;
        this.expireMonth = expireMonth;
        this.expireYear = expireYear;
        this.cvc = cvc;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpireMonth() {
        return expireMonth;
    }

    public void setExpireMonth(String expireMonth) {
        this.expireMonth = expireMonth;
    }

    public String getExpireYear() {
        return expireYear;
    }

    public void setExpireYear(String expireYear) {
        this.expireYear = expireYear;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
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
        return "CardDto{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", expireMonth='" + expireMonth + '\'' +
                ", expireYear='" + expireYear + '\'' +
                ", cvc='" + cvc + '\'' +
                ", user=" + user +
                ", registrationDate=" + registrationDate +
                ", active=" + active +
                '}';
    }
}
