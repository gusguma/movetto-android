package com.movetto.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "card"
})
public class DepositDto extends TransactionDto {

    @JsonProperty("card")
    private CardDto card;

    public DepositDto(){
        super();
    }

    public DepositDto(double amount, CardDto card){
        super(amount);
        this.card = card;
    }

    public CardDto getCard() {
        return card;
    }

    public void setCard(CardDto card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "DepositDto{" +
                "card=" + card +
                '}';
    }
}
