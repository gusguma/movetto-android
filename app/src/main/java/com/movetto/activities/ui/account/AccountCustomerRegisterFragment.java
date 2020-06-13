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
import com.movetto.dtos.CustomerDto;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.DirectionType;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.dtos.validations.Validation;
import com.movetto.view_models.CustomerViewModel;

import java.util.Objects;

public class AccountCustomerRegisterFragment extends Fragment
        implements View.OnFocusChangeListener, View.OnClickListener {

    private View root;
    private CustomerViewModel customerViewModel;

    private EditText displayName;
    private EditText email;
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
    private boolean response;

    public AccountCustomerRegisterFragment() {
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
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_account_customer_register, container, false);
    }

    private void setComponents() {
        displayName = root.findViewById(R.id.account_customer_reg_name_edit);
        email = root.findViewById(R.id.account_customer_reg_email_edit);
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
        displayName.setOnFocusChangeListener(this);
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
                if (userDto != null) {
                    displayName.setText(userDto.getDisplayName());
                    email.setText(userDto.getEmail());
                    if (userDto.getPhone() != null) {
                        phone.setText(userDto.getPhone().toString());
                        phone.setEnabled(false);
                    }
                    userOutputDto = userDto;
                }
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
        boolean isValidate = true;
        EditText[] editTexts = new EditText[]{
                displayName,phone,street,postalCode,city,
                state,country,customerId
        };
        for (EditText editText:editTexts) {
            String text = editText.getText().toString();
            CharSequence error = editText.getError();
            if (error != null || text.isEmpty())
                isValidate = false;
        }
        return isValidate;
    }

    private void setCustomerDataOutput() {
        userOutputDto.setDisplayName(displayName.getText().toString());
        userOutputDto.setPhone(phone.getText().toString());
        userOutputDto.setCustomer(new CustomerDto());
        setCustomerDirection();
        userOutputDto.getDirections().add(directionOutputDto);
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
    }

    @Override
    public void onClick(View v) {
        try {
            setCustomerDataOutput();
            if (isFormValidate()){
                setResponseResult();
            } else {
                Toast.makeText(root.getContext()
                        ,"Verifique los datos del formulario",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setResponseResult() throws Exception {
        customerViewModel.saveCustomer(userOutputDto).observe(getViewLifecycleOwner(),
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean responseResult) {
                        response = responseResult;
                        if (response) {
                            getRegisterOkFragment();
                        } else {
                            getRegisterErrorFragment();
                        }
                    }
                });
    }

    private void getRegisterOkFragment() {
        Bundle data = new Bundle();
        data.putInt("image", R.drawable.ic_check_circle_black_24dp);
        data.putString("title", "Cliente Guardado");
        data.putString("subtitle", "El registro de Cliente se ha completado correctamente.");
        Navigation.findNavController(root).navigate(
                R.id.action_nav_account_customer_reg_to_nav_account_reg_result, data);
        Toast.makeText(root.getContext(),
                "El registro de Cliente se ha realizado correctamente",
                Toast.LENGTH_LONG).show();
        Objects.requireNonNull(getActivity()).finish();
    }

    private void getRegisterErrorFragment() {
        Bundle data = new Bundle();
        data.putInt("image", R.drawable.ic_warning_black_24dp);
        data.putString("title", "Hemos tenido un problema");
        data.putString("subtitle", "El registro de Cliente no se ha podido completar.");
        Navigation.findNavController(root).navigate(
                R.id.action_nav_account_customer_reg_to_nav_account_reg_result, data);
        Toast.makeText(root.getContext(),
                "No se ha podido registrar el cliente",
                Toast.LENGTH_LONG).show();
        Objects.requireNonNull(getActivity()).finish();
    }
}
