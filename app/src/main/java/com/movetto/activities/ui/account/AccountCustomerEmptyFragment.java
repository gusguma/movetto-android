package com.movetto.activities.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.movetto.R;

public class AccountCustomerEmptyFragment extends Fragment {

    private View root;

    public AccountCustomerEmptyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_account_customer_empty, container, false);
        setButtonCustomerSignUp();
        setButtonLater();
        return root;
    }

    private void setButtonCustomerSignUp(){
        Button buttonCustomerSignUp = root.findViewById(R.id.account_customer_empty_create_button);
        buttonCustomerSignUp.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_nav_account_customer_empty_to_nav_account_customer_reg,null)
        );
    }

    private void setButtonLater(){
        Button buttonLater = root.findViewById(R.id.account_customer_empty_back_button);
        buttonLater.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_nav_account_customer_empty_to_nav_account,null)
        );
    }

}
