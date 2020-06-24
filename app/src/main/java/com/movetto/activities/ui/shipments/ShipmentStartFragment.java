package com.movetto.activities.ui.shipments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.dtos.validations.Validation;

public class ShipmentStartFragment extends Fragment
        implements View.OnFocusChangeListener, View.OnClickListener {

    private View root;
    private EditText street;
    private EditText postalCode;
    private EditText city;
    private EditText state;
    private EditText country;
    private DirectionDto directionStart;
    private Button buttonContinue;

    public ShipmentStartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setLayout(inflater, container);
        setComponents();
        setFormFieldsListener();
        return root;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_shipment_start, container, false);
    }

    private void setComponents() {
        street = root.findViewById(R.id.shipment_start_customer_street_edit);
        postalCode = root.findViewById(R.id.shipment_start_customer_cp_edit);
        city = root.findViewById(R.id.shipment_start_customer_city_edit);
        state = root.findViewById(R.id.shipment_start_customer_state_edit);
        country = root.findViewById(R.id.shipment_start_customer_country_edit);
        buttonContinue = root.findViewById(R.id.shipment_start_customer_continue_button);
    }

    private void setFormFieldsListener() {
        street.setOnFocusChangeListener(this);
        postalCode.setOnFocusChangeListener(this);
        city.setOnFocusChangeListener(this);
        state.setOnFocusChangeListener(this);
        country.setOnFocusChangeListener(this);
        buttonContinue.setOnClickListener(this);
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
            if(editText.getId() == R.id.shipment_start_customer_cp_edit){
                isPostalCodeValid();
            }
        }
    }

    private void isPostalCodeValid(){
        if (!Validation.isPostalCodeValid(postalCode.getText().toString()))
            postalCode.setError(ErrorStrings.INVALID_POSTAL_CODE);
    }

    private boolean isFormValidate(){
        boolean isValidate = true;
        EditText[] editTexts = new EditText[]{
                street,postalCode,city,state,country
        };
        for (EditText editText:editTexts) {
            String text = editText.getText().toString();
            CharSequence error = editText.getError();
            if (error != null || text.isEmpty())
                isValidate = false;
        }
        return isValidate;
    }

    private void setDirectionStart() {
        directionStart = new DirectionDto(DirectionType.START);
        directionStart.setStreet(street.getText().toString());
        directionStart.setPostalCode(postalCode.getText().toString());
        directionStart.setCity(city.getText().toString());
        directionStart.setState(state.getText().toString());
        directionStart.setCountry(country.getText().toString());
    }

    private void setPartnerBundle(){
        Bundle data = new Bundle();
        data.putSerializable("directionStart", directionStart);
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipments_start_to_nav_shipment_end, data);
    }

    @Override
    public void onClick(View v) {
        try {
            if (isFormValidate()){
                setDirectionStart();
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