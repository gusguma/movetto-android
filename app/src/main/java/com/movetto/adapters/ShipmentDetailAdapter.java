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

public class ShipmentDetailAdapter extends FragmentStatePagerAdapter {

    private static final String PACKAGES = "Paquetes";
    private static final String PICK_UP = "Recogida";
    private static final String DELIVERY = "Entrega";

    private int behavior;
    private Context context;
    private String[] titles;
    private int shipmentId;

    public ShipmentDetailAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.behavior = behavior;
        this.context = context;
        titles = new String[]{PACKAGES,PICK_UP, DELIVERY};
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
                fragment = new ShipmentDetailPackagesFragment();
                fragment.setArguments(getBundle());
                return fragment;
            case 1:
                fragment = new ShipmentDetailStartFragment();
                fragment.setArguments(getBundle());
                return fragment;
            case 2:
                fragment = new ShipmentDetailEndFragment();
                fragment.setArguments(getBundle());
                return fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return behavior;
    }

    public void setShipmentId (int shipmentId){
        this.shipmentId = shipmentId;
    }

    private Bundle getBundle(){
        Bundle bundle = new Bundle();
        bundle.putInt("shipmentId", shipmentId);
        return bundle;
    }
}
