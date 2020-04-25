package com.movetto.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.movetto.activities.ui.account.AccountPartnerDirectionFragment;
import com.movetto.activities.ui.account.AccountPartnerVehiclesFragment;
import com.movetto.activities.ui.account.AccountPartnerDataFragment;

public class AccountPartnerAdapter extends FragmentPagerAdapter {

    private static final String DATA = "Mis Datos";
    private static final String DIRECTION = "Dirección";
    private static final String VEHICLES = "Vehiculos";

    private int behavior;
    private Context context;
    private String[] titles;

    public AccountPartnerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.behavior = behavior;
        this.context = context;
        titles = new String[]{DATA,DIRECTION, VEHICLES};
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AccountPartnerDataFragment();
            case 1:
                return new AccountPartnerDirectionFragment();
            case 2:
                return new AccountPartnerVehiclesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return behavior;
    }
}
