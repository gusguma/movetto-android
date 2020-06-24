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
import com.movetto.dtos.ShipmentStatus;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.dtos.validations.Validation;
import com.movetto.view_models.ShipmentViewModel;
import com.movetto.view_models.UserViewModel;

import org.json.JSONException;

public class ShipmentDetailEndFragment extends Fragment
        implements View.OnFocusChangeListener, View.OnClickListener {

    private View root;
    private ShipmentViewModel shipmentViewModel;
    private UserViewModel userViewModel;
    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText street;
    private EditText postalCode;
    private EditText city;
    private EditText state;
    private EditText country;
    private int shipmentId;
    private UserDto destinationUser;
    private ShipmentDto shipment;
    private DirectionDto directionEnd;
    private FloatingActionButton buttonSave;
    private Bundle data;

    public ShipmentDetailEndFragment() {
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
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_shipment_detail_end, container, false);
    }

    private void setComponents() {
        name = root.findViewById(R.id.shipment_detail_end_name_edit);
        email = root.findViewById(R.id.shipment_detail_end_email_edit);
        phone = root.findViewById(R.id.shipment_detail_end_phone_edit);
        street = root.findViewById(R.id.shipment_detail_end_street_edit);
        postalCode = root.findViewById(R.id.shipment_detail_end_cp_edit);
        city = root.findViewById(R.id.shipment_detail_end_city_edit);
        state = root.findViewById(R.id.shipment_detail_end_state_edit);
        country = root.findViewById(R.id.shipment_detail_end_country_edit);
        buttonSave = root.findViewById(R.id.shipment_detail_end_save_button);
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
        buttonSave.setOnClickListener(this);
    }

    private void setShipmentId() {
        data = getArguments();
        if (data != null && data.getInt("shipmentId") != 0) {
            shipmentId = data.getInt("shipmentId");
            data.putInt("serviceId", shipmentId);
        }
    }

    private void setShipmentDataInput() {
        shipmentViewModel.readShipmentById(shipmentId).observe(getViewLifecycleOwner(), new Observer<ShipmentDto>() {
            @Override
            public void onChanged(ShipmentDto shipmentDto) {
                shipment = shipmentDto;
                destinationUser = shipment.getDestinationUser();
                directionEnd = shipment.getEndDirection();
                name.setText(destinationUser.getDisplayName());
                email.setText(destinationUser.getEmail());
                phone.setText(String.valueOf(destinationUser.getPhone()));
                street.setText(directionEnd.getStreet());
                postalCode.setText(directionEnd.getPostalCode());
                city.setText(directionEnd.getCity());
                state.setText(directionEnd.getState());
                country.setText(directionEnd.getCountry());
                checkShipmentStatus();
            }
        });
    }

    private void checkShipmentStatus(){
        if (shipment.getStatus() != ShipmentStatus.SAVED) {
            name.setEnabled(false);
            email.setEnabled(false);
            phone.setEnabled(false);
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
        if (editText.getId() == R.id.shipment_detail_end_cp_edit) {
            isPostalCodeValid();
        }
        if (editText.getId() == R.id.shipment_detail_end_phone_edit) {
            isPhoneValid();
        }
    }

    private void isPostalCodeValid() {
        if (!Validation.isPostalCodeValid(postalCode.getText().toString()))
            postalCode.setError(ErrorStrings.INVALID_POSTAL_CODE);
    }

    private void isPhoneValid() {
        if (!Validation.isPhoneValid(phone.getText().toString()))
            phone.setError(ErrorStrings.INVALID_PHONE_NUMBER);
    }

    private boolean isFormValidate() {
        boolean isValidate = true;
        EditText[] editTexts = new EditText[]{
                name, email, phone, street, postalCode, city, state, country
        };
        for (EditText editText : editTexts) {
            String text = editText.getText().toString();
            CharSequence error = editText.getError();
            if (error != null || text.isEmpty())
                isValidate = false;
        }
        return isValidate;
    }

    private void setDestinationUser() {
        destinationUser.setDisplayName(name.getText().toString());
        destinationUser.setPhone(phone.getText().toString());
        shipment.setDestinationUser(destinationUser);
    }

    private void setDirectionEnd() {
        directionEnd.setStreet(street.getText().toString());
        directionEnd.setPostalCode(postalCode.getText().toString());
        directionEnd.setCity(city.getText().toString());
        directionEnd.setState(state.getText().toString());
        directionEnd.setCountry(country.getText().toString());
        directionEnd.setActive(true);
        shipment.setStartDirection(directionEnd);
    }

    @Override
    public void onClick(View v) {
        try {
            setDirectionEnd();
            setDestinationUser();
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
                    try {
                        updateUser();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    updateShipmentError();
                }
            }
        });
    }

    private void updateUser() throws Exception {
        userViewModel.updateUser(destinationUser).observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                if (userDto != null) {
                    updateShipmentOk();
                } else {
                    updateShipmentError();
                }
            }
        });
    }

    private void setBundle(){
        Bundle bundle = new Bundle();
        bundle.putInt("shipmentId", shipmentId);
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipment_detail_self, data);
    }

    private void updateShipmentOk() {
        setBundle();
        Toast.makeText(root.getContext(),
                "Envio Actualizado",
                Toast.LENGTH_LONG).show();
    }

    private void updateShipmentError() {
        setBundle();
        Toast.makeText(root.getContext()
                , "No se ha podido actualizar el envío",
                Toast.LENGTH_LONG).show();
    }
}
