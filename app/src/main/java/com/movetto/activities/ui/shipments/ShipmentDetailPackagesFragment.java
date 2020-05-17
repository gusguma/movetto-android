package com.movetto.activities.ui.shipments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.movetto.R;
import com.movetto.adapters.AccountPartnerVehiclesAdapter;
import com.movetto.adapters.ShipmentDetailPackagesAdapter;
import com.movetto.dtos.PackageDto;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.VehicleDto;
import com.movetto.view_models.PartnerViewModel;
import com.movetto.view_models.ShipmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShipmentDetailPackagesFragment extends Fragment {

    private View root;
    private ShipmentViewModel shipmentViewModel;
    private ShipmentDetailPackagesAdapter adapter;
    private ShipmentDto shipment;
    private Bundle data;
    private FloatingActionButton addPackageButton;

    public ShipmentDetailPackagesFragment() {
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
        shipmentViewModel = new ViewModelProvider(this).get(ShipmentViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container){
        root = inflater.inflate(R.layout.fragment_shipment_detail_packages
                , container, false);
    }

    private void setComponents(){
        RecyclerView recyclerView = root.findViewById(R.id.packages_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new ShipmentDetailPackagesAdapter();
        recyclerView.setAdapter(adapter);
        addPackageButton = root.findViewById(R.id.packages_list_add_button);
    }

    private void setListeners(){
        addPackageButton.setOnClickListener(new View.OnClickListener() {
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
        shipmentViewModel.readShipmentById(getShipmentId()).observe(getViewLifecycleOwner(), new Observer<ShipmentDto>() {
            @Override
            public void onChanged(ShipmentDto shipmentDto) {
                shipment = shipmentDto;
                List<PackageDto> packages = new ArrayList<>(shipmentDto.getPackages());
                adapter.setPackages(packages);
            }
        });
    }

    private int getShipmentId(){
        assert getParentFragment() != null;
        data = getParentFragment().getArguments();
        if (data != null && data.getInt("shipmentId") != 0){
            return data.getInt("shipmentId");
        }
        return 0;
    }

    private void setAdapterListener(){
        adapter.setOnItemClickListener(new ShipmentDetailPackagesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PackageDto packageDto) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("shipment", shipment);
                bundle.putInt("packageId", packageDto.getId());
                Navigation.findNavController(root).navigate(
                        R.id.action_nav_shipment_detail_to_nav_shipment_add_update_package, bundle);
            }
        });
    }

    private void setPartnerBundle(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("shipment", shipment);
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipment_detail_to_nav_shipment_add_update_package, bundle);
    }
}
