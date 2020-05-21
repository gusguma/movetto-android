package com.movetto.activities.ui.shipments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.movetto.R;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.dtos.validations.Validation;
import com.movetto.view_models.ShipmentViewModel;

import org.json.JSONException;

public class ShipmentDetailStartFragment extends Fragment
        implements View.OnFocusChangeListener, View.OnClickListener {

    private View root;
    private ShipmentViewModel shipmentViewModel;
    private EditText street;
    private EditText postalCode;
    private EditText city;
    private EditText state;
    private EditText country;
    private int shipmentId;
    private ShipmentDto shipment;
    private DirectionDto directionStart;
    private FloatingActionButton buttonSave;

    public ShipmentDetailStartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setFormFieldsListener();
        setShipmentId();
        setShipmentDataInput();
        return root;
    }

    private void setViewModels() {
        shipmentViewModel = new ViewModelProvider(this).get(ShipmentViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_shipment_detail_start, container, false);
    }

    private void setComponents() {
        street = root.findViewById(R.id.shipment_detail_start_street_edit);
        postalCode = root.findViewById(R.id.shipment_detail_start_cp_edit);
        city = root.findViewById(R.id.shipment_detail_start_city_edit);
        state = root.findViewById(R.id.shipment_detail_start_state_edit);
        country = root.findViewById(R.id.shipment_detail_start_country_edit);
        buttonSave = root.findViewById(R.id.shipment_detail_start_save_button);
    }

    private void setFormFieldsListener() {
        street.setOnFocusChangeListener(this);
        postalCode.setOnFocusChangeListener(this);
        city.setOnFocusChangeListener(this);
        state.setOnFocusChangeListener(this);
        country.setOnFocusChangeListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void setShipmentId() {
        Bundle data = getArguments();
        if (data != null && data.getInt("shipmentId") != 0) {
            shipmentId = data.getInt("shipmentId");
        }
    }

    private void setShipmentDataInput() {
        shipmentViewModel.readShipmentById(shipmentId).observe(getViewLifecycleOwner(), new Observer<ShipmentDto>() {
            @Override
            public void onChanged(ShipmentDto shipmentDto) {
                shipment = shipmentDto;
                directionStart = shipment.getStartDirection();
                street.setText(directionStart.getStreet());
                postalCode.setText(directionStart.getPostalCode());
                city.setText(directionStart.getCity());
                state.setText(directionStart.getState());
                country.setText(directionStart.getCountry());
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;
        if (editText.hasFocus())
            editText.setError(null);
        if (editText.getText().toString().isEmpty()) {
            editText.setError(ErrorStrings.EMPTY);
        }
        if (editText.getId() == R.id.shipment_detail_start_cp_edit) {
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
        shipment.setStartDirection(directionStart);
    }

    @Override
    public void onClick(View v) {
        try {
            setDirectionStart();
            if (isFormValidate()) {
                updateShipment();
            } else {
                Toast.makeText(root.getContext()
                        , "Verifique los datos del formulario", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateShipment() throws JsonProcessingException, JSONException {
        shipmentViewModel.updateShipment(shipment).observe(getViewLifecycleOwner(), new Observer<ShipmentDto>() {
            @Override
            public void onChanged(ShipmentDto shipmentDto) {
                if (shipmentDto != null) {
                    updateShipmentOk();
                } else {
                    updateShipmentError();
                }
            }
        });
    }

    private void updateShipmentOk() {
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipment_detail_self);
        Toast.makeText(root.getContext(),
                "Envio Actualizado",
                Toast.LENGTH_LONG).show();
    }

    private void updateShipmentError() {
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipment_detail_self);
        Toast.makeText(root.getContext()
                , "No se ha podido actualizar el envío",
                Toast.LENGTH_LONG).show();
    }

}
