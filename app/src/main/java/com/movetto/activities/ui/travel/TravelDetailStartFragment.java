package com.movetto.activities.ui.travel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.movetto.R;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.TravelDto;
import com.movetto.dtos.TravelStatus;
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.dtos.validations.Validation;
import com.movetto.view_models.TravelViewModel;

import org.json.JSONException;

public class TravelDetailStartFragment extends Fragment
        implements View.OnFocusChangeListener, View.OnClickListener{

    private View root;
    private TravelViewModel travelViewModel;
    private EditText street;
    private EditText postalCode;
    private EditText city;
    private EditText state;
    private EditText country;
    private int travelId;
    private TravelDto travel;
    private DirectionDto directionStart;
    private FloatingActionButton buttonSave;
    private Bundle data;

    public TravelDetailStartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setFormFieldsListener();
        setTravelId();
        setTravelDataInput();
        return root;
    }

    private void setViewModels() {
        travelViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_travel_detail_start, container, false);
    }

    private void setComponents() {
        street = root.findViewById(R.id.travel_detail_start_street_edit);
        postalCode = root.findViewById(R.id.travel_detail_start_cp_edit);
        city = root.findViewById(R.id.travel_detail_start_city_edit);
        state = root.findViewById(R.id.travel_detail_start_state_edit);
        country = root.findViewById(R.id.travel_detail_start_country_edit);
        buttonSave = root.findViewById(R.id.travel_detail_start_save_button);
    }

    private void setFormFieldsListener() {
        street.setOnFocusChangeListener(this);
        postalCode.setOnFocusChangeListener(this);
        city.setOnFocusChangeListener(this);
        state.setOnFocusChangeListener(this);
        country.setOnFocusChangeListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void setTravelId() {
        data = getArguments();
        if (data != null && data.getInt("travelId") != 0) {
            travelId = data.getInt("travelId");
            data.putInt("serviceId", travelId);
        }
    }

    private void setTravelDataInput() {
        travelViewModel.readTravelById(travelId).observe(getViewLifecycleOwner(), new Observer<TravelDto>() {
            @Override
            public void onChanged(TravelDto travelDto) {
                travel = travelDto;
                directionStart = travel.getStartDirection();
                street.setText(directionStart.getStreet());
                postalCode.setText(directionStart.getPostalCode());
                city.setText(directionStart.getCity());
                state.setText(directionStart.getState());
                country.setText(directionStart.getCountry());
                checkTravelStatus();
            }
        });
    }

    private void checkTravelStatus(){
        if (travel.getStatus() != TravelStatus.SAVED) {
            street.setEnabled(false);
            postalCode.setEnabled(false);
            city.setEnabled(false);
            state.setEnabled(false);
            country.setEnabled(false);
            buttonSave.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;
        if (editText.hasFocus())
            editText.setError(null);
        if (editText.getText().toString().isEmpty()) {
            editText.setError(ErrorStrings.EMPTY);
        }
        if (editText.getId() == R.id.travel_detail_start_cp_edit) {
            isPostalCodeValid();
        }
    }

    private void isPostalCodeValid() {
        if (!Validation.isPostalCodeValid(postalCode.getText().toString()))
            postalCode.setError(ErrorStrings.INVALID_POSTAL_CODE);
    }

    private boolean isFormValidate() {
        boolean isValidate = true;
        EditText[] editTexts = new EditText[]{
                street, postalCode, city, state, country
        };
        for (EditText editText : editTexts) {
            String text = editText.getText().toString();
            CharSequence error = editText.getError();
            if (error != null || text.isEmpty())
                isValidate = false;
        }
        return isValidate;
    }

    private void setDirectionStart() {
        directionStart.setStreet(street.getText().toString());
        directionStart.setPostalCode(postalCode.getText().toString());
        directionStart.setCity(city.getText().toString());
        directionStart.setState(state.getText().toString());
        directionStart.setCountry(country.getText().toString());
        directionStart.setActive(true);
        travel.setStartDirection(directionStart);
    }

    @Override
    public void onClick(View v) {
        try {
            setDirectionStart();
            if (isFormValidate()) {
                setDistance();
            } else {
                Toast.makeText(root.getContext()
                        , "Verifique los datos del formulario", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDistance(){
        travelViewModel.getTravelDistance(travel).observe(getViewLifecycleOwner(), new Observer<TravelDto>() {
            @Override
            public void onChanged(TravelDto travelDto) {
                if (travelDto != null) {
                    travel = travelDto;
                    try {
                        updateTravel();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                } else {
                    updateTravelError();
                }
            }
        });
    }

    private void updateTravel() throws JsonProcessingException, JSONException {
        travelViewModel.updateTravel(travel).observe(getViewLifecycleOwner(), new Observer<TravelDto>() {
            @Override
            public void onChanged(TravelDto travelDto) {
                if (travelDto != null) {
                    updateTravelOk();
                } else {
                    updateTravelError();
                }
            }
        });
    }

    private void updateTravelOk() {
        Navigation.findNavController(root).navigate(
                R.id.action_nav_travel_detail_self, data);
        Toast.makeText(root.getContext(),
                "Viaje Actualizado",
                Toast.LENGTH_LONG).show();
    }

    private void updateTravelError() {
        Navigation.findNavController(root).navigate(
                R.id.action_nav_travel_detail_self, data);
        Toast.makeText(root.getContext()
                , "No se ha podido actualizar el viaje",
                Toast.LENGTH_LONG).show();
    }
}
