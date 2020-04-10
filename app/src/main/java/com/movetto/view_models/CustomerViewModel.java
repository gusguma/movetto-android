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

    public MutableLiveData<UserDto> readCustomer(){
        customerRepository.readCustomer(userDto);
        return userDto;
    }

    public void saveCustomer(UserDto userOutputDto) throws Exception {
        customerRepository.saveCustomer(userOutputDto);
    }

    public MutableLiveData<UserDto> updateCustomer(){
        customerRepository.updateCustomer(userDto);
        return userDto;
    }

    public MutableLiveData<UserDto> deleteCustomer(){
        customerRepository.deleteCustomer(userDto);
        return userDto;
    }










}
