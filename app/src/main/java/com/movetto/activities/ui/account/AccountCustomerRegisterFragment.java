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
import com.movetto.dtos.CustomerDto;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.UserDto;
import com.movetto.view_models.CustomerViewModel;

import org.json.JSONException;

import java.util.HashSet;
import java.util.Set;

public class AccountCustomerRegisterFragment extends Fragment {

    private View root;
    private CustomerViewModel customerViewModel;

    private TextView displayNameTop;
    private TextView emailTop;
    private EditText displayNameForm;
    private EditText emailForm;
    private EditText phone;
    private EditText street;
    private EditText postalCode;
    private EditText city;
    private EditText state;
    private EditText country;
    private EditText customerId;
    private UserDto userOutputDto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        root = inflater.inflate(R.layout.fragment_account_customer_register, container, false);
        displayNameTop = root.findViewById(R.id.account_customer_reg_name);
        emailTop = root.findViewById(R.id.account_customer_reg_email);
        displayNameForm = root.findViewById(R.id.account_customer_reg_name_edit);
        emailForm = root.findViewById(R.id.account_customer_reg_email_edit);
        phone = root.findViewById(R.id.account_customer_reg_phone_edit);
        street = root.findViewById(R.id.account_customer_reg_street_edit);
        postalCode = root.findViewById(R.id.account_customer_reg_cp_edit);
        city = root.findViewById(R.id.account_customer_reg_city_edit);
        state = root.findViewById(R.id.account_customer_reg_state_edit);
        country = root.findViewById(R.id.account_customer_reg_country_edit);
        customerId = root.findViewById(R.id.account_customer_reg_id_edit);
        setUserData();
        return root;
    }

    private void setUserData() {
        customerViewModel.readUser().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
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
        userOutputDto = customerViewModel.readUser().getValue();
        Set<DirectionDto> directions = new HashSet<>();
        if (userOutputDto != null ){
            userOutputDto.setDisplayName(displayNameForm.getText().toString());
            userOutputDto.setPhone(phone.getText().toString());
            userOutputDto.setDirections(new HashSet<DirectionDto>());
            userOutputDto.getDirections().add(setDefaultDirection());
            userOutputDto.setCustomer(new CustomerDto());
            userOutputDto.getCustomer().setCustomerId(customerId.getText().toString());
        }
    }

    private DirectionDto setDefaultDirection (){
        DirectionDto direction = new DirectionDto();
        direction.setStreet(street.getText().toString());
        direction.setPostalCode(postalCode.getText().toString());
        direction.setCity(city.getText().toString());
        direction.setState(state.getText().toString());
        direction.setCountry(country.getText().toString());
        return direction;

    }

    private void setButtonSave(final UserDto userDto){
        //TODO
        Button buttonSave = root.findViewById(R.id.account_customer_reg_save_button);
        setCustomerData();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    customerViewModel.saveCustomer(userOutputDto);
                    //TODO make fragment OK
                } catch (JSONException e) {
                    //TODO make fragment ERROR
                    e.printStackTrace();
                }
            }
        });
    }

    private void getRegisterOkFragment(){
        //TODO make fragment OK
    }

    private void getRegisterErrorFragment(){
        //TODO make fragment OK
    }


}
