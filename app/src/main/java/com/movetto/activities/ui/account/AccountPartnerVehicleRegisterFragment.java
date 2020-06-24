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
import com.movetto.dtos.VehicleDto;
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.dtos.validations.Validation;
import com.movetto.view_models.PartnerViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

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
    private VehicleDto vehicleDto;
    private UserDto userOutputDto;
    private Button buttonSave;
    private Button buttonDelete;
    private boolean response;

    public AccountPartnerVehicleRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
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
        vehicleType = root.findViewById(R.id.account_vehicle_reg_type_edit);
        name = root.findViewById(R.id.account_vehicle_reg_name_edit);
        make = root.findViewById(R.id.account_vehicle_reg_make_edit);
        model = root.findViewById(R.id.account_vehicle_reg_model_edit);
        registration = root.findViewById(R.id.account_vehicle_reg_registration_edit);
        description = root.findViewById(R.id.account_vehicle_reg_description_edit);
        buttonSave = root.findViewById(R.id.account_vehicle_reg_save_button);
        buttonDelete = root.findViewById(R.id.account_vehicle_reg_delete_button);
        buttonDelete.setVisibility(View.GONE);
        modelLabel = root.findViewById(R.id.account_vehicle_reg_model_label);
        registrationLabel = root.findViewById(R.id.account_vehicle_reg_registration_label);
        setUserData();
    }

    private void setListeners(){
        vehicleType.setOnItemSelectedListener(this);
        name.setOnFocusChangeListener(this);
        make.setOnFocusChangeListener(this);
        model.setOnFocusChangeListener(this);
        registration.setOnFocusChangeListener(this);
        description.setOnFocusChangeListener(this);
        buttonSave.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
    }

    private void setUserData(){
        data = getArguments();
        if(data != null && data.getSerializable("user") != null) {
            userOutputDto = (UserDto) data.getSerializable("user");
        }
        if (data != null && data.getInt("id") != 0){
            vehicleType.setEnabled(false);
            registration.setEnabled(false);
            buttonDelete.setVisibility(View.VISIBLE);
            getVehicleUpdateData();
        }
    }

    private void getVehicleUpdateData(){
        int id = data.getInt("id");
        Set<VehicleDto> vehicles = userOutputDto.getPartner().getVehicles();
        for(VehicleDto vehicle : vehicles){
            if (vehicle.getId() == id){
                vehicleDto = vehicle;
                setVehicleFormData();
            }
        }
    }

    private void setVehicleFormData(){
        if (vehicleDto.getClass() == BikeDto.class){
            setBikeFormData((BikeDto)vehicleDto);
        }
        if (vehicleDto.getClass() == CarDto.class){
            setCarFormData((CarDto)vehicleDto);
        }
        if (vehicleDto.getClass() == MotorcycleDto.class){
            setMotorcycleFormData((MotorcycleDto)vehicleDto);
        }
        if (vehicleDto.getClass() == VanDto.class){
            setVanFormData((VanDto)vehicleDto);
        }
    }

    private void setBikeFormData(BikeDto bike){
        vehicleType.setSelection(0);
        name.setText(bike.getName());
        make.setText(bike.getMake());
        description.setText(bike.getDescription());
    }

    private void setCarFormData(CarDto car){
        vehicleType.setSelection(1);
        name.setText(car.getName());
        make.setText(car.getMake());
        model.setText(car.getModel());
        registration.setText(car.getRegistration());
        description.setText(car.getDescription());
    }

    private void setMotorcycleFormData(MotorcycleDto motorcycle){
        vehicleType.setSelection(2);
        name.setText(motorcycle.getName());
        make.setText(motorcycle.getMake());
        model.setText(motorcycle.getModel());
        registration.setText(motorcycle.getRegistration());
        description.setText(motorcycle.getDescription());
    }

    private void setVanFormData(VanDto van){
        vehicleType.setSelection(3);
        name.setText(van.getName());
        make.setText(van.getMake());
        model.setText(van.getModel());
        registration.setText(van.getRegistration());
        description.setText(van.getDescription());
    }

    private VehicleDto setVehicleData(){
        if(vehicleSelected.equals(BIKE))
            vehicleDto = setBikeData();
        if (vehicleSelected.equals(CAR))
            vehicleDto = setCarData();
        if (vehicleSelected.equals(MOTORCYCLE))
            vehicleDto = setMotorcycleData();
        if (vehicleSelected.equals(VAN))
            vehicleDto = setVanData();
        return vehicleDto;
    }

    private BikeDto setBikeData() {
        if (vehicleDto == null){
            vehicleDto = new BikeDto();
        }
        BikeDto bike = (BikeDto) vehicleDto;
        bike.setName(name.getText().toString());
        bike.setMake(make.getText().toString());
        bike.setDescription(description.getText().toString());
        bike.setActive(true);
        return bike;
    }

    private CarDto setCarData() {
        if (vehicleDto == null){
            vehicleDto = new CarDto();
        }
        CarDto car = (CarDto) vehicleDto;
        car.setName(name.getText().toString());
        car.setMake(make.getText().toString());
        car.setModel(model.getText().toString());
        car.setDescription(description.getText().toString());
        car.setRegistration(registration.getText().toString());
        car.setActive(true);
        return car;
    }

    private MotorcycleDto setMotorcycleData() {
        if (vehicleDto == null){
            vehicleDto = new MotorcycleDto();
        }
        MotorcycleDto motorcycle = (MotorcycleDto) vehicleDto;
        motorcycle.setName(name.getText().toString());
        motorcycle.setMake(make.getText().toString());
        motorcycle.setModel(model.getText().toString());
        motorcycle.setDescription(description.getText().toString());
        motorcycle.setRegistration(registration.getText().toString());
        motorcycle.setActive(true);
        return motorcycle;
    }

    private VanDto setVanData() {
        if (vehicleDto == null){
            vehicleDto = new VanDto();
        }
        VanDto van = (VanDto) vehicleDto;
        van.setName(name.getText().toString());
        van.setMake(make.getText().toString());
        van.setModel(model.getText().toString());
        van.setDescription(description.getText().toString());
        van.setRegistration(registration.getText().toString());
        van.setActive(true);
        return van;
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
            registration.setText("-");
            registration.setVisibility(View.INVISIBLE);
            modelLabel.setVisibility(View.INVISIBLE);
            model.setText("-");
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
        if (v.getId() == R.id.account_vehicle_reg_delete_button){
            try {
                deleteVehicle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (v.getId() == R.id.account_vehicle_reg_save_button){
            setButtonSaveListener();
        }
    }

    private void setButtonSaveListener(){
        try {
            if (isFormValidate()){
                checkPartner();
            } else {
                Toast.makeText(root.getContext()
                        ,"Verifique los datos del formulario",Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkPartner() throws Exception {
        if (userOutputDto.getPartner().getVehicles().isEmpty()){
            vehicleDto = setVehicleData();
            userOutputDto.getPartner().getVehicles().add(vehicleDto);
            savePartner();
        } else {
            updateVehicle();
        }
    }

    private void updateVehicle() throws Exception {
        Set<VehicleDto> vehicles = userOutputDto.getPartner().getVehicles();
        for (VehicleDto vehicle : vehicles){
            if (vehicle.getId() == data.getInt("id")){
                vehicleDto = vehicle;
            }
        }
        vehicleDto = setVehicleData();
        vehicleDto.setId(data.getInt("id"));
        userOutputDto.getPartner().getVehicles().add(vehicleDto);
        updatePartner();
    }

    private void deleteVehicle() throws Exception {
        Set<VehicleDto> vehicles = userOutputDto.getPartner().getVehicles();
        for (VehicleDto vehicle : vehicles){
            if (vehicle.getId() == data.getInt("id")){
                vehicles.remove(vehicle);
            }
        }
        userOutputDto.getPartner().setVehicles(vehicles);
        updatePartner();
    }

    private void savePartner() throws Exception {
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

    private void updatePartner() throws Exception {
        partnerViewModel.updatePartner(userOutputDto).observe(getViewLifecycleOwner(),
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean responseResult) {
                        response = responseResult;
                        if (response) {
                            getUpdateOk();
                        } else {
                            getUpdateError();
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

    private void getUpdateOk() {
        Navigation.findNavController(root).navigate(
                R.id.action_nav_account_reg_vehicle_to_nav_account_partner);
        Toast.makeText(root.getContext(),
                "El registro de Vehiculo se ha realizado correctamente",
                Toast.LENGTH_LONG).show();
    }

    private void getUpdateError() {
        Toast.makeText(root.getContext(),
                "No se ha podido registrar el Vehículo",
                Toast.LENGTH_LONG).show();
    }
}
