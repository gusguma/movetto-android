package com.movetto.states;

import android.view.View;

import com.movetto.dtos.ShipmentStatus;

public class ShipmentDetained extends ShipmentState {

    private static final String DETAINED = "Retenido";
    private static final String TRANSIT = "En Transito";
    private static final String FINISH = "Finalizar";

    public ShipmentDetained(ShipmentContext context) {
        context.chipStatus.setText(DETAINED);
        context.greenButton.setText(TRANSIT);
        context.redButton.setText(FINISH);
        transit(context);
        cancel(context);
    }

    @Override
    public ShipmentStatus state() {
        return ShipmentStatus.DETAINED;
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

    @Override
    public void cancel(ShipmentContext context) {
        context.redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.data.putSerializable("newStatus", ShipmentStatus.FINISHED);
                context.navigate();
            }
        });
    }
}
