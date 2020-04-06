package com.movetto.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseUser;
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

    public UserDto readPartner(){
        return partnerRepository.readPartnerByUid();
    }

    public UserDto saveUserPartner(UserDto user){
        return new UserDto();
    }

    private FirebaseUser getFirebaseUser(){
        return partnerRepository.getUser();
    }
}
