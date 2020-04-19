package com.movetto.activities.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movetto.R;

public class AccountPartnerDirectionFragment extends Fragment {

    public AccountPartnerDirectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_partner_direction, container, false);
    }
}
