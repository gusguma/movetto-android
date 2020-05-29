package com.movetto.activities.ui.travel;

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

public class TravelEndFragment extends Fragment
        implements View.OnFocusChangeListener, View.OnClickListener{

    private View root;
    private EditText street;
    private EditText postalCode;
    private EditText city;
    private EditText state;
    private EditText country;
    private Bundle data;
    private DirectionDto directionEnd;
    private Button buttonContinue;

    public TravelEndFragment() {
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
        root = inflater.inflate(R.layout.fragment_travel_end, container, false);
    }

    private void setComponents() {
        street = root.findViewById(R.id.travel_end_street_edit);
        postalCode = root.findViewById(R.id.travel_end_cp_edit);
        city = root.findViewById(R.id.travel_end_city_edit);
        state = root.findViewById(R.id.travel_end_state_edit);
        country = root.findViewById(R.id.travel_end_country_edit);
        buttonContinue = root.findViewById(R.id.travel_end_continue_button);
    }

    private void setFormFieldsListener() {
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
            if(editText.getId() == R.id.travel_end_cp_edit){
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

    private void setDirectionEnd() {
        directionEnd = new DirectionDto(DirectionType.FINISH);
        directionEnd.setStreet(street.getText().toString());
        directionEnd.setPostalCode(postalCode.getText().toString());
        directionEnd.setCity(city.getText().toString());
        directionEnd.setState(state.getText().toString());
        directionEnd.setCountry(country.getText().toString());
    }

    private void setBundle(){
        if (data != null) {
            data.putSerializable("directionEnd", directionEnd);
            Navigation.findNavController(root).navigate(
                    R.id.action_nav_travel_end_to_nav_travel_data, data);
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (isFormValidate()){
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
