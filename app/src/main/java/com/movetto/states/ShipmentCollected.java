package com.movetto.states;

import android.view.View;

import com.movetto.dtos.ShipmentStatus;

public class ShipmentCollected extends ShipmentState {

    private static final String COLLECTED = "Recogido";
    private static final String TRANSIT = "En Transito";

    public ShipmentCollected(ShipmentContext context) {
        context.chipStatus.setText(COLLECTED);
        context.greenButton.setText(TRANSIT);
        context.redButton.setVisibility(View.GONE);
        transit(context);
    }

    @Override
    public ShipmentStatus state() {
        return ShipmentStatus.COLLECTED;
    }

    @Override
    public void transit(ShipmentContext context) {
        context.greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.data.putSerializable("newStatus", ShipmentStatus.TRANSIT);
                context.navigate();
            }
        });
    }
}
