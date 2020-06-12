package com.movetto.states;

import android.view.View;

import com.movetto.dtos.ShipmentStatus;

public class ShipmentDelivered extends ShipmentState {

    private static final String DELIVERED = "Entregado";
    private static final String FINISH = "Finalizar";

    public ShipmentDelivered(ShipmentContext context) {
        context.chipStatus.setText(DELIVERED);
        context.greenButton.setText(FINISH);
        context.redButton.setVisibility(View.GONE);
        finish(context);
    }

    @Override
    public ShipmentStatus state() {
        return ShipmentStatus.DELIVERED;
    }

    @Override
    public void finish(ShipmentContext context) {
        context.greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.data.putSerializable("newStatus", ShipmentStatus.FINISHED);
                context.navigate();
            }
        });
    }

}
