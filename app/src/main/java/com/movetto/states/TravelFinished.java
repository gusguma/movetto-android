package com.movetto.states;

import android.view.View;

import com.movetto.dtos.TravelStatus;

public class TravelFinished extends TravelState {

    private static final String FINISHED = "Finalizado";

    public TravelFinished(TravelContext context) {
        context.chipStatus.setText(FINISHED);
        context.greenButton.setVisibility(View.GONE);
        context.redButton.setVisibility(View.GONE);
    }

    @Override
    public TravelStatus state() {
        return TravelStatus.FINISHED;
    }
}
