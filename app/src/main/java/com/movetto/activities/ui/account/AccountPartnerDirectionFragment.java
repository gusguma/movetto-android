package com.movetto.activities.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.movetto.R;
import com.movetto.dtos.UserDto;
import com.movetto.view_models.PartnerViewModel;

public class AccountPartnerDirectionFragment extends Fragment {

    private View root;
    private PartnerViewModel partnerViewModel;
    private UserDto userOutputDto;
    private EditText street;
    private EditText postalCode;
    private EditText city;
    private EditText state;
    private EditText country;

    public AccountPartnerDirectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account_partner_direction, container, false);
    }


}
