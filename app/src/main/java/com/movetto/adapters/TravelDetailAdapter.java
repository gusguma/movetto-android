package com.movetto.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.movetto.activities.ui.shipments.ShipmentDetailEndFragment;
import com.movetto.activities.ui.shipments.ShipmentDetailPackagesFragment;
import com.movetto.activities.ui.shipments.ShipmentDetailStartFragment;
import com.movetto.activities.ui.travel.TravelDetailDataFragment;
import com.movetto.activities.ui.travel.TravelDetailEndFragment;
import com.movetto.activities.ui.travel.TravelDetailStartFragment;

public class TravelDetailAdapter extends FragmentStatePagerAdapter {

    private static final String DETAIL = "Detalle";
    private static final String ORIGIN = "Recogida";
    private static final String DESTINATION = "Destino";

    private int behavior;
    private Context context;
    private String[] titles;
    private Bundle data;

    public TravelDetailAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.behavior = behavior;
        this.context = context;
        titles = new String[]{DETAIL,ORIGIN, DESTINATION};
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new TravelDetailDataFragment();
                fragment.setArguments(data);
                return fragment;
            case 1:
                fragment = new TravelDetailStartFragment();
                fragment.setArguments(data);
                return fragment;
            case 2:
                fragment = new TravelDetailEndFragment();
                fragment.setArguments(data);
                return fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return behavior;
    }

    public void setData (Bundle data){
        this.data = data;
    }
}
