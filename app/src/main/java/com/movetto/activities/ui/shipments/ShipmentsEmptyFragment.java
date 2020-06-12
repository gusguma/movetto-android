package com.movetto.activities.ui.shipments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.movetto.R;
import com.movetto.activities.MainActivity;
import com.movetto.dtos.CustomerDto;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.UserDto;
import com.movetto.view_models.CustomerViewModel;
import com.movetto.view_models.ShipmentViewModel;

import java.util.List;
import java.util.Objects;

public class ShipmentsEmptyFragment extends Fragment {

    private View root;
    private CustomerViewModel customerViewModel;
    private ShipmentViewModel shipmentViewModel;
    private UserDto customer;
    private Button buttonContinue;
    private Button buttonLater;
    private ConstraintLayout progressBar;

    public ShipmentsEmptyFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater,container);
        setComponents();
        getCustomer();
        setButtonLater();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setViewModels() {
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        shipmentViewModel = new ViewModelProvider(this).get(ShipmentViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_shipment_empty, container, false);
    }

    private void setComponents() {
        buttonContinue = root.findViewById(R.id.shipment_land_continue_button);
        buttonLater = root.findViewById(R.id.shipment_land_later_button);
        progressBar = root.findViewById(R.id.shipment_land_progress_bar);
    }

    private void getCustomer() {
        customerViewModel.readCustomer().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                if(userDto != null){
                    customer = userDto;
                    setButtonContinueExist();
                    readShipments();
                } else {
                    progressBar.setVisibility(View.GONE);
                    setButtonContinueEmpty();
                }
            }
        });
    }

    private void readShipments(){
        shipmentViewModel.readShipmentsByUid(customer.getUid()).observe(getViewLifecycleOwner(), new Observer<List<ShipmentDto>>() {
            @Override
            public void onChanged(List<ShipmentDto> shipmentDtos) {
                if (!shipmentDtos.isEmpty()){
                    Navigation.findNavController(root)
                            .navigate(R.id.action_nav_shipments_empty_to_nav_shipments_list);
                    getParentFragmentManager()
                            .beginTransaction()
                            .remove(ShipmentsEmptyFragment.this)
                            .commit();
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setButtonContinueExist(){
        buttonContinue.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_nav_shipments_empty_to_nav_shipments_start,null)
        );
    }

    private void setButtonContinueEmpty(){
        buttonContinue.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_nav_shipments_empty_to_nav_account_customer_empty,null)
        );
    }

    private void setButtonLater(){
        buttonLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), MainActivity.class));
                Objects.requireNonNull(getActivity()).finish();
            }
        });
    }
}