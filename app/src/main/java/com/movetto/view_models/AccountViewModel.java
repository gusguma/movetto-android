package com.movetto.view_models;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movetto.repositories.UserRepository;

public class AccountViewModel extends AndroidViewModel {

    private RequestQueue requestQueue;
    private ObjectMapper mapper;
    private MutableLiveData<String> mText;
    private Application application = new Application();
    private UserRepository userRepository;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        requestQueue = Volley.newRequestQueue(getApplication().getApplicationContext());
        mText = new MutableLiveData<>();
        mText.setValue("This is wallet fragment");
        userRepository = new UserRepository(requestQueue);
    }
    public LiveData<String> getText() {
        return mText;
    }

    public boolean checkUserCustomerExist(){
        return userRepository.userCustomerExist();
    }

    public boolean checkUserPartnerExist(){
        return userRepository.userPartnerExist();
    }
}
