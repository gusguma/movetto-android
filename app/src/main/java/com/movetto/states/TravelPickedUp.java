package com.movetto.states;

import android.view.View;

import com.movetto.dtos.TravelStatus;

public class TravelPickedUp extends TravelState {

    private static final String COLLECTED = "Recogido";
    private static final String TRANSIT = "En Transito";

    public TravelPickedUp(TravelContext context) {
        context.chipStatus.setText(COLLECTED);
        context.greenButton.setText(TRANSIT);
        context.redButton.setVisibility(View.GONE);
        transit(context);
    }

    @Override
    public TravelStatus state() {
        return TravelStatus.PICKED_UP;
    }

    @Override
    public void transit(TravelContext context) {
        context.greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.data.putSerializable("newStatus", TravelStatus.TRANSIT);
                context.navigate();
            }
        });
    }
}
