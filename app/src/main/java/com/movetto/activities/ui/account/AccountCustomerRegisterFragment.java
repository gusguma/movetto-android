package com.movetto.activities.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.movetto.R;
import com.movetto.dtos.CustomerDto;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.DirectionType;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.dtos.validations.Validation;
import com.movetto.view_models.CustomerViewModel;
import com.movetto.view_models.DirectionViewModel;

public class AccountCustomerRegisterFragment extends Fragment
        implements View.OnFocusChangeListener, View.OnClickListener {

    private View root;
    private CustomerViewModel customerViewModel;
    private DirectionViewModel directionViewModel;

    private TextView displayNameTop;
    private TextView emailTop;
    private EditText displayNameForm;
    private EditText emailForm;
    private EditText phone;
    private EditText street;
    private EditText postalCode;
    private EditText city;
    private EditText state;
    private EditText country;
    private EditText customerId;
    private UserDto userOutputDto;
    private DirectionDto directionOutputDto;
    private Button buttonSave;

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
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        directionViewModel = new ViewModelProvider(this).get(DirectionViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_account_customer_register, container, false);
    }

    private void setComponents() {
        displayNameTop = root.findViewById(R.id.account_customer_reg_name);
        emailTop = root.findViewById(R.id.account_customer_reg_email);
        displayNameForm = root.findViewById(R.id.account_customer_reg_name_edit);
        emailForm = root.findViewById(R.id.account_customer_reg_email_edit);
        phone = root.findViewById(R.id.account_customer_reg_phone_edit);
        street = root.findViewById(R.id.account_customer_reg_street_edit);
        postalCode = root.findViewById(R.id.account_customer_reg_cp_edit);
        city = root.findViewById(R.id.account_customer_reg_city_edit);
        state = root.findViewById(R.id.account_customer_reg_state_edit);
        country = root.findViewById(R.id.account_customer_reg_country_edit);
        customerId = root.findViewById(R.id.account_customer_reg_id_edit);
        buttonSave = root.findViewById(R.id.account_customer_reg_save_button);
    }

    private void setFormFieldsListener() {
        displayNameForm.setOnFocusChangeListener(this);
        phone.setOnFocusChangeListener(this);
        street.setOnFocusChangeListener(this);
        postalCode.setOnFocusChangeListener(this);
        city.setOnFocusChangeListener(this);
        state.setOnFocusChangeListener(this);
        country.setOnFocusChangeListener(this);
        customerId.setOnFocusChangeListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void setUserDataInput() {
        customerViewModel.readUser().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                displayNameTop.setText(userDto.getDisplayName());
                emailTop.setText(userDto.getEmail());
                displayNameForm.setText(userDto.getDisplayName());
                emailForm.setText(userDto.getEmail());
                userOutputDto = userDto;
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;
        boolean focus = editText.hasFocus();
        boolean isEmpty = editText.getText().toString().isEmpty();
        if (focus) {
            editText.setError(null);
        } else {
            if(isEmpty){
                editText.setError(ErrorStrings.EMPTY);
            } else {
                if (editText.getId() == R.id.account_customer_reg_cp_edit){
                    if (!Validation.isPostalCodeValid(postalCode.getText().toString()))
                        postalCode.setError(ErrorStrings.INVALID_POSTAL_CODE);
                }
                if (editText.getId() == R.id.account_customer_reg_phone_edit) {
                    if (!Validation.isPhoneValid(phone.getText().toString()))
                        phone.setError(ErrorStrings.INVALID_PHONE_NUMBER);
                }
                if (editText.getId() == R.id.account_customer_reg_id_edit) {
                    if (!Validation.isRegisterIdValid(customerId.getText().toString()))
                        customerId.setError(ErrorStrings.INVALID_REGISTER_ID);
                }
            }
        }
    }

    private boolean isFormValidate(){
        boolean hasError = true;
        EditText[] editTexts = new EditText[]{
                displayNameForm,phone,street,postalCode,city,
                state,country,customerId
        };
        for (EditText editText:editTexts) {
            if (editText.getError() != null)
                hasError = false;
        }
        return hasError;
    }

    private void setCustomerDataOutput() {
        userOutputDto.setDisplayName(displayNameForm.getText().toString());
        userOutputDto.setPhone(phone.getText().toString());
        userOutputDto.setCustomer(new CustomerDto());
        userOutputDto.getCustomer().setCustomerId(customerId.getText().toString());
    }

    private void setCustomerDirection() {
        directionOutputDto = new DirectionDto();
        directionOutputDto.setDirectionType(DirectionType.CUSTOMER);
        directionOutputDto.setStreet(street.getText().toString());
        directionOutputDto.setPostalCode(postalCode.getText().toString());
        directionOutputDto.setCity(city.getText().toString());
        directionOutputDto.setState(state.getText().toString());
        directionOutputDto.setCountry(country.getText().toString());
        directionOutputDto.setActive(true);
        directionOutputDto.setUser(userOutputDto);
    }

    @Override
    public void onClick(View v) {
        try {
            setCustomerDataOutput();
            setCustomerDirection();
            if (isFormValidate()){
                customerViewModel.saveCustomer(userOutputDto);
                directionViewModel.saveDirection(directionOutputDto);
                getRegisterOkFragment();
            } else {
                Toast.makeText(root.getContext(),"Verifique los datos del formulario",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            getRegisterErrorFragment();
            e.printStackTrace();
        }
    }

    private void getRegisterOkFragment() {
        //TODO make fragment OK
    }

    private void getRegisterErrorFragment() {
        //TODO make fragment OK
    }
}
