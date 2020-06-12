package com.movetto.states;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.movetto.dtos.ShipmentStatus;

import org.json.JSONException;

public abstract class ShipmentState {

    private static final String UNSUPPORTED = "Accion no Permitida";

    public abstract ShipmentStatus state();

    public void pay(ShipmentContext context) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }
    public void cancel(ShipmentContext context) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }
    public void accept(ShipmentContext context) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }
    public void collect(ShipmentContext context) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }
    public void transit(ShipmentContext context) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }
    public void deliver(ShipmentContext context) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }
    public void detain(ShipmentContext context) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }
    public void finish(ShipmentContext context) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }
}