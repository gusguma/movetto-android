package com.movetto.states;

import com.movetto.dtos.TravelStatus;

public abstract class TravelState {

    private static final String UNSUPPORTED = "Accion no Permitida";

    public abstract TravelStatus state();

    public void pay(TravelContext context) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }
    public void cancel(TravelContext context) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }
    public void accept(TravelContext context) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }
    public void pick(TravelContext context) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }
    public void transit(TravelContext context) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }
    public void detain(TravelContext context) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }
    public void finish(TravelContext context) {
        throw new UnsupportedOperationException(UNSUPPORTED);
    }
}
