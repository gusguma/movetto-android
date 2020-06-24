package com.movetto.activities.ui.packages;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movetto.R;
import com.movetto.adapters.ShipmentListAdapter;
import com.movetto.dtos.ShipmentDto;
import com.movetto.view_models.ShipmentViewModel;

import java.util.List;

public class ShipmentAvailableListAvailableFragment extends Fragment {

    private View root;
    private ShipmentViewModel shipmentViewModel;
    private ShipmentListAdapter adapter;
    private ConstraintLayout progressBar;
    private TextView shipmentsAvailableEmpty;
    private TextView shipmentsPendingEmpty;
    private TextView shipmentsFinishedEmpty;

    private String partnerUid;
    private Bundle data;

    public ShipmentAvailableListAvailableFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setObservers();
        setAdapterListener();
        return root;
    }

    private void setViewModels(){
        shipmentViewModel = new ViewModelProvider(this).get(ShipmentViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container){
        root = inflater.inflate(R.layout.fragment_shipment_available_list_available
                , container, false);
    }

    private void setComponents(){
        data = getArguments();
        setPartnerUid();
        RecyclerView recyclerView = root.findViewById(R.id.shipment_available_list_available_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new ShipmentListAdapter();
        recyclerView.setAdapter(adapter);
        progressBar = root.findViewById(R.id.shipment_available_list_available_progress_bar);
        shipmentsAvailableEmpty = root.findViewById(R.id.shipment_available_list_available_no_available);
        shipmentsPendingEmpty = root.findViewById(R.id.shipment_available_list_available_no_pending);
        shipmentsPendingEmpty.setVisibility(View.GONE);
        shipmentsFinishedEmpty = root.findViewById(R.id.shipment_available_list_available_no_finished);
        shipmentsFinishedEmpty.setVisibility(View.GONE);
    }

    private void setPartnerUid() {
        if (data != null && data.getString("partnerUid") != null) {
            partnerUid = data.getString("partnerUid");
        }
    }

    private void setObservers(){
        shipmentViewModel.readShipmentsAvailable(partnerUid).observe(getViewLifecycleOwner(), new Observer<List<ShipmentDto>>() {
            @Override
            public void onChanged(List<ShipmentDto> shipmentDtos) {
                if (!shipmentDtos.isEmpty()) {
                    adapter.setShipments(shipmentDtos);
                    progressBar.setVisibility(View.GONE);
                    shipmentsAvailableEmpty.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setAdapterListener(){
        adapter.setOnItemClickListener(new ShipmentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ShipmentDto shipment) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("shipment", shipment);
                bundle.putInt("serviceId", shipment.getId());
                Navigation.findNavController(root).navigate(
                        R.id.action_nav_shipment_available_list_to_nav_shipment_available_detail, bundle);
            }
        });
    }
}
