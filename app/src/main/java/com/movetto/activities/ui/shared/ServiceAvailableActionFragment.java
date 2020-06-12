package com.movetto.activities.ui.shared;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.movetto.R;
import com.movetto.adapters.ServiceActionSpinnerAdapter;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.ShipmentStatus;
import com.movetto.dtos.TravelDto;
import com.movetto.dtos.TravelStatus;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.VehicleDto;
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.view_models.ShipmentViewModel;
import com.movetto.view_models.TravelViewModel;
import com.movetto.view_models.UserViewModel;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ServiceAvailableActionFragment extends Fragment
        implements AdapterView.OnItemSelectedListener, View.OnFocusChangeListener {

    private static final int SHIPMENT = 1;
    private static final int TRAVEL = 2;

    private View root;
    private ShipmentViewModel shipmentViewModel;
    private TravelViewModel travelViewModel;
    private UserViewModel userViewModel;
    private double paymentAmount;
    private TextView type;
    private TextView serviceNumber;
    private TextView amount;
    private TextView action;
    private EditText registration;
    private TextView spinnerLabel;
    private Spinner spinnerVehicles;
    private ServiceActionSpinnerAdapter adapter;
    private UserDto partner;
    private ShipmentDto shipment;
    private TravelDto travel;
    private VehicleDto vehicle;
    private Button buttonConfirm;
    private Button buttonCancel;
    private ConstraintLayout progressBar;
    private Bundle data;

    public ServiceAvailableActionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setActionData();
        setButtonConfirmListener();
        setButtonCancelListener();
        return root;
    }

    private void setViewModels(){
        shipmentViewModel = new ViewModelProvider(this).get(ShipmentViewModel.class);
        travelViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_service_available_action, container, false);
    }

    private void setComponents() {
        type = root.findViewById(R.id.service_available_action_type_edit);
        serviceNumber = root.findViewById(R.id.service_available_action_number_edit);
        amount = root.findViewById(R.id.service_available_action_amount_edit);
        action = root.findViewById(R.id.service_available_action_action_edit);
        registration = root.findViewById(R.id.service_available_action_nif_edit);
        buttonConfirm = root.findViewById(R.id.service_available_action_confirm_button);
        buttonCancel = root.findViewById(R.id.service_available_action_cancel_button);
        progressBar = root.findViewById(R.id.service_available_action_progress_bar);
        spinnerVehicles = root.findViewById(R.id.service_available_action_vehicle_spinner);
        spinnerLabel = root.findViewById(R.id.service_available_action_vehicle_label);
        spinnerVehicles.setFocusable(true);
        data = getArguments();
    }

    private void setAdapter(){
        List<VehicleDto> vehicles = new ArrayList<>(partner.getPartner().getVehicles());
        adapter = new ServiceActionSpinnerAdapter(getContext());
        adapter.setVehicles(vehicles);
        spinnerVehicles.setAdapter(adapter);
        setListeners();
    }

    private void setActionData(){
        if (data != null && data.getInt("serviceType") != 0) {
            setService();
        }
        if (data != null && data.getString("serviceNumber") != null) {
            serviceNumber.setText(data.getString("serviceNumber"));
        }
        if (data != null && data.getDouble("amount") != 0) {
            paymentAmount = data.getDouble("amount");
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            amount.setText(decimalFormat.format(paymentAmount));
        }
    }

    private void setService(){
        if (data.getInt("serviceType") == SHIPMENT) {
            setShipment();
            type.setText("Envío");
            checkShipmentAction();
        }
        if (data.getInt("serviceType") == TRAVEL) {
            setTravel();
            type.setText("Viaje");
        }
    }

    private void checkShipmentAction(){
        ShipmentStatus status = (ShipmentStatus) data.getSerializable("newStatus");
        if (status == ShipmentStatus.ACCEPTED) {
            action.setText("Aceptar");
        }
        if (status == ShipmentStatus.COLLECTED) {
            action.setText("Recoger");
        }
        if (status == ShipmentStatus.TRANSIT) {
            action.setText("En Camino");
        }
        if (status == ShipmentStatus.DELIVERED) {
            action.setText("Entregar");
        }
        if (status == ShipmentStatus.DETAINED) {
            action.setText("Detener");
        }
        if (status == ShipmentStatus.FINISHED) {
            action.setText("Finalizar");
        }
    }

    private void setShipment(){
        if (data != null && data.getInt("shipmentId") != 0) {
            shipmentViewModel.readShipmentById(data.getInt("shipmentId")).observe(getViewLifecycleOwner(), new Observer<ShipmentDto>() {
                @Override
                public void onChanged(ShipmentDto shipmentDto) {
                    if (shipmentDto != null) {
                        shipment = shipmentDto;
                        setPartner();
                    }
                }
            });
        }
    }

    private void setTravel(){
        if (data != null && data.getInt("travelId") != 0) {
            travelViewModel.readTravelById(data.getInt("travelId")).observe(getViewLifecycleOwner(), new Observer<TravelDto>() {
                @Override
                public void onChanged(TravelDto travelDto) {
                    if (travelDto != null) {
                        travel = travelDto;
                        setPartner();
                    }
                }
            });
        }
    }

    private void setPartner(){
        userViewModel.readUser().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                if (userDto != null) {
                    partner = userDto;
                    checkVehicle();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void checkVehicle() {
        if (shipment != null && shipment.getVehicle() == null) {
            setAdapter();
        } else {
            spinnerVehicles.setVisibility(View.GONE);
            spinnerLabel.setVisibility(View.GONE);
        }
        if (travel != null && travel.getVehicle() == null) {
            setAdapter();
        } else {
            spinnerVehicles.setVisibility(View.GONE);
            spinnerLabel.setVisibility(View.GONE);
        }
    }

    private void setListeners() {
        spinnerVehicles.setOnItemSelectedListener(this);
        registration.setOnFocusChangeListener(this);
    }

    private void setButtonConfirmListener() {
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                try {
                    if (isFormValidate()){
                        checkService();
                    } else {
                        Toast.makeText(root.getContext()
                                ,"Verifique los datos del formulario",Toast.LENGTH_LONG).show();
                        v.setEnabled(true);
                    }
                } catch (JsonProcessingException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void checkService() throws JsonProcessingException, JSONException {
        if (data != null && data.getInt("serviceType") == SHIPMENT) {
            createShipmentAction();
        }
        if (data != null && data.getInt("serviceType") == TRAVEL) {
            createTravelAction();
        }
    }

    private void createShipmentAction() throws JsonProcessingException, JSONException {
        if (shipment != null && data.getSerializable("newStatus") != null) {
            ShipmentStatus status = (ShipmentStatus) data.getSerializable("newStatus");
            if (shipment.getStatus() != status) {
                shipment.setStatus(status);
                checkShipmentData();
                updateShipment();
            } else {
                Navigation.findNavController(root).navigate(
                        R.id.action_nav_service_available_action_detail_to_nav_shipment_available_empty, data);
                Toast.makeText(root.getContext(),
                        "No se ha podido actualizar el Envío",
                        Toast.LENGTH_LONG).show();
            }

        }
    }

    private void createTravelAction() throws JsonProcessingException, JSONException {
        if (travel != null && data.getSerializable("newStatus") != null) {
            TravelStatus status = (TravelStatus) data.getSerializable("newStatus");
            if (travel.getStatus() != status) {
                travel.setStatus(status);
                checkTravelData();
                updateTravel();
            } else {
                Navigation.findNavController(root).navigate(
                        R.id.action_nav_service_available_action_detail_to_nav_travellers_available_empty, data);
                Toast.makeText(root.getContext(),
                        "No se ha podido actualizar el Envío",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void checkShipmentData(){
        if (shipment.getPartner() == null) {
            shipment.setPartner(partner);
            shipment.setVehicle(vehicle);
        } else {
            checkServiceNewStatus();
        }
    }
    private void checkTravelData(){
        if (travel.getPartner() == null) {
            travel.setPartner(partner);
            travel.setVehicle(vehicle);
        } else {
            checkServiceNewStatus();
        }
    }

    private void checkServiceNewStatus() {
        if (data.getSerializable("newStatus") == ShipmentStatus.PAID) {
            shipment.setPartner(null);
            shipment.setVehicle(null);
        }
        if (data.getSerializable("newStatus") == TravelStatus.PAID) {
            travel.setPartner(null);
            travel.setVehicle(null);
        }
    }

    private void updateShipment() throws JsonProcessingException, JSONException {
        shipmentViewModel.updateShipment(shipment).observe(getViewLifecycleOwner(), new Observer<ShipmentDto>() {
            @Override
            public void onChanged(ShipmentDto shipmentDto) {
                if (shipmentDto != null) {
                    getActionOk();
                } else {
                    getActionError();
                }
            }
        });
    }

    private void updateTravel() throws JsonProcessingException, JSONException {
        travelViewModel.updateTravel(travel).observe(getViewLifecycleOwner(), new Observer<TravelDto>() {
            @Override
            public void onChanged(TravelDto travelDto) {
                if (travelDto != null) {
                    getActionOk();
                } else {
                    getActionError();
                }
            }
        });
    }

    private void setButtonCancelListener() {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                if (data.getInt("serviceType") == SHIPMENT) {
                    Navigation.findNavController(root).navigate(
                            R.id.action_nav_service_available_action_detail_to_nav_shipment_available_empty, data
                    );
                }
                if (data.getInt("serviceType") == TRAVEL) {
                    Navigation.findNavController(root).navigate(
                            R.id.action_nav_service_available_action_detail_to_nav_travellers_available_empty, data
                    );
                }
            }
        });
    }

    private void getActionOk() {
        data.putInt("image", R.drawable.ic_check_circle_black_24dp);
        data.putString("title", "Servicio Actualizado");
        data.putString("subtitle", "El servicio se actualizó correctamente.");
        Navigation.findNavController(root).navigate(
                R.id.action_nav_service_available_action_detail_to_nav_service_available_action_result, data);
        Toast.makeText(root.getContext(),
                "El servicio se actualizó correctamente",
                Toast.LENGTH_LONG).show();
    }

    private void getActionError() {
        data.putInt("image", R.drawable.ic_warning_black_24dp);
        data.putString("title", "Hemos tenido un problema");
        data.putString("subtitle", "No se ha podido realizar el pago.");
        Navigation.findNavController(root).navigate(
                R.id.action_nav_service_available_action_detail_to_nav_service_available_action_result, data);
        Toast.makeText(root.getContext(),
                "No se ha podido realizar el pago.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        vehicle = (VehicleDto) adapter.getItem(position);
        registration.clearFocus();
        spinnerVehicles.requestFocus();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Do nothing
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;
        if (editText.getText().toString().isEmpty()){
            editText.setError(ErrorStrings.EMPTY);
        }
        if (v.hasFocus()) {
            editText.setError(null);
        }
    }

    private boolean isFormValidate(){
        boolean isValidate = true;
        EditText[] editTexts = new EditText[]{
                registration
        };
        for (EditText editText:editTexts) {
            String text = editText.getText().toString();
            CharSequence error = editText.getError();
            if (error != null || text.isEmpty())
                isValidate = false;
        }
        return isValidate;
    }
}
