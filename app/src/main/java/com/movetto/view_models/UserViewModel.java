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
    MutableLiveData<UserDto> userDto;

    public UserViewModel(@NonNull Application application) {
        super(application);
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplication().getApplicationContext());
        userRepository = new CustomerRepository(requestQueue);
        userDto = new MutableLiveData<>();
    }

    public MutableLiveData<UserDto> readUser(){
        userRepository.readUser(userDto);
        return userDto;
    }

    public MutableLiveData<UserDto> updateUser(){
        userRepository.updateUser(userDto);
        return userDto;
    }

    public MutableLiveData<UserDto> deleteUser(){
        userRepository.deleteUser(userDto);
        return userDto;
    }

    public MutableLiveData<UserDto> getUserDto() {
        return userDto;
    }
}
