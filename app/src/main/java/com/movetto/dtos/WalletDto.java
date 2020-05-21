package com.movetto.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "balance",
        "user",
        "transactions",
        "registrationDate",
        "active"
})
public class WalletDto {
    @JsonProperty("registrationDate")
    private int id;
    @JsonProperty("registrationDate")
    private double balance;
    @JsonProperty("registrationDate")
    private UserDto user;
    @JsonProperty("registrationDate")
    private Set<TransactionDto> transactions;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("registrationDate")
    private LocalDateTime registrationDate;
    @JsonProperty("registrationDate")
    private boolean active;

    public WalletDto () {
        this.balance = 0;
        this.registrationDate = LocalDateTime.now();
        this.active = true;
    }

    public WalletDto (UserDto user) {
        this();
        this.user = user;
        this.transactions = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Set<TransactionDto> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionDto> transactions) {
        this.transactions = transactions;
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
        return "WalletDto{" +
                "id=" + id +
                ", balance=" + balance +
                ", user=" + user +
                ", transactions=" + transactions +
                ", registrationDate=" + registrationDate +
                ", active=" + active +
                '}';
    }
}
