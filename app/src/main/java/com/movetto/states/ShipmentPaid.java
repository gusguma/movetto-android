package com.movetto.states;

import android.view.View;

import androidx.navigation.Navigation;

import com.movetto.R;
import com.movetto.dtos.ShipmentStatus;

public class ShipmentPaid extends ShipmentState {

    private static final String PAID = "Pagado";
    private static final String ACCEPT = "Aceptar";

    public ShipmentPaid(ShipmentContext context) {
        context.chipStatus.setText(PAID);
        context.greenButton.setText(ACCEPT);
        context.redButton.setVisibility(View.GONE);
        accept(context);
    }

    @Override
    public ShipmentStatus state() {
        return ShipmentStatus.PAID;
    }

    @Override
    public void accept(ShipmentContext context) {
        context.greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.data.putSerializable("newStatus", ShipmentStatus.ACCEPTED);
                context.navigate();
            }
        });
    }

}
