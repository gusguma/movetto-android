package com.movetto.activities.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.movetto.R;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.PartnerDto;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.VehicleDto;
import com.movetto.view_models.PartnerViewModel;

import java.util.ArrayList;
import java.util.List;

public class AccountPartnerDataFragment extends Fragment {

    private View root;
    private PartnerViewModel partnerViewModel;
    private UserDto userInputDto;
    private EditText displayName;
    private EditText email;
    private EditText phone;
    private EditText partnerId;

    public AccountPartnerDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater,container);
        setComponents();
        setObservers();
        return root;
    }

    private void setViewModels() {
        partnerViewModel = new ViewModelProvider(this).get(PartnerViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_acount_partner_data, container, false);
    }

    private void setComponents() {
        displayName = root.findViewById(R.id.account_partner_name_edit);
        email = root.findViewById(R.id.account_partner_email_edit);
        email.setEnabled(false);
        phone = root.findViewById(R.id.account_partner_phone_edit);
        phone.setEnabled(false);
        partnerId = root.findViewById(R.id.account_partner_id_edit);
        partnerId.setEnabled(false);
    }

    private void setObservers(){
        partnerViewModel.readPartner().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                userInputDto = userDto;
                displayName.setText(userDto.getDisplayName());
                email.setText(userDto.getEmail());
                phone.setText(userDto.getPhone().toString());
            }
        });
    }
}
