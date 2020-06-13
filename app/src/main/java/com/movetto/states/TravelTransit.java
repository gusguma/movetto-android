package com.movetto.states;

import android.view.View;

import com.movetto.dtos.TravelStatus;

public class TravelTransit extends TravelState {

    private static final String TRANSIT = "En Transito";
    private static final String FINISH = "Finalizar";
    private static final String DETAIN = "Detener";


    public TravelTransit(TravelContext context) {
        context.chipStatus.setText(TRANSIT);
        context.greenButton.setText(FINISH);
        context.redButton.setText(DETAIN);
        finish(context);
        detain(context);
    }

    @Override
    public TravelStatus state() {
        return TravelStatus.TRANSIT;
    }

    @Override
    public void finish(TravelContext context) {
        context.greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.data.putSerializable("newStatus", TravelStatus.FINISHED);
                context.navigate();
            }
        });
    }

    @Override
    public void detain(TravelContext context) {
        context.redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.data.putSerializable("newStatus", TravelStatus.DETAINED);
                context.navigate();
            }
        });
    }
}
