package com.movetto.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.movetto.dtos.UserDto;
import com.movetto.repositories.PartnerRepository;

public class PartnerViewModel extends UserViewModel {

    private PartnerRepository partnerRepository;

    public PartnerViewModel(@NonNull Application application) {
        super(application);
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplication().getApplicationContext());
        partnerRepository = new PartnerRepository(requestQueue);
    }

    public MutableLiveData<UserDto> readPartner(){
        partnerRepository.readPartner(userDto);
        return userDto;
    }

    public MutableLiveData<UserDto> savePartner(){
        partnerRepository.savePartner(userDto);
        return userDto;
    }

    public MutableLiveData<UserDto> updatePartner(){
        partnerRepository.updatePartner(userDto);
        return userDto;
    }

    public MutableLiveData<UserDto> deletePartner(){
        partnerRepository.deletePartner(userDto);
        return userDto;
    }
}
