package com.movetto.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.movetto.dtos.UserDto;
import com.movetto.repositories.CustomerRepository;
import com.movetto.repositories.UserRepository;

import org.json.JSONException;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    protected MutableLiveData<UserDto> userDto;

    public UserViewModel(@NonNull Application application) {
        super(application);
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplication().getApplicationContext());
        userRepository = new CustomerRepository(requestQueue);
        userDto = new MutableLiveData<UserDto>();
    }

    public MutableLiveData<UserDto> readUser(){
        return userRepository.readUser();
    }

    public MutableLiveData<UserDto> readUserByEmail(UserDto user) {
        return userRepository.readUserByEmail(user);
    }

    public MutableLiveData<UserDto> saveUser(UserDto userDto) throws Exception {
        return userRepository.saveUser();
    }

    public MutableLiveData<UserDto> saveUserByEmail(UserDto user) throws Exception {
        return userRepository.saveUserByEmail(user);
    }

    public MutableLiveData<UserDto> updateUser(UserDto user) throws Exception {
        return userRepository.updateUser(user);
    }

    public MutableLiveData<UserDto> deleteUser(){
        userRepository.deleteUser(userDto);
        return userDto;
    }

    public MutableLiveData<UserDto> getUserDto() {
        return userDto;
    }
}
