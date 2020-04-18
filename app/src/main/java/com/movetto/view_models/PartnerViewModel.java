package com.movetto.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.movetto.dtos.UserDto;
import com.movetto.repositories.PartnerRepository;

public class PartnerViewModel extends UserViewModel {

    private MutableLiveData<UserDto> partner;
    private MutableLiveData<Boolean> response;
    private PartnerRepository partnerRepository;

    public PartnerViewModel(@NonNull Application application) {
        super(application);
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplication().getApplicationContext());
        partnerRepository = new PartnerRepository(requestQueue);
        partner = new MutableLiveData<>();
        response = new MutableLiveData<>();
    }

    public MutableLiveData<UserDto> readPartner(){
        partner = partnerRepository.readPartner();
        return partner;
    }

    public MutableLiveData<Boolean> savePartner(UserDto userOutputDto) throws Exception {
        return partnerRepository.savePartner(userOutputDto);
    }

    public MutableLiveData<Boolean> updatePartner(UserDto userOutputDto) throws Exception {
        return partnerRepository.updatePartner(userOutputDto);
    }

    public MutableLiveData<UserDto> deletePartner(){
        return null;
        //TODO
    }
}
