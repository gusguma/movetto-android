package com.movetto.activities.ui.travellers;

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
import com.movetto.dtos.TravelDto;
import com.movetto.dtos.UserDto;
import com.movetto.view_models.PartnerViewModel;
import com.movetto.view_models.TravelViewModel;

import java.util.List;
import java.util.Objects;

public class TravellerAvailableEmptyFragment extends Fragment {

    private View root;
    private PartnerViewModel partnerViewModel;
    private TravelViewModel travelViewModel;
    private UserDto partner;
    private List<TravelDto> travels;
    private Button buttonBack;
    private ConstraintLayout progressBar;
    private Bundle data;

    public TravellerAvailableEmptyFragment() {
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
        travels = null;
    }

    private void setViewModels() {
        partnerViewModel = new ViewModelProvider(this).get(PartnerViewModel.class);
        travelViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_traveller_land, container, false);
    }

    private void setComponents() {
        data = new Bundle();
        buttonBack = root.findViewById(R.id.traveller_land_button);
        progressBar = root.findViewById(R.id.traveller_land_progress_bar);
    }

    private void getPartner() {
        partnerViewModel.readPartner().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                if(userDto != null){
                    partner = userDto;
                    data.putString("partnerUid", partner.getUid());
                    readTravelsByPartner();
                } else {
                    setPartnerEmpty();
                }
            }
        });
    }

    private void readTravelsByPartner(){
        travelViewModel.readTravelsByPartnerUid(partner.getUid()).observe(getViewLifecycleOwner(), new Observer<List<TravelDto>>() {
            @Override
            public void onChanged(List<TravelDto> travelDtos) {
                if (!travelDtos.isEmpty()) {
                    System.out.println("travelbypartner vacio");
                    readTravelsAvailable();
                } else {
                    getTravelListFragment();
                }
            }
        });
    }

    private void readTravelsAvailable(){
        travelViewModel.readTravelsAvailable(partner.getUid()).observe(getViewLifecycleOwner(), new Observer<List<TravelDto>>() {
            @Override
            public void onChanged(List<TravelDto> travelDtos) {
                if (travelDtos.isEmpty()){
                    System.out.println("travelsAvailable vacio");
                    progressBar.setVisibility(View.GONE);
                } else {
                    getTravelListFragment();
                }
            }
        });
    }

    private void getTravelListFragment() {
        if (Objects.requireNonNull(Navigation.findNavController(root)
                .getCurrentDestination())
                .getId() == R.id.nav_travellers_available_empty) {
            Navigation.findNavController(root)
                    .navigate(R.id.action_nav_travellers_available_empty_to_nav_traveller_available_list, data);
            getParentFragmentManager()
                    .beginTransaction()
                    .remove(TravellerAvailableEmptyFragment.this)
                    .commit();
        }
    }

    private void setPartnerEmpty(){
        Navigation.findNavController(root)
                .navigate(R.id.action_nav_travellers_available_empty_to_nav_account_partner_empty);
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