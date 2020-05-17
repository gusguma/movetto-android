package com.movetto.activities.ui.shipments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.movetto.R;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.DirectionType;
import com.movetto.dtos.Role;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.dtos.validations.Validation;
import com.movetto.view_models.CustomerViewModel;
import com.movetto.view_models.UserViewModel;

public class ShipmentEndFragment extends Fragment
        implements View.OnFocusChangeListener, View.OnClickListener{

    private View root;
    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText street;
    private EditText postalCode;
    private EditText city;
    private EditText state;
    private EditText country;
    private Bundle data;
    private DirectionDto directionEnd;
    private UserDto destinationUser;
    private Button buttonContinue;

    public ShipmentEndFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setLayout(inflater, container);
        setComponents();
        setFormFieldsListener();
        setBundleData();
        return root;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_shipment_end, container, false);
    }

    private void setComponents() {
        name = root.findViewById(R.id.shipment_end_name_edit);
        email = root.findViewById(R.id.shipment_end_email_edit);
        phone = root.findViewById(R.id.shipment_end_phone_edit);
        street = root.findViewById(R.id.shipment_end_street_edit);
        postalCode = root.findViewById(R.id.shipment_end_cp_edit);
        city = root.findViewById(R.id.shipment_end_city_edit);
        state = root.findViewById(R.id.shipment_end_state_edit);
        country = root.findViewById(R.id.shipment_end_country_edit);
        buttonContinue = root.findViewById(R.id.shipment_end_continue_button);
    }

    private void setFormFieldsListener() {
        name.setOnFocusChangeListener(this);
        email.setOnFocusChangeListener(this);
        phone.setOnFocusChangeListener(this);
        street.setOnFocusChangeListener(this);
        postalCode.setOnFocusChangeListener(this);
        city.setOnFocusChangeListener(this);
        state.setOnFocusChangeListener(this);
        country.setOnFocusChangeListener(this);
        buttonContinue.setOnClickListener(this);
    }

    private void setBundleData(){
        data = getArguments();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;
        if (editText.hasFocus()){
            editText.setError(null);
        } else {
            if(editText.getText().toString().isEmpty()){
                editText.setError(ErrorStrings.EMPTY);
            }
            if(editText.getId() == R.id.shipment_end_cp_edit){
                isPostalCodeValid();
            }
            if (editText.getId() == R.id.shipment_end_phone_edit) {
                isPhoneValid();
            }
            if (editText.getId() == R.id.shipment_end_email_edit) {
                isEmailValid();
            }
        }
    }

    private void isPostalCodeValid(){
        if (!Validation.isPostalCodeValid(postalCode.getText().toString()))
            postalCode.setError(ErrorStrings.INVALID_POSTAL_CODE);
    }

    private void isPhoneValid(){
        if (!Validation.isPhoneValid(phone.getText().toString()))
            phone.setError(ErrorStrings.INVALID_PHONE_NUMBER);
    }

    private void isEmailValid(){
        if (!Validation.isEmailValid(email.getText().toString()))
            phone.setError(ErrorStrings.INVALID_EMAIL_ADDRESS);
    }

    private boolean isFormValidate(){
        boolean isValidate = true;
        EditText[] editTexts = new EditText[]{
                name,email,phone,street,postalCode,city,state,country
        };
        for (EditText editText:editTexts) {
            String text = editText.getText().toString();
            CharSequence error = editText.getError();
            if (error != null || text.isEmpty())
                isValidate = false;
        }
        return isValidate;
    }

    private void setDestinationUser() {
        destinationUser = new UserDto();
        destinationUser.getRoles().add(Role.DESTINATION);
        destinationUser.setDisplayName(name.getText().toString());
        destinationUser.setEmail(email.getText().toString());
        destinationUser.setPhone(phone.getText().toString());
    }

    private void setDirectionEnd() {
        directionEnd = new DirectionDto(DirectionType.FINISH);
        directionEnd.setStreet(street.getText().toString());
        directionEnd.setPostalCode(postalCode.getText().toString());
        directionEnd.setCity(city.getText().toString());
        directionEnd.setState(state.getText().toString());
        directionEnd.setCountry(country.getText().toString());
        destinationUser.getDirections().add(directionEnd);
    }

    private void setBundle(){
        if (data != null) {
            data.putSerializable("directionEnd", directionEnd);
            data.putSerializable("destinationUser", destinationUser);
            Navigation.findNavController(root).navigate(
                    R.id.action_nav_shipment_end_to_nav_shipment_add_update_package, data);
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (isFormValidate()){
                setDestinationUser();
                setDirectionEnd();
                setBundle();
            } else {
                Toast.makeText(root.getContext()
                        ,"Verifique los datos del formulario",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
