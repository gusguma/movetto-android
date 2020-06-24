package com.movetto.activities.ui.packages;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.movetto.R;
import com.movetto.activities.MainActivity;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.UserDto;
import com.movetto.view_models.PartnerViewModel;
import com.movetto.view_models.ShipmentViewModel;

import java.util.List;
import java.util.Objects;

public class ShipmentsAvailableEmptyFragment extends Fragment {

    private View root;
    private PartnerViewModel partnerViewModel;
    private ShipmentViewModel shipmentViewModel;
    private UserDto partner;
    private List<ShipmentDto> shipments;
    private Button buttonBack;
    private ConstraintLayout progressBar;
    private Bundle data;
    private int count;

    public ShipmentsAvailableEmptyFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setViewModels();
        setLayout(inflater,container);
        setComponents();
        getPartner();
        setButtonBack();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        shipments = null;
    }

    private void setViewModels() {
        partnerViewModel = new ViewModelProvider(this).get(PartnerViewModel.class);
        shipmentViewModel = new ViewModelProvider(this).get(ShipmentViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_shipment_available_land, container, false);
    }

    private void setComponents() {
        data = new Bundle();
        buttonBack = root.findViewById(R.id.shipment_available_empty_button);
        progressBar = root.findViewById(R.id.shipment_available_progress_bar);
    }

    private void getPartner() {
        partnerViewModel.readPartner().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                if(userDto != null){
                    partner = userDto;
                    data.putString("partnerUid", partner.getUid());
                    count += 1;
                    if (count == 1) {
                        readShipmentsByPartner();
                    }
                } else {
                    setPartnerEmpty();
                }
            }
        });
    }

    private void readShipmentsByPartner(){
        shipmentViewModel.readShipmentsByPartnerUid(partner.getUid()).observe(getViewLifecycleOwner(), new Observer<List<ShipmentDto>>() {
            @Override
            public void onChanged(List<ShipmentDto> shipmentDtos) {
                if (shipmentDtos.isEmpty()) {
                    count += 1;
                    if (count == 2) {
                        readShipmentsAvailable();
                    }
                } else {
                    getShipmentListFragment();
                }
            }
        });
    }

    private void readShipmentsAvailable(){
        shipmentViewModel.readShipmentsAvailable(partner.getUid()).observe(getViewLifecycleOwner(), new Observer<List<ShipmentDto>>() {
            @Override
            public void onChanged(List<ShipmentDto> shipmentDtos) {
                if (shipmentDtos.isEmpty()){
                    count += 1;
                    if (count > 3) {
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    getShipmentListFragment();
                }
            }
        });
    }

    private void getShipmentListFragment() {
        if (Objects.requireNonNull(Navigation.findNavController(root)
                .getCurrentDestination())
                .getId() == R.id.nav_shipment_available_empty) {
            Navigation.findNavController(root)
                    .navigate(R.id.action_nav_shipment_available_empty_to_nav_shipment_available_list, data);
            getParentFragmentManager()
                    .beginTransaction()
                    .remove(ShipmentsAvailableEmptyFragment.this)
                    .commit();
        }
    }

    private void setPartnerEmpty(){
        Navigation.findNavController(root)
                .navigate(R.id.action_nav_shipment_available_empty_to_nav_account_partner_empty);
    }

    private void setButtonBack(){
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), MainActivity.class));
                Objects.requireNonNull(getActivity()).finish();
            }
        });
    }
}