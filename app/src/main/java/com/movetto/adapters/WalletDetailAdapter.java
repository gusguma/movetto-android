package com.movetto.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.movetto.activities.ui.wallet.WalletDetailCardsFragment;
import com.movetto.activities.ui.wallet.WalletDetailTransactionsFragment;

public class WalletDetailAdapter extends FragmentStatePagerAdapter {

    private static final String CARDS = "Tarjetas";
    private static final String TRANSACTIONS = "Movimientos";

    private int behavior;
    private Context context;
    private String[] titles;
    private int walletId;
    private int customerId;

    public WalletDetailAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.behavior = behavior;
        this.context = context;
        titles = new String[]{CARDS, TRANSACTIONS};
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
                fragment = new WalletDetailCardsFragment();
                fragment.setArguments(getBundle());
                return fragment;
            case 1:
                fragment = new WalletDetailTransactionsFragment();
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

    public void setWalletId (int walletId){
        this.walletId = walletId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    private Bundle getBundle(){
        Bundle bundle = new Bundle();
        bundle.putInt("walletId", walletId);
        bundle.putInt("customerId",customerId);
        return bundle;
    }
}
