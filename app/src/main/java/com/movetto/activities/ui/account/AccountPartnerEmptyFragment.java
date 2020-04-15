package com.movetto.activities.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.movetto.R;

public class AccountPartnerEmptyFragment extends Fragment {

    private View root;
    private Button buttonPartnerSignUp;
    private Button buttonLater;

    public AccountPartnerEmptyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_account_partner_empty, container, false);
        buttonPartnerSignUp = root.findViewById(R.id.account_partner_empty_create_button);
        buttonLater = root.findViewById(R.id.account_partner_empty_back_button);
        setButtonPartnerSignUp();
        setButtonLater();
        return root;
    }

    private void setButtonPartnerSignUp(){
        buttonPartnerSignUp.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_nav_account_partner_empty_to_nav_account_partner_reg,null)
        );
    }

    private void setButtonLater(){
        buttonLater.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_nav_account_partner_empty_to_nav_account,null)
        );
    }
}
