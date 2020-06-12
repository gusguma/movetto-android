package com.movetto.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.movetto.activities.ui.travellers.TravellerAvailableListAvailableFragment;
import com.movetto.activities.ui.travellers.TravellerAvailableListFinishedFragment;
import com.movetto.activities.ui.travellers.TravellerAvailableListPendingFragment;

public class TravelAvailableListAdapter extends FragmentStatePagerAdapter {

    private static final String AVAILABLE = "Disponibles";
    private static final String PENDING = "Pendientes";
    private static final String DELIVERED = "Realizados";

    private int behavior;
    private Context context;
    private String[] titles;
    private Bundle data;

    public TravelAvailableListAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.behavior = behavior;
        this.context = context;
        titles = new String[]{AVAILABLE,PENDING, DELIVERED};
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
                fragment = new TravellerAvailableListAvailableFragment();
                fragment.setArguments(data);
                return fragment;
            case 1:
                fragment = new TravellerAvailableListPendingFragment();
                fragment.setArguments(data);
                return fragment;
            case 2:
                fragment = new TravellerAvailableListFinishedFragment();
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
