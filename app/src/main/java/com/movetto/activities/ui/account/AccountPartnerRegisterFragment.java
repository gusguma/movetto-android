package com.movetto.activities.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.movetto.R;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.DirectionType;
import com.movetto.dtos.PartnerDto;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.dtos.validations.Validation;
import com.movetto.view_models.PartnerViewModel;

public class AccountPartnerRegisterFragment extends Fragment
        implements View.OnFocusChangeListener, View.OnClickListener {

    private View root;
    private PartnerViewModel partnerViewModel;

    private EditText displayName;
    private EditText email;
    private EditText phone;
    private EditText street;
    private EditText postalCode;
    private EditText city;
    private EditText state;
    private EditText country;
    private EditText partnerId;
    private UserDto userOutputDto;
    private DirectionDto directionOutputDto;
    private Button buttonSave;
    private boolean response;

    public AccountPartnerRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setFormFieldsListener();
        setUserDataInput();
        return root;
    }

    private void setViewModels() {
        partnerViewModel = new ViewModelProvider(this).get(PartnerViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_account_partner_register, container, false);
    }

    private void setComponents() {
        displayName = root.findViewById(R.id.account_partner_reg_name_edit);
        email = root.findViewById(R.id.account_partner_reg_email_edit);
        phone = root.findViewById(R.id.account_partner_reg_phone_edit);
        street = root.findViewById(R.id.account_partner_reg_street_edit);
        postalCode = root.findViewById(R.id.account_partner_reg_cp_edit);
        city = root.findViewById(R.id.account_partner_reg_city_edit);
        state = root.findViewById(R.id.account_partner_reg_state_edit);
        country = root.findViewById(R.id.account_partner_reg_country_edit);
        partnerId = root.findViewById(R.id.account_partner_reg_id_edit);
        buttonSave = root.findViewById(R.id.account_partner_reg_save_button);
    }

    private void setFormFieldsListener() {
        displayName.setOnFocusChangeListener(this);
        phone.setOnFocusChangeListener(this);
        street.setOnFocusChangeListener(this);
        postalCode.setOnFocusChangeListener(this);
        city.setOnFocusChangeListener(this);
        state.setOnFocusChangeListener(this);
        country.setOnFocusChangeListener(this);
        partnerId.setOnFocusChangeListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void setUserDataInput() {
        partnerViewModel.readUser().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                displayName.setText(userDto.getDisplayName());
                email.setText(userDto.getEmail());
                if (userDto.getPhone() != null) {
                    phone.setText(userDto.getPhone().toString());
                    phone.setEnabled(false);
                }
                userOutputDto = userDto;
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;
        boolean focus = editText.hasFocus();
        boolean isEmpty = editText.getText().toString().isEmpty();
        if (editText.getId() == R.id.account_partner_reg_cp_edit) {
            if (!Validation.isPostalCodeValid(postalCode.getText().toString()))
                postalCode.setError(ErrorStrings.INVALID_POSTAL_CODE);
        }
        if (editText.getId() == R.id.account_partner_reg_phone_edit) {
            if (!Validation.isPhoneValid(phone.getText().toString()))
                phone.setError(ErrorStrings.INVALID_PHONE_NUMBER);
        }
        if (editText.getId() == R.id.account_partner_reg_id_edit) {
            if (!Validation.isRegisterIdValid(partnerId.getText().toString()))
                partnerId.setError(ErrorStrings.INVALID_REGISTER_ID);
        }
        if (isEmpty) {
            editText.setError(ErrorStrings.EMPTY);
        }
        if (focus) {
            editText.setError(null);
        }
    }

    private boolean isFormValidate(){
        boolean isValidate = true;
        EditText[] editTexts = new EditText[]{
                displayName,phone,street,postalCode,city,
                state,country,partnerId
        };
        for (EditText editText:editTexts) {
            String text = editText.getText().toString();
            CharSequence error = editText.getError();
            if (error != null || text.isEmpty())
                isValidate = false;
        }
        return isValidate;
    }

    private void setPartnerDataOutput() {
        userOutputDto.setDisplayName(displayName.getText().toString());
        userOutputDto.setPhone(phone.getText().toString());
        userOutputDto.setPartner(new PartnerDto());
        setPartnerDirection();
        userOutputDto.getDirections().add(directionOutputDto);
        userOutputDto.getPartner().setPartnerId(partnerId.getText().toString());
        userOutputDto.getPartner().setDriverId(partnerId.getText().toString());
    }

    private void setPartnerDirection() {
        directionOutputDto = new DirectionDto();
        directionOutputDto.setDirectionType(DirectionType.PARTNER);
        directionOutputDto.setStreet(street.getText().toString());
        directionOutputDto.setPostalCode(postalCode.getText().toString());
        directionOutputDto.setCity(city.getText().toString());
        directionOutputDto.setState(state.getText().toString());
        directionOutputDto.setCountry(country.getText().toString());
        directionOutputDto.setActive(true);
    }

    private void setPartnerBundle(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",userOutputDto);
        Navigation.findNavController(root).navigate(
                R.id.action_nav_account_partner_reg_to_nav_account_reg_vehicle, bundle);
    }

    @Override
    public void onClick(View v) {
        try {
            if (isFormValidate()){
                setPartnerDataOutput();
                setPartnerBundle();
            } else {
                Toast.makeText(root.getContext()
                        ,"Verifique los datos del formulario",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
