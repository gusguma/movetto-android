package com.movetto.view_models;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.movetto.dtos.UserDto;
import com.movetto.repositories.CustomerRepository;

public class CustomerViewModel extends UserViewModel {

    private CustomerRepository customerRepository;

    public CustomerViewModel(@NonNull Application application) {
        super(application);
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplication().getApplicationContext());
        customerRepository = new CustomerRepository(requestQueue);
    }

    public UserDto readCustomer(){
        return customerRepository.readCustomerByUid();
    }


    public UserDto saveUserCustomer(UserDto user){
        return null;
        //TODO
    }

    public UserDto updateUserCustomer(UserDto user){
        return null;
        //TODO
    }

    public UserDto deleteUserCustomer(UserDto user){
        return null;
        //TODO
    }






}
