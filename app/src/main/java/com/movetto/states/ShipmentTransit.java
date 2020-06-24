package com.movetto.states;

import android.view.View;

import com.movetto.dtos.ShipmentStatus;

public class ShipmentTransit extends ShipmentState {

    private static final String TRANSIT = "En Transito";
    private static final String DELIVER = "Entregar";
    private static final String DETAIN = "Detener";

    public ShipmentTransit(ShipmentContext context) {
        context.chipStatus.setText(TRANSIT);
        context.greenButton.setText(DELIVER);
        context.redButton.setText(DETAIN);
        deliver(context);
        detain(context);
    }

    @Override
    public ShipmentStatus state() {
        return ShipmentStatus.TRANSIT;
    }

    @Override
    public void deliver(ShipmentContext context) {
        context.greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.data.putSerializable("newStatus", ShipmentStatus.DELIVERED);
                context.navigate();
            }
        });
    }

    @Override
    public void detain(ShipmentContext context) {
        context.redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.data.putSerializable("newStatus", ShipmentStatus.DETAINED);
                context.navigate();
            }
        });
    }

}
