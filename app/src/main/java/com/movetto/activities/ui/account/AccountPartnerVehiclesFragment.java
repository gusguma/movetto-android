package com.movetto.activities.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movetto.R;
import com.movetto.adapters.AccountPartnerVehiclesAdapter;
import com.movetto.dtos.PartnerDto;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.VehicleDto;
import com.movetto.view_models.PartnerViewModel;
import com.movetto.view_models.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class AccountPartnerVehiclesFragment extends Fragment {

    private PartnerViewModel partnerViewModel;
    private View root;
    private AccountPartnerVehiclesAdapter accountPartnerVehiclesAdapter;
    private PartnerDto partnerDto;

    public AccountPartnerVehiclesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setObservers();
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
    }

    private void setObservers(){
        partnerViewModel.readPartner().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                partnerDto = userDto.getPartner();
                List<VehicleDto> vehicleDtos = new ArrayList<>(partnerDto.getVehicles());
                accountPartnerVehiclesAdapter.setVehicles(vehicleDtos);
            }
        });
    }
}
