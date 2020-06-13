package com.movetto.states;

import com.movetto.dtos.TravelStatus;

public class TravelSaved extends TravelState {

    private static final String SAVED = "Grabado";
    private static final String PAY = "Pagar";
    private static final String DELETE = "Eliminar";

    public TravelSaved(TravelContext context) {
        context.chipStatus.setText(SAVED);
        context.greenButton.setText(PAY);
        context.redButton.setText(DELETE);
        pay(context);
        cancel(context);
    }

    @Override
    public TravelStatus state() {
        return TravelStatus.SAVED;
    }

    @Override
    public void pay(TravelContext context) {
        //TODO
    }

    @Override
    public void cancel(TravelContext context) {
        //TODO
    }
}
