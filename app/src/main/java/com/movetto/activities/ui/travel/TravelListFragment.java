package com.movetto.activities.ui.travel;

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
import com.movetto.adapters.TravelListAdapter;
import com.movetto.dtos.TravelDto;
import com.movetto.view_models.TravelViewModel;

import java.util.List;

public class TravelListFragment extends Fragment {

    private static final int TRAVEL = 2;

    private View root;
    private TravelViewModel travelViewModel;
    private RecyclerView recyclerView;
    private TravelListAdapter adapter;
    private FirebaseUser user;
    private FloatingActionButton addButton;

    public TravelListFragment() {
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
        travelViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_travel_list, container, false);
    }

    private void setComponents() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = root.findViewById(R.id.travel_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new TravelListAdapter();
        recyclerView.setAdapter(adapter);
        addButton = root.findViewById(R.id.travel_list_add_button);
    }

    private void setListeners(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root)
                        .navigate(R.id.action_nav_travel_list_to_nav_travel_start);
            }
        });
    }

    private void setObservers(){
        travelViewModel.readTravelsByUid(user.getUid()).observe(getViewLifecycleOwner(), new Observer<List<TravelDto>>() {
            @Override
            public void onChanged(List<TravelDto> travels) {
                adapter.setTravels(travels);
            }
        });
    }

    private void setAdapterListener(){
        adapter.setOnItemClickListener(new TravelListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TravelDto travel) {
                Bundle bundle = new Bundle();
                bundle.putString("customerUid", user.getUid());
                bundle.putInt("serviceType", TRAVEL );
                bundle.putInt("serviceId", travel.getId());
                bundle.putInt("travelId", travel.getId());
                Navigation.findNavController(root).navigate(
                        R.id.action_nav_travel_list_to_nav_travel_detail, bundle);
            }
        });
    }
}
