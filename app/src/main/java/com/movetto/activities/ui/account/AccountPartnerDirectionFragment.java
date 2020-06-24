package com.movetto.activities.ui.account;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.movetto.R;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.DirectionType;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.dtos.validations.Validation;
import com.movetto.view_models.PartnerViewModel;

import java.util.Objects;
import java.util.Set;

public class AccountPartnerDirectionFragment extends Fragment
        implements View.OnClickListener, View.OnFocusChangeListener  {

    private View root;
    private PartnerViewModel partnerViewModel;
    private EditText street;
    private EditText postalCode;
    private EditText city;
    private EditText state;
    private EditText country;
    private UserDto userOutputDto;
    private DirectionDto directionOutputDto;
    private FloatingActionButton buttonSave;
    private Boolean response;

    public AccountPartnerDirectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater,container);
        setComponents();
        setFormFieldsListener();
        setUserDataInput();
        return root;
    }

    private void setViewModels() {
        partnerViewModel = new ViewModelProvider(this).get(PartnerViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_account_partner_direction, container, false);
    }

    private void setComponents() {
        street = root.findViewById(R.id.account_partner_street_edit);
        postalCode = root.findViewById(R.id.account_partner_cp_edit);
        city = root.findViewById(R.id.account_partner_city_edit);
        state = root.findViewById(R.id.account_partner_state_edit);
        country = root.findViewById(R.id.account_partner_country_edit);
        buttonSave = root.findViewById(R.id.account_partner_direction_save_button);
    }

    private void setFormFieldsListener() {
        street.setOnFocusChangeListener(this);
        postalCode.setOnFocusChangeListener(this);
        city.setOnFocusChangeListener(this);
        state.setOnFocusChangeListener(this);
        country.setOnFocusChangeListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void setUserDataInput(){
        partnerViewModel.readPartner().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                userOutputDto = userDto;
                DirectionDto partnerDirection = getPartnerDirection(userDto);
                street.setText(partnerDirection.getStreet());
                postalCode.setText(partnerDirection.getPostalCode());
                city.setText(partnerDirection.getCity());
                state.setText(partnerDirection.getState());
                country.setText(partnerDirection.getCountry());
            }
        });
    }

    private DirectionDto getPartnerDirection(UserDto user){
        Set<DirectionDto> directionDtos = user.getDirections();
        for(DirectionDto direction:directionDtos){
            if(direction.getDirectionType() == DirectionType.PARTNER){
                directionOutputDto = direction;
            }
        }
        return directionOutputDto;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;
        if (editText.hasFocus())
            editText.setError(null);
        if(editText.getText().toString().isEmpty()){
            editText.setError(ErrorStrings.EMPTY);
        }
        if(editText.getId() == R.id.account_customer_cp_edit){
            isPostalCodeValid();
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

    private void setPartnerDataOutput() {
        setPartnerDirection();
        userOutputDto.getDirections().add(directionOutputDto);
    }

    private void setPartnerDirection() {
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
            setPartnerDataOutput();
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
        partnerViewModel.updatePartner(userOutputDto).observe(getViewLifecycleOwner(),
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
        data.putString("title", "Socio Actualizado");
        data.putString("subtitle", "La cuenta de Socio se ha actualizado correctamente.");
        Navigation.findNavController(root).navigate(
                R.id.action_nav_account_partner_to_nav_account_reg_result, data);
        Toast.makeText(root.getContext(),
                "Socio Actualizado",
                Toast.LENGTH_LONG).show();
        Objects.requireNonNull(getActivity()).finish();
    }

    private void getRegisterErrorFragment() {
        Bundle data = new Bundle();
        data.putInt("image", R.drawable.ic_warning_black_24dp);
        data.putString("title", "Hemos tenido un problema");
        data.putString("subtitle", "La cuenta de Socio no se ha podido actualizar.");
        Navigation.findNavController(root).navigate(
                R.id.action_nav_account_partner_to_nav_account_reg_result, data);
        Toast.makeText(root.getContext(),
                "Socio No Actualizado",
                Toast.LENGTH_LONG).show();
        Objects.requireNonNull(getActivity()).finish();
    }


}
