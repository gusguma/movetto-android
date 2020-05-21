package com.movetto.activities.ui.wallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movetto.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WalletCardAddUpdateFragment extends Fragment {

    public WalletCardAddUpdateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet_card_add_update, container, false);
    }
}
