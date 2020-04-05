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
import com.movetto.view_models.AccountViewModel;
import com.movetto.view_models.WalletViewModel;

public class AccountLandFragment extends Fragment {

    private View root;
    private AccountViewModel accountViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);
        root = inflater.inflate(R.layout.fragment_account_land, container, false);
        setButtonPartner();
        setButtonCustomer();
        return root;
    }

    private void setButtonPartner(){
        Button buttonPartner = root.findViewById(R.id.account_land_partner_button);
        if (checkUserPartnerExist()){
            buttonPartner.setOnClickListener(
                    Navigation.createNavigateOnClickListener(
                            R.id.action_nav_account_to_nav_account_partner,null)
            );
        } else {
            buttonPartner.setOnClickListener(
                    Navigation.createNavigateOnClickListener(
                            R.id.action_nav_account_to_nav_account_partner_empty,null)
            );
        }
    }

    private void setButtonCustomer(){
        Button buttonCustomer = root.findViewById(R.id.account_land_customer_button);
        if (checkUserCustomerExist()){
            buttonCustomer.setOnClickListener(
                    Navigation.createNavigateOnClickListener(
                            R.id.action_nav_account_to_nav_account_customer,null)
            );
        } else {
            buttonCustomer.setOnClickListener(
                    Navigation.createNavigateOnClickListener(
                            R.id.action_nav_account_to_nav_account_customer_empty,null)
            );
        }
    }

    private boolean checkUserCustomerExist(){
        return accountViewModel.checkUserCustomerExist();
    }

    private boolean checkUserPartnerExist(){
        return accountViewModel.checkUserPartnerExist();
    }
}