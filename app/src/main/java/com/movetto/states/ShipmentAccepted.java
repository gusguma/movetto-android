package com.movetto.states;

import android.view.View;

import com.movetto.dtos.ShipmentStatus;

public class ShipmentAccepted extends ShipmentState {

    private static final String ACCEPT = "Aceptado";
    private static final String COLLECT = "Recoger";
    private static final String CANCEL = "Cancelar";

    public ShipmentAccepted(ShipmentContext context) {
        context.chipStatus.setText(ACCEPT);
        context.greenButton.setText(COLLECT);
        context.redButton.setText(CANCEL);
        collect(context);
        cancel(context);
    }

    @Override
    public ShipmentStatus state() {
        return ShipmentStatus.ACCEPTED;
    }

    @Override
    public void collect(ShipmentContext context) {
        context.greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.data.putSerializable("newStatus", ShipmentStatus.COLLECTED);
                context.navigate();
            }
        });
    }

    @Override
    public void cancel(ShipmentContext context) {
        context.redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.data.putSerializable("newStatus", ShipmentStatus.PAID);
                context.navigate();
            }
        });
    }

}
