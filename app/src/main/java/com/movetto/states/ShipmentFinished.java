package com.movetto.states;

import android.view.View;

import com.movetto.dtos.ShipmentStatus;

public class ShipmentFinished extends ShipmentState {

    private static final String FINISHED = "Finalizado";

    public ShipmentFinished(ShipmentContext context) {
        context.chipStatus.setText(FINISHED);
        context.greenButton.setVisibility(View.GONE);
        context.redButton.setVisibility(View.GONE);
    }

    @Override
    public ShipmentStatus state() {
        return ShipmentStatus.FINISHED;
    }
}
