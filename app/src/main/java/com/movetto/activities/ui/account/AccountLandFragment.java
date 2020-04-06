package com.movetto.activities.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        customerViewModel =
                new ViewModelProvider(this).get(CustomerViewModel.class);
        partnerViewModel =
                new ViewModelProvider(this).get(PartnerViewModel.class);
        root = inflater.inflate(R.layout.fragment_account_land, container, false);
        setButtonPartner();
        setButtonCustomer();
        return root;
    }

    private void setButtonPartner(){
        Button buttonPartner = root.findViewById(R.id.account_land_partner_button);
        if (checkUserPartnerExist() == null){
            buttonPartner.setOnClickListener(
                    Navigation.createNavigateOnClickListener(
                            R.id.action_nav_account_to_nav_account_partner_empty,null)
            );
        } else {
            buttonPartner.setOnClickListener(
                    Navigation.createNavigateOnClickListener(
                            R.id.action_nav_account_to_nav_account_partner,null)
            );
        }
    }

    private void setButtonCustomer(){
        Button buttonCustomer = root.findViewById(R.id.account_land_customer_button);
        if (checkUserCustomerExist() == null){
            buttonCustomer.setOnClickListener(
                    Navigation.createNavigateOnClickListener(
                            R.id.action_nav_account_to_nav_account_customer_empty,null)
            );
        } else {
            buttonCustomer.setOnClickListener(
                    Navigation.createNavigateOnClickListener(
                            R.id.action_nav_account_to_nav_account_customer,null)
            );
        }
    }

    private UserDto checkUserCustomerExist(){
        return customerViewModel.readCustomer();
    }

    private UserDto checkUserPartnerExist(){
        return partnerViewModel.readPartner();
    }
}