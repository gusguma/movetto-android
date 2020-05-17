package com.movetto.activities.ui.shipments;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.movetto.R;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.PackageDto;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.view_models.CustomerViewModel;
import com.movetto.view_models.ShipmentViewModel;
import com.movetto.view_models.UserViewModel;

import org.json.JSONException;

import java.util.Arrays;
import java.util.Set;

public class ShipmentAddUpdatePackageFragment extends Fragment
        implements View.OnFocusChangeListener, View.OnClickListener{

    private View root;
    private UserViewModel userViewModel;
    private CustomerViewModel customerViewModel;
    private ShipmentViewModel shipmentViewModel;
    private EditText lenght;
    private EditText width;
    private EditText height;
    private EditText weight;
    private Bundle data;
    private DirectionDto directionStart;
    private DirectionDto directionEnd;
    private PackageDto packageDto;
    private UserDto customer;
    private UserDto destinationUser;
    private ShipmentDto shipment;
    private Button buttonSave;
    private Button buttonDelete;

    public ShipmentAddUpdatePackageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setFormFieldsListener();
        try {
            getBundleData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    private void setViewModels() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        shipmentViewModel = new ViewModelProvider(this).get(ShipmentViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_shipment_add_update_package, container, false);
    }

    private void setComponents() {
        lenght = root.findViewById(R.id.shipment_package_lenght_edit);
        width = root.findViewById(R.id.shipment_package_width_edit);
        height = root.findViewById(R.id.shipment_package_height_edit);
        weight = root.findViewById(R.id.shipment_package_weight_edit);
        buttonSave = root.findViewById(R.id.shipment_package_continue_button);
        buttonDelete = root.findViewById(R.id.shipment_package_delete_button);
    }

    private void setFormFieldsListener() {
        lenght.setOnFocusChangeListener(this);
        width.setOnFocusChangeListener(this);
        height.setOnFocusChangeListener(this);
        weight.setOnFocusChangeListener(this);
        buttonSave.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
    }

    private void getBundleData() {
        data = getArguments();
        if (data != null && data.getSerializable("shipment") != null){
            shipment = (ShipmentDto) data.getSerializable("shipment");
            checkPackagesNumber();
        }
        if (data != null && data.getInt("packageId") != 0){
            updatePackageData();
        }
        if (data != null && data.getSerializable("destinationUser") != null){
            createNewShipment();
            checkDestinationUser();
        }
    }

    private void checkPackagesNumber(){
        if (shipment.getPackages().size() > 1) {
            buttonDelete.setVisibility(View.VISIBLE);
        }
    }

    private void updatePackageData(){
        int packageId = data.getInt("packageId");
        Set<PackageDto> packages = shipment.getPackages();
        for (PackageDto packageDto : packages ) {
            if (packageDto.getId() == packageId){
                this.packageDto = packageDto;
                setPackageFormData();
            }
        }
    }

    private void setPackageFormData(){
        lenght.setText(String.valueOf(packageDto.getLenght()));
        width.setText(String.valueOf(packageDto.getWidth()));
        height.setText(String.valueOf(packageDto.getHigh()));
        weight.setText(String.valueOf(packageDto.getWeight()));
    }

    private void addBundleData(){
        destinationUser = (UserDto) data.getSerializable("destinationUser");
        directionStart = (DirectionDto)data.getSerializable("directionStart");
        directionEnd = (DirectionDto)data.getSerializable("directionEnd");
    }

    private void createNewShipment(){
        addBundleData();
        customerViewModel.readCustomer().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                customer = userDto;
            }
        });
    }

    private void checkDestinationUser() {
        userViewModel.readUserByEmail(destinationUser).observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                if (userDto == null){
                    System.out.println("Usuario no Existe");
                    try {
                        saveUser();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        destinationUser = userDto;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void saveUser() throws Exception {
        userViewModel.saveUserByEmail(destinationUser).observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                if (userDto != null){
                    destinationUser = userDto;
                }
            }
        });
    }

    private void setPackageData(){
        if (packageDto == null) {
            packageDto = new PackageDto();
        }
        packageDto.setLenght(Double.parseDouble(lenght.getText().toString()));
        packageDto.setWidth(Double.parseDouble(width.getText().toString()));
        packageDto.setHigh(Double.parseDouble(height.getText().toString()));
        packageDto.setWeight(Double.parseDouble(weight.getText().toString()));
        packageDto.setPackagePrice();
    }

    private boolean isFormValidate(){
        boolean isValidate = true;
        EditText[] editTexts = new EditText[]{
                lenght,width,height,weight
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
        if (editText.hasFocus()){
            editText.setError(null);
        } else {
            if(editText.getText().toString().isEmpty()){
                editText.setError(ErrorStrings.EMPTY);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonSave.getId()) {
            try {
                setButtonSaveListener();
            } catch (JsonProcessingException | JSONException e) {
                e.printStackTrace();
            }
        }
        if (v.getId() == buttonDelete.getId()) {
            try {
                setButtonDeleteListener();
            } catch (JsonProcessingException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setButtonSaveListener() throws JsonProcessingException, JSONException {
        setPackageData();
        if (isFormValidate()) {
            if (shipment == null) {
                setNewShipment();
                saveShipment();
            } else {
                setUpdateShipment();
                updateShipment();
            }
        } else {
            Toast.makeText(root.getContext()
                    , "Verifique los datos del formulario", Toast.LENGTH_LONG).show();
        }
    }

    private void setButtonDeleteListener() throws JsonProcessingException, JSONException {
        shipment.getPackages()
                .removeIf(p -> p.getId() == packageDto.getId());
        updateShipment();
    }

    private void setNewShipment(){
        shipment = new ShipmentDto(
                customer,
                directionStart,
                directionEnd,
                destinationUser
        );
        shipment.getPackages().add(packageDto);
        shipment.setShipmentPrice();
    }

    private void setUpdateShipment(){
        if (data.getInt("packageId") != 0) {
            shipment.getPackages().forEach(pack-> {
                if (pack.getId() == data.getInt("packageId")){
                    pack.setLenght(Double.parseDouble(lenght.getText().toString()));
                    pack.setWidth(Double.parseDouble(width.getText().toString()));
                    pack.setHigh(Double.parseDouble(height.getText().toString()));
                    pack.setWeight(Double.parseDouble(weight.getText().toString()));
                    pack.setPackagePrice();
                }
            });
            shipment.setShipmentPrice();
        } else {
            setPackageData();
            shipment.getPackages().add(packageDto);
            shipment.setShipmentPrice();
        }
    }

    private void saveShipment() throws JsonProcessingException, JSONException {
        shipmentViewModel.saveShipment(shipment).observe(getViewLifecycleOwner(), new Observer<ShipmentDto>() {
            @Override
            public void onChanged(ShipmentDto shipmentDto) {
                if(shipmentDto != null) {
                    shipment = shipmentDto;
                    setBundleData();
                    getShipmentSaveOk();
                } else {
                    shipment = null;
                    getShipmentSaveError();
                }
            }
        });
    }

    private void updateShipment() throws JsonProcessingException, JSONException {
        shipmentViewModel.updateShipment(shipment).observe(getViewLifecycleOwner(), new Observer<ShipmentDto>() {
            @Override
            public void onChanged(ShipmentDto shipmentDto) {
                if(shipmentDto != null) {
                    shipment = shipmentDto;
                    setBundleData();
                    getShipmentUpdateOk();
                } else {
                    shipment = null;
                    getShipmentUpdateError();
                }
            }
        });
    }

    private void setBundleData (){
        data = new Bundle();
        data.putInt("shipmentId", shipment.getId());
    }

    private void getShipmentSaveOk() {
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipment_add_update_package_to_nav_shipment_detail, data);
        Toast.makeText(root.getContext(),"Envío Creado", Toast.LENGTH_LONG).show();
    }

    private void getShipmentSaveError() {
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipment_add_update_package_to_nav_shipments_empty);
        Toast.makeText(root.getContext(),"No se ha podido crear el envío"
                ,Toast.LENGTH_LONG).show();
    }

    private void getShipmentUpdateOk() {
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipment_add_update_package_to_nav_shipment_detail, data);
        Toast.makeText(root.getContext(),
                "Envío Actualizado",
                Toast.LENGTH_LONG).show();
    }

    private void getShipmentUpdateError() {
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipment_add_update_package_to_nav_shipments_empty);
        Toast.makeText(root.getContext(),
                "No se ha podido actualizar el envío",
                Toast.LENGTH_LONG).show();
    }

}
