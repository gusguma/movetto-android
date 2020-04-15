package com.movetto.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.movetto.dtos.UserDto;
import com.movetto.repositories.CustomerRepository;

public class CustomerViewModel extends UserViewModel {

    private MutableLiveData<UserDto> customer;
    private MutableLiveData<Boolean> response;
    private CustomerRepository customerRepository;

    public CustomerViewModel(@NonNull Application application) {
        super(application);
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplication().getApplicationContext());
        customerRepository = new CustomerRepository(requestQueue);
        customer = new MutableLiveData<>();
        response = new MutableLiveData<>();
    }

    public MutableLiveData<UserDto> readCustomer(){
        customer = customerRepository.readCustomer();
        return customer;
    }

    public MutableLiveData<Boolean> saveCustomer(UserDto userOutputDto) throws Exception {
        return customerRepository.saveCustomer(userOutputDto);
    }

    public MutableLiveData<Boolean> updateCustomer(UserDto userOutputDto) throws Exception {
        return customerRepository.updateCustomer(userOutputDto);
    }

    public MutableLiveData<UserDto> deleteCustomer(){
        customerRepository.deleteCustomer(userDto);
        return userDto;
    }










}
