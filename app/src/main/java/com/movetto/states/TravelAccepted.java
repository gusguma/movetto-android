package com.movetto.states;

import android.view.View;

import com.movetto.dtos.TravelStatus;

public class TravelAccepted extends TravelState {

    private static final String ACCEPT = "Aceptado";
    private static final String COLLECT = "Recoger";
    private static final String CANCEL = "Cancelar";

    public TravelAccepted(TravelContext context) {
        context.chipStatus.setText(ACCEPT);
        context.greenButton.setText(COLLECT);
        context.redButton.setText(CANCEL);
        pick(context);
        cancel(context);
    }

    @Override
    public TravelStatus state() {
        return TravelStatus.ACCEPTED;
    }

    @Override
    public void pick(TravelContext context) {
        context.greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.data.putSerializable("newStatus", TravelStatus.PICKED_UP);
                context.navigate();
            }
        });
    }

    @Override
    public void cancel(TravelContext context) {
        context.redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.data.putSerializable("newStatus", TravelStatus.PAID);
                context.navigate();
            }
        });
    }
}
