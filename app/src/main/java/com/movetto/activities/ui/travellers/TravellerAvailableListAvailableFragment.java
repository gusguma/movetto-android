package com.movetto.activities.ui.travellers;

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
import com.movetto.adapters.TravelListAdapter;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.TravelDto;
import com.movetto.view_models.ShipmentViewModel;
import com.movetto.view_models.TravelViewModel;

import java.util.List;

public class TravellerAvailableListAvailableFragment extends Fragment {

    private View root;
    private TravelViewModel travelViewModel;
    private TravelListAdapter adapter;
    private ConstraintLayout progressBar;
    private TextView travelsAvailableEmpty;
    private TextView travelsPendingEmpty;
    private TextView travelsFinishedEmpty;

    private String partnerUid;
    private Bundle data;

    public TravellerAvailableListAvailableFragment() {
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
        travelViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container){
        root = inflater.inflate(R.layout.fragment_traveller_available_list_available
                , container, false);
    }

    private void setComponents(){
        data = getArguments();
        setPartnerUid();
        RecyclerView recyclerView = root.findViewById(R.id.travel_available_list_available_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new TravelListAdapter();
        recyclerView.setAdapter(adapter);
        progressBar = root.findViewById(R.id.travel_available_list_available_progress_bar);
        travelsAvailableEmpty = root.findViewById(R.id.travel_available_list_available_no_available);
        travelsPendingEmpty = root.findViewById(R.id.travel_available_list_available_no_pending);
        travelsPendingEmpty.setVisibility(View.GONE);
        travelsFinishedEmpty = root.findViewById(R.id.travel_available_list_available_no_finished);
        travelsFinishedEmpty.setVisibility(View.GONE);
    }

    private void setPartnerUid() {
        if (data != null && data.getString("partnerUid") != null) {
            partnerUid = data.getString("partnerUid");
        }
    }

    private void setObservers(){
        travelViewModel.readTravelsAvailable(partnerUid).observe(getViewLifecycleOwner(), new Observer<List<TravelDto>>() {
            @Override
            public void onChanged(List<TravelDto> travelDtos) {
                if (!travelDtos.isEmpty()) {
                    adapter.setTravels(travelDtos);
                    progressBar.setVisibility(View.GONE);
                    travelsAvailableEmpty.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setAdapterListener(){
        adapter.setOnItemClickListener(new TravelListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TravelDto travel) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("travel", travel);
                bundle.putInt("serviceId", travel.getId());
                Navigation.findNavController(root).navigate(
                        R.id.action_nav_traveller_available_list_to_nav_traveller_available_detail, bundle);
            }
        });
    }
}
