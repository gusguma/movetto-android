package com.movetto.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.movetto.dtos.UserDto;
import com.movetto.repositories.CustomerRepository;
import com.movetto.repositories.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private MutableLiveData<UserDto> userDto;

    public UserViewModel(@NonNull Application application) {
        super(application);
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplication().getApplicationContext());
        userRepository = new CustomerRepository(requestQueue);
        userDto = new MutableLiveData<>();
    }

    public MutableLiveData<UserDto> getUserData(){
        userRepository.readUser(userDto);
        return userDto;
    }




}
