package com.movetto.activities.ui.shared;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.movetto.R;
import com.movetto.dtos.CardDto;
import com.movetto.dtos.DepositDto;
import com.movetto.dtos.PaymentDto;
import com.movetto.dtos.ServiceDto;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.ShipmentStatus;
import com.movetto.dtos.TravelDto;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.WalletDto;
import com.movetto.view_models.CardViewModel;
import com.movetto.view_models.CustomerViewModel;
import com.movetto.view_models.ShipmentViewModel;
import com.movetto.view_models.WalletViewModel;

import org.json.JSONException;

import java.text.DecimalFormat;

public class ServicePaymentDetailFragment extends Fragment {

    private static final int SHIPMENT = 1;
    private static final int TRAVEL = 2;

    private View root;
    private WalletViewModel walletViewModel;
    private ShipmentViewModel shipmentViewModel;
    private double paymentAmount;
    private TextView type;
    private TextView serviceNumber;
    private TextView amount;
    private ShipmentDto shipment;
    private TravelDto travel;
    private WalletDto wallet;
    private Button buttonPay;
    private Button buttonCancel;
    private Bundle data;

    public ServicePaymentDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setPaymentData();
        setButtonPayListener();
        setButtonCancelListener();
        return root;
    }

    private void setViewModels(){
        walletViewModel = new ViewModelProvider(this).get(WalletViewModel.class);
        shipmentViewModel = new ViewModelProvider(this).get(ShipmentViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_service_payment_detail, container, false);
    }

    private void setComponents() {
        type = root.findViewById(R.id.service_payment_detail_type_edit);
        serviceNumber = root.findViewById(R.id.service_payment_detail_number_edit);
        amount = root.findViewById(R.id.service_payment_detail_amount_edit);
        buttonPay = root.findViewById(R.id.service_payment_detail_pay_button);
        buttonCancel = root.findViewById(R.id.service_payment_detail_cancel_button);
        data = getArguments();
    }

    private void setPaymentData(){
        if (data != null && data.getString("customerUid") != null) {
            setWallet();
        }
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

    private void setWallet(){
        walletViewModel.readWallet(data.getString("customerUid")).observe(getViewLifecycleOwner(), new Observer<WalletDto>() {
            @Override
            public void onChanged(WalletDto walletDto) {
                wallet = walletDto;
            }
        });
    }

    private void setService(){
        if (data.getInt("serviceType") == SHIPMENT) {
            setShipment();
            type.setText("Envío");
        }
        if (data.getInt("serviceType") == TRAVEL) {
            setTravel();
            type.setText("Viaje");
        }
    }

    private void setShipment(){
        if (data != null && data.getInt("serviceId") != 0) {
            shipmentViewModel.readShipmentById(data.getInt("shipmentId")).observe(getViewLifecycleOwner(), new Observer<ShipmentDto>() {
                @Override
                public void onChanged(ShipmentDto shipmentDto) {
                    if (shipmentDto != null) {
                        shipment = shipmentDto;
                    }
                }
            });
        }
    }

    private void setTravel(){
        if (data != null && data.getInt("serviceId") != 0) {
            //TODO
        }
    }

    private void setButtonPayListener() {
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wallet != null) {
                    try {
                        checkService();
                    } catch (JsonProcessingException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Navigation.findNavController(root).navigate(
                            R.id.action_nav_service_payment_detail_to_nav_wallet
                    );
                }
            }
        });
    }

    private void checkService() throws JsonProcessingException, JSONException {
        if (data != null && data.getInt("shipmentId") != 0) {
            createShipmentPayment();
            updateShipment();
        }
        if (data != null && data.getInt("travelId") != 0) {
            createTravelPayment();
            updateTravel();
        }
    }

    private void createShipmentPayment(){
        if (shipment.getStatus() == ShipmentStatus.SAVED){
            PaymentDto paymentDto = new PaymentDto(paymentAmount,shipment);
            wallet.getTransactions().add(paymentDto);
            shipment.setStatus(ShipmentStatus.ACCEPTED);
            System.out.println(shipment.toString());
        }
    }

    private void createTravelPayment(){
        PaymentDto paymentDto = new PaymentDto(paymentAmount,travel);
        wallet.getTransactions().add(paymentDto);
    }

    private void updateShipment() throws JsonProcessingException, JSONException {
        shipmentViewModel.updateShipment(shipment).observe(getViewLifecycleOwner(), new Observer<ShipmentDto>() {
            @Override
            public void onChanged(ShipmentDto shipmentDto) {
                if (shipmentDto != null) {
                    try {
                        updateWallet();
                    } catch (JsonProcessingException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    getPaymentError();
                }
            }
        });
    }

    private void updateTravel(){
        //TODO
    }

    private void updateWallet() throws JsonProcessingException, JSONException {
        walletViewModel.updateWallet(wallet).observe(getViewLifecycleOwner(), new Observer<WalletDto>() {
            @Override
            public void onChanged(WalletDto walletDto) {
                if (walletDto != null) {
                    getPaymentOk();
                } else {
                    getPaymentError();
                }
            }
        });
    }

    private void setButtonCancelListener() {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(
                        R.id.action_nav_service_payment_detail_to_nav_shipment_detail
                );
            }
        });
    }

    private void getPaymentOk() {
        data.putInt("image", R.drawable.ic_check_circle_black_24dp);
        data.putString("title", "Pago Realizado");
        data.putString("subtitle", "El pago se ha realizado correctamente.");
        Navigation.findNavController(root).navigate(
                R.id.action_nav_service_payment_detail_to_nav_service_payment_result, data);
        Toast.makeText(root.getContext(),
                "El pago se ha realizado correctamente",
                Toast.LENGTH_LONG).show();
    }

    private void getPaymentError() {
        data.putInt("image", R.drawable.ic_warning_black_24dp);
        data.putString("title", "Hemos tenido un problema");
        data.putString("subtitle", "No se ha podido realizar el pago.");
        Navigation.findNavController(root).navigate(
                R.id.action_nav_service_payment_detail_to_nav_service_payment_result, data);
        Toast.makeText(root.getContext(),
                "No se ha podido realizar el depósito.",
                Toast.LENGTH_LONG).show();
    }
}
