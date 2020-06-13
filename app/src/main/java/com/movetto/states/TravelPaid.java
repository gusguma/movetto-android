package com.movetto.states;

import android.view.View;

import com.movetto.dtos.TravelStatus;

public class TravelPaid extends TravelState {

    private static final String PAID = "Pagado";
    private static final String ACCEPT = "Aceptar";

    public TravelPaid(TravelContext context) {
        context.chipStatus.setText(PAID);
        context.greenButton.setText(ACCEPT);
        context.redButton.setVisibility(View.GONE);
        accept(context);
    }

    @Override
    public TravelStatus state() {
        return TravelStatus.PAID;
    }

    @Override
    public void accept(TravelContext context) {
        context.greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.data.putSerializable("newStatus", TravelStatus.ACCEPTED);
                context.navigate();
            }
        });
    }
}
