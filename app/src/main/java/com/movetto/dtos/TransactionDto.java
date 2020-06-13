package com.movetto.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
@JsonPropertyOrder({
        "id",
        "amount",
        "status",
        "registrationDate",
        "active"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PaymentDto.class, name = "Payment"),
        @JsonSubTypes.Type(value = DepositDto.class, name = "Deposit"),
        @JsonSubTypes.Type(value = ChargeDto.class, name = "Charge")
})
public abstract class TransactionDto {
    @JsonProperty("id")
    private int id;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("status")
    private TransactionStatus status;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("registrationDate")
    private LocalDateTime registrationDate;
    @JsonProperty("active")
    private boolean active;

    public TransactionDto(){
        this.registrationDate = LocalDateTime.now();
        this.active = true;
    }

    public TransactionDto(double amount) {
        this();
        this.status = TransactionStatus.CREATED;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
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
        return "TransactionDto{" +
                "id=" + id +
                ", amount=" + amount +
                ", status=" + status +
                ", registrationDate=" + registrationDate +
                ", active=" + active +
                '}';
    }
}
