package com.movetto.activities.ui.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.movetto.R;
import com.movetto.adapters.AccountPartnerVehiclesAdapter;
import com.movetto.dtos.PartnerDto;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.VehicleDto;
import com.movetto.view_models.PartnerViewModel;
import com.movetto.view_models.UserViewModel;
import com.movetto.view_models.VehicleViewModel;

import java.util.ArrayList;
import java.util.List;

public class AccountPartnerVehiclesFragment extends Fragment {

    private PartnerViewModel partnerViewModel;
    private View root;
    private AccountPartnerVehiclesAdapter accountPartnerVehiclesAdapter;
    private UserDto userOutputDto;
    private PartnerDto partnerDto;
    private FloatingActionButton addVehicleButton;

    public AccountPartnerVehiclesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setListeners();
        setObservers();
        setAdapterListener();
        return root;
    }

    private void setViewModels(){
        partnerViewModel = new ViewModelProvider(this).get(PartnerViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container){
        root = inflater.inflate(R.layout.fragment_account_partner_vehicles
                , container, false);
    }

    private void setComponents(){
        RecyclerView recyclerView = root.findViewById(R.id.account_partner_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        accountPartnerVehiclesAdapter = new AccountPartnerVehiclesAdapter();
        recyclerView.setAdapter(accountPartnerVehiclesAdapter);
        addVehicleButton = root.findViewById(R.id.account_partner_add_vehicle_button);
    }

    private void setListeners(){
        addVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setPartnerBundle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setObservers(){
        partnerViewModel.readPartner().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                userOutputDto = userDto;
                partnerDto = userDto.getPartner();
                List<VehicleDto> vehicles = new ArrayList<>(partnerDto.getVehicles());
                accountPartnerVehiclesAdapter.setVehicles(vehicles);
            }
        });
    }

    private void setAdapterListener(){
        accountPartnerVehiclesAdapter.setOnItemClickListener(new AccountPartnerVehiclesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VehicleDto vehicle) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", userOutputDto);
                bundle.putInt("id", vehicle.getId());
                Navigation.findNavController(root).navigate(
                        R.id.action_nav_account_partner_to_nav_account_reg_vehicle, bundle);
            }
        });
    }

    private void setPartnerBundle(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",userOutputDto);
        Navigation.findNavController(root).navigate(
                R.id.action_nav_account_partner_to_nav_account_reg_vehicle, bundle);
    }
}
