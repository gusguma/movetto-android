package com.movetto.activities.ui.travel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.movetto.R;
import com.movetto.activities.MainActivity;
import com.movetto.activities.ui.shipments.ShipmentsEmptyFragment;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.TravelDto;
import com.movetto.dtos.UserDto;
import com.movetto.view_models.CustomerViewModel;
import com.movetto.view_models.TravelViewModel;

import java.util.List;
import java.util.Objects;

public class TravelEmptyFragment extends Fragment {

    private View root;
    private CustomerViewModel customerViewModel;
    private TravelViewModel travelViewModel;
    private UserDto customer;
    private Button buttonContinue;
    private Button buttonLater;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater,container);
        setComponents();
        getCustomer();
        setButtonLater();
        return root;
    }

    private void setViewModels() {
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        travelViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_travel_empty, container, false);
    }

    private void setComponents() {
        buttonContinue = root.findViewById(R.id.travel_land_continue_button);
        buttonLater = root.findViewById(R.id.travel_land_later_button);
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
                    setButtonContinueEmpty();
                }
            }
        });
    }

    private void readShipments(){
        travelViewModel.readTravelsByUid(customer.getUid()).observe(getViewLifecycleOwner(), new Observer<List<TravelDto>>() {
            @Override
            public void onChanged(List<TravelDto> shipmentDtos) {
                if (!shipmentDtos.isEmpty()){
                    Navigation.findNavController(root)
                            .navigate(R.id.action_nav_travel_empty_to_nav_travel_list);
                    getParentFragmentManager()
                            .beginTransaction()
                            .remove(TravelEmptyFragment.this)
                            .commit();
                }
            }
        });
    }

    private void setButtonContinueExist(){
        buttonContinue.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_nav_travel_empty_to_nav_travel_start,null)
        );
    }

    private void setButtonContinueEmpty(){
        buttonContinue.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_nav_travel_empty_to_nav_account_customer_empty,null)
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