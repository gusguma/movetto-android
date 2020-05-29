package com.movetto.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.WalletDto;
import com.movetto.repositories.WalletRepository;

import org.json.JSONException;

import java.util.List;

public class WalletViewModel extends AndroidViewModel {

    private WalletRepository walletRepository;

    public WalletViewModel(@NonNull Application application) {
        super(application);
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplication().getApplicationContext());
        walletRepository = new WalletRepository(requestQueue);
    }

    public MutableLiveData<WalletDto> readWallet(String uid){
        return walletRepository.readWallet(uid);
    }

    public MutableLiveData<WalletDto> readWalletById(int id){
        return walletRepository.readWalletById(id);
    }

    public MutableLiveData<WalletDto> saveWallet(WalletDto wallet)
            throws JsonProcessingException, JSONException {
        return walletRepository.saveWallet(wallet);
    }

    public MutableLiveData<WalletDto>  updateWallet(WalletDto wallet)
            throws JsonProcessingException, JSONException {
        return walletRepository.updateWallet(wallet);
    }

}