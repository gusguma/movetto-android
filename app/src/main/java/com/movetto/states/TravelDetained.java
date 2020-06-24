package com.movetto.states;

import android.view.View;

import com.movetto.dtos.TravelStatus;

public class TravelDetained extends TravelState {

    private static final String DETAINED = "Retenido";
    private static final String TRANSIT = "En Transito";
    private static final String FINISH = "Finalizar";

    public TravelDetained(TravelContext context) {
        context.chipStatus.setText(DETAINED);
        context.greenButton.setText(TRANSIT);
        context.redButton.setText(FINISH);
        transit(context);
        cancel(context);
    }

    @Override
    public TravelStatus state() {
        return TravelStatus.DETAINED;
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

    @Override
    public void cancel(TravelContext context) {
        context.redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.data.putSerializable("newStatus", TravelStatus.FINISHED);
                context.navigate();
            }
        });
    }
}
