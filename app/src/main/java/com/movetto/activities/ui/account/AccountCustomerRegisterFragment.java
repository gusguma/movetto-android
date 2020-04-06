package com.movetto.activities.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.movetto.R;
import com.movetto.dtos.UserDto;
import com.movetto.view_models.CustomerViewModel;

public class AccountCustomerRegisterFragment extends Fragment {

    private View root;
    private CustomerViewModel customerViewModel;

    private TextView displayNameTop;
    private TextView emailTop;
    private EditText displayNameForm;
    private EditText emailForm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        root = inflater.inflate(R.layout.fragment_account_customer_register, container, false);
        displayNameTop = root.findViewById(R.id.account_customer_reg_name);
        emailTop = root.findViewById(R.id.account_customer_reg_email);
        displayNameForm = root.findViewById(R.id.account_customer_reg_name_edit);
        emailForm = root.findViewById(R.id.account_customer_reg_email_edit);
        setUserData();
        return root;
    }

    private void setUserData() {
        customerViewModel.getUserData().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                displayNameTop.setText(userDto.getDisplayName());
                emailTop.setText(userDto.getEmail());
                displayNameForm.setText(userDto.getDisplayName());
                emailForm.setText(userDto.getEmail());
            }
        });
    }

    private void setCustomerData(){
        //TODO
    }

    private void setButtonSave(final UserDto userDto){
        //TODO
        Button buttonSave = root.findViewById(R.id.account_customer_reg_save_button);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerViewModel.saveUserCustomer(userDto);
            }
        });
    }
}
