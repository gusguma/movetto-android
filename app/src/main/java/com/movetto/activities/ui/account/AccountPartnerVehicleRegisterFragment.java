package com.movetto.activities.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.movetto.R;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.UserDto;
import com.movetto.view_models.CustomerViewModel;
import com.movetto.view_models.PartnerViewModel;


public class AccountPartnerVehicleRegisterFragment extends Fragment {

    private View root;
    private PartnerViewModel partnerViewModel;
    private Bundle data;
    private Spinner vehicleType;
    private EditText make;
    private EditText model;
    private EditText registration;
    private EditText description;

    private UserDto userOutputDto;
    private Button buttonSave;
    private boolean response;

    public AccountPartnerVehicleRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater,container);
        setComponents();
        return root;
    }

    private void setViewModels() {
        partnerViewModel = new ViewModelProvider(this).get(PartnerViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_account_partner_vehicle_register, container, false);
    }

    private void setUserData(){
        data = getArguments();
        if(data != null && data.getSerializable("user") != null) {
            userOutputDto = (UserDto) data.getSerializable("user");
        }
    }

    private void setComponents() {
        setUserData();
        vehicleType = root.findViewById(R.id.account_vehicle_reg_type_edit);
        make = root.findViewById(R.id.account_vehicle_reg_make_edit);
        model = root.findViewById(R.id.account_vehicle_reg_model_edit);
        registration = root.findViewById(R.id.account_vehicle_reg_registration_edit);
        description = root.findViewById(R.id.account_vehicle_reg_description_edit);
        buttonSave = root.findViewById(R.id.account_vehicle_reg_save_button);
    }
}
