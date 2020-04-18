package com.movetto.activities.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.movetto.R;
import com.movetto.dtos.BikeDto;
import com.movetto.dtos.CarDto;
import com.movetto.dtos.MotorcycleDto;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.VanDto;
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.dtos.validations.Validation;
import com.movetto.view_models.PartnerViewModel;

import java.util.Objects;

public class AccountPartnerVehicleRegisterFragment extends Fragment
        implements AdapterView.OnItemSelectedListener,
        View.OnFocusChangeListener, View.OnClickListener {

    private static final String BIKE = "Bicicleta";
    private static final String CAR = "Coche";
    private static final String MOTORCYCLE = "Motocicleta";
    private static final String VAN = "Furgoneta";

    private View root;
    private PartnerViewModel partnerViewModel;
    private Bundle data;
    private Spinner vehicleType;
    private TextView modelLabel;
    private TextView registrationLabel;
    private EditText name;
    private EditText make;
    private EditText description;
    private EditText model;
    private EditText registration;
    private String vehicleSelected;

    private UserDto userOutputDto;
    private Button buttonSave;
    private boolean response;

    public AccountPartnerVehicleRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater,container);
        setComponents();
        setListeners();
        return root;
    }

    private void setViewModels() {
        partnerViewModel = new ViewModelProvider(this).get(PartnerViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_account_partner_vehicle_register, container, false);
    }

    private void setComponents() {
        setUserData();
        vehicleType = root.findViewById(R.id.account_vehicle_reg_type_edit);
        name = root.findViewById(R.id.account_vehicle_reg_name_edit);
        make = root.findViewById(R.id.account_vehicle_reg_make_edit);
        model = root.findViewById(R.id.account_vehicle_reg_model_edit);
        registration = root.findViewById(R.id.account_vehicle_reg_registration_edit);
        description = root.findViewById(R.id.account_vehicle_reg_description_edit);
        buttonSave = root.findViewById(R.id.account_vehicle_reg_save_button);
        modelLabel = root.findViewById(R.id.account_vehicle_reg_model_label);
        registrationLabel = root.findViewById(R.id.account_vehicle_reg_registration_label);
    }

    private void setListeners(){
        vehicleType.setOnItemSelectedListener(this);
        name.setOnFocusChangeListener(this);
        make.setOnFocusChangeListener(this);
        model.setOnFocusChangeListener(this);
        registration.setOnFocusChangeListener(this);
        description.setOnFocusChangeListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void setUserData(){
        data = getArguments();
        if(data != null && data.getSerializable("user") != null) {
            userOutputDto = (UserDto) data.getSerializable("user");
        }
    }

    private void setVehicleData(){
        if(vehicleSelected.equals(BIKE))
            setBikeData();
        if (vehicleSelected.equals(CAR))
            setCarData();
        if (vehicleSelected.equals(MOTORCYCLE))
            setMotorcycleData();
        if (vehicleSelected.equals(VAN))
            setVanData();
    }

    private void setBikeData() {
        BikeDto bikeDto = new BikeDto(
                make.getText().toString(),
                description.getText().toString());
        bikeDto.setName(name.getText().toString());
        userOutputDto.getPartner().getVehicles().add(bikeDto);
    }

    private void setCarData() {
        CarDto carDto = new CarDto(
                registration.getText().toString(),
                make.getText().toString(),
                model.getText().toString(),
                description.getText().toString()
        );
        carDto.setName(name.getText().toString());
        userOutputDto.getPartner().getVehicles().add(carDto);
    }

    private void setMotorcycleData() {
        MotorcycleDto motorcycleDto = new MotorcycleDto(
                registration.getText().toString(),
                make.getText().toString(),
                model.getText().toString(),
                description.getText().toString()
        );
        motorcycleDto.setName(name.getText().toString());
        userOutputDto.getPartner().getVehicles().add(motorcycleDto);
    }

    private void setVanData() {
        VanDto vanDto = new VanDto(
                registration.getText().toString(),
                make.getText().toString(),
                model.getText().toString(),
                description.getText().toString()
        );
        vanDto.setName(name.getText().toString());
        userOutputDto.getPartner().getVehicles().add(vanDto);
    }

    private boolean isFormValidate(){
        boolean isValidate = true;
        EditText[] editTexts = new EditText[]{
                name,make,model,registration,description
        };
        for (EditText editText:editTexts) {
            String text = editText.getText().toString();
            CharSequence error = editText.getError();
            if (error != null || text.isEmpty())
                isValidate = false;
        }
        return isValidate;
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
                if (editText.getId() == R.id.account_vehicle_reg_registration_edit){
                    if (!Validation.isRegistrationIdValid(registration.getText().toString()))
                        registration.setError(ErrorStrings.INVALID_REGISTRATION_ID);
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        vehicleSelected = parent.getItemAtPosition(position).toString();
        if (vehicleSelected.equals(BIKE)){
            registrationLabel.setVisibility(View.INVISIBLE);
            registration.setVisibility(View.INVISIBLE);
            modelLabel.setVisibility(View.INVISIBLE);
            model.setVisibility(View.INVISIBLE);
        } else {
            registrationLabel.setVisibility(View.VISIBLE);
            registration.setVisibility(View.VISIBLE);
            modelLabel.setVisibility(View.VISIBLE);
            model.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Do nothing
    }

    @Override
    public void onClick(View v) {
        try {
            setVehicleData();
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
        partnerViewModel.savePartner(userOutputDto).observe(getViewLifecycleOwner(),
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
        data = new Bundle();
        data.putInt("image", R.drawable.ic_check_circle_black_24dp);
        data.putString("title", "Socio Guardado");
        data.putString("subtitle", "El registro de Socio se ha completado correctamente.");
        Navigation.findNavController(root).navigate(
                R.id.action_nav_account_reg_vehicle_to_nav_account_reg_result, data);
        Toast.makeText(root.getContext(),
                "El registro de Socio se ha realizado correctamente",
                Toast.LENGTH_LONG).show();
        Objects.requireNonNull(getActivity()).finish();
    }

    private void getRegisterErrorFragment() {
        data = new Bundle();
        Bundle bundle = new Bundle();
        bundle.putInt("image", R.drawable.ic_warning_black_24dp);
        data.putString("title", "Hemos tenido un problema");
        data.putString("subtitle", "El registro de Socio no se ha podido completar.");
        Navigation.findNavController(root).navigate(
                R.id.action_nav_account_reg_vehicle_to_nav_account_reg_result, data);
        Toast.makeText(root.getContext(),
                "No se ha podido realizar el registro",
                Toast.LENGTH_LONG).show();
        Objects.requireNonNull(getActivity()).finish();
    }
}
