package com.movetto.activities.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.movetto.R;
import com.movetto.dtos.UserDto;
import com.movetto.view_models.CustomerViewModel;
import com.movetto.view_models.PartnerViewModel;

public class AccountLandFragment extends Fragment {

    private View root;
    private CustomerViewModel customerViewModel;
    private PartnerViewModel partnerViewModel;
    private Button buttonPartner;
    private Button buttonCustomer;
    private UserDto customerDto;
    private UserDto partnerDto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customerViewModel =
                new ViewModelProvider(this).get(CustomerViewModel.class);
        partnerViewModel =
                new ViewModelProvider(this).get(PartnerViewModel.class);
        root = inflater.inflate(R.layout.fragment_account_land, container, false);
        buttonCustomer = root.findViewById(R.id.account_land_customer_button);
        buttonPartner = root.findViewById(R.id.account_land_partner_button);
        customerDto = new UserDto();
        partnerDto = new UserDto();
        setButtonCustomer();
        setButtonPartner();
        setCustomerDataInput();
        setPartnerDataInput();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setButtonCustomer();
        setButtonPartner();
    }

    private void setButtonCustomer(){
        buttonCustomer.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_nav_account_to_nav_account_customer_empty,null)
        );
    }

    private void setButtonPartner(){
        buttonPartner.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_nav_account_to_nav_account_partner_empty,null)
            );
    }

    private void setCustomerDataInput() {
        customerViewModel.readCustomer().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                customerDto = userDto;
                if(customerDto != null){
                    buttonCustomer.setOnClickListener(
                            Navigation.createNavigateOnClickListener(
                                    R.id.action_nav_account_to_nav_account_customer,null)
                    );
                }
            }
        });
    }

    private void setPartnerDataInput() {
        partnerViewModel.readPartner().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                partnerDto = userDto;
                if(partnerDto != null){
                    buttonPartner.setOnClickListener(
                            Navigation.createNavigateOnClickListener(
                                    R.id.action_nav_account_to_nav_account_partner,null)
                    );
                }
            }
        });
    }
}