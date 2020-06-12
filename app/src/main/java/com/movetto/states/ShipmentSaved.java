package com.movetto.states;

import com.movetto.dtos.ShipmentStatus;

public class ShipmentSaved extends ShipmentState {

    private static final String SAVED = "Grabado";
    private static final String PAY = "Pagar";
    private static final String DELETE = "Eliminar";


    public ShipmentSaved(ShipmentContext context) {
        context.chipStatus.setText(SAVED);
        context.greenButton.setText(PAY);
        context.redButton.setText(DELETE);
        pay(context);
        cancel(context);
    }

    @Override
    public ShipmentStatus state() {
        return ShipmentStatus.SAVED;
    }

    @Override
    public void pay(ShipmentContext context) {
        //TODO
    }

    @Override
    public void cancel(ShipmentContext context) {
        //TODO
    }
}
