package com.movetto.activities.ui.wallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.movetto.R;
import com.movetto.view_models.ShipmentViewModel;
import com.movetto.view_models.WalletViewModel;

public class WalletCardFragment extends Fragment {

    private View root;
    private WalletViewModel walletViewModel;
    private Bundle data;
    private FloatingActionButton addCardButton;

    public WalletCardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet_card, container, false);
    }
}
