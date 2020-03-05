package com.movetto.activities.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.movetto.R;
import com.movetto.activities.MainMenuActivity;

public class AccountCustomerFragment extends Fragment {

    private View root;
    private FirebaseUser user;

    public AccountCustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_account_customer, container, false);
        setUser();
        setCustomerData();
        setEditCustomerData();
        return root;
    }

    private void setUser(){
        this.user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void setCustomerData(){
        TextView customerName = root.findViewById(R.id.account_customer_name);
        TextView customerEmail = root.findViewById(R.id.account_customer_email);
        customerName.setText(user.getDisplayName());
        customerEmail.setText(user.getEmail());
    }

    private void setEditCustomerData(){
        EditText customerName = root.findViewById(R.id.account_customer_name_edit);
        EditText customerEmail = root.findViewById(R.id.account_customer_email_edit);
        customerName.setText(user.getDisplayName());
        customerEmail.setText(user.getEmail());
    }
}
