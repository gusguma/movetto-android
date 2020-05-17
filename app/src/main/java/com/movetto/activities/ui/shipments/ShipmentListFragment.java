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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.movetto.R;
import com.movetto.adapters.AccountPartnerVehiclesAdapter;
import com.movetto.adapters.ShipmentListAdapter;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.VehicleDto;
import com.movetto.view_models.CustomerViewModel;
import com.movetto.view_models.ShipmentViewModel;
import com.movetto.view_models.UserViewModel;

import java.util.List;

public class ShipmentListFragment extends Fragment {

    private View root;
    private ShipmentViewModel shipmentViewModel;
    private RecyclerView recyclerView;
    private ShipmentListAdapter adapter;
    private FirebaseUser user;
    private FloatingActionButton addButton;

    public ShipmentListFragment() {
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

    private void setViewModels() {
        shipmentViewModel = new ViewModelProvider(this).get(ShipmentViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_shipment_list, container, false);
    }

    private void setComponents() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = root.findViewById(R.id.shipment_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new ShipmentListAdapter();
        recyclerView.setAdapter(adapter);
        addButton = root.findViewById(R.id.shipment_list_add_button);
    }

    private void setListeners(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root)
                        .navigate(R.id.action_nav_shipments_list_to_nav_shipments_start);
            }
        });
    }

    private void setObservers(){
        shipmentViewModel.readShipmentsByUid(user.getUid()).observe(getViewLifecycleOwner(), new Observer<List<ShipmentDto>>() {
            @Override
            public void onChanged(List<ShipmentDto> shipmentDtos) {
                adapter.setShipments(shipmentDtos);
            }
        });
    }
    private void setAdapterListener(){
        adapter.setOnItemClickListener(new ShipmentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ShipmentDto shipment) {
                Bundle bundle = new Bundle();
                bundle.putInt("shipmentId", shipment.getId());
                Navigation.findNavController(root).navigate(
                        R.id.action_nav_shipments_list_to_nav_shipment_detail, bundle);
            }
        });
    }
}
