package com.movetto.activities.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.movetto.R;

public class AccountLandFragment extends Fragment {

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_account_land, container, false);
        setButtonPartner();
        setButtonCustomer();
        return root;
    }

    public void setButtonPartner(){
        Button buttonPartner = root.findViewById(R.id.account_land_partner_button);
        buttonPartner.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_nav_account_to_nav_account_partner,null)
        );
    }

    public void setButtonCustomer(){
        Button buttonPartner = root.findViewById(R.id.account_land_customer_button);
        buttonPartner.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_nav_account_to_nav_account_customer,null)
        );
    }
}