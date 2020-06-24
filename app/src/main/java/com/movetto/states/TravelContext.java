package com.movetto.states;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.navigation.Navigation;

import com.google.android.material.chip.Chip;
import com.movetto.R;
import com.movetto.dtos.TravelDto;
import com.movetto.dtos.TravelStatus;

import java.util.Formatter;

public class TravelContext {

    private static final int TRAVEL_HASH = 213;
    protected static final int TRAVEL = 2;

    private TravelState travelState;
    protected View root;
    protected Button greenButton;
    protected Button redButton;
    protected Chip chipStatus;
    protected TravelDto travel;
    protected Bundle data;

    public TravelContext(TravelDto travel, View root) {
        this.travel = travel;
        this.root = root;
        setComponents();
        setTravelState();
    }

    private void setComponents(){
        chipStatus = root.findViewById(R.id.travel_available_detail_status);
        greenButton = root.findViewById(R.id.travel_available_detail_green_button);
        redButton = root.findViewById(R.id.travel_available_detail_red_button);
        greenButton.setVisibility(View.VISIBLE);
        redButton.setVisibility(View.VISIBLE);
        setBundle();
    }


    public void setTravelState() {
        if (travel.getStatus() == TravelStatus.SAVED) {
            travelState = new TravelSaved(this);
        }
        if (travel.getStatus() == TravelStatus.PAID) {
            travelState = new TravelPaid(this);
        }
        if (travel.getStatus() == TravelStatus.ACCEPTED) {
            travelState = new TravelAccepted(this);
        }
        if (travel.getStatus() == TravelStatus.PICKED_UP) {
            travelState = new TravelPickedUp(this);
        }
        if (travel.getStatus() == TravelStatus.TRANSIT) {
            travelState = new TravelTransit(this);
        }
        if (travel.getStatus() == TravelStatus.DETAINED) {
            travelState = new TravelDetained(this);
        }
        if (travel.getStatus() == TravelStatus.FINISHED) {
            travelState = new TravelFinished(this);
        }
    }

    protected String encodeTravelNumber(){
        Formatter formatter = new Formatter();
        int number = (travel.getId() * TRAVEL_HASH) - 23 ;
        return "" + formatter.format("%08d", number);
    }

    private void setBundle(){
        data = new Bundle();
        data.putString("serviceNumber", encodeTravelNumber());
        data.putDouble("amount", travel.getPriceTravel());
        data.putInt("serviceId", travel.getId());
        data.putInt("travelId", travel.getId());
        data.putInt("serviceType", TRAVEL);
    }

    protected void navigate(){
        Navigation.findNavController(root).navigate(
                R.id.action_nav_traveller_available_detail_to_nav_service_available_action_detail, data);
    }
}
