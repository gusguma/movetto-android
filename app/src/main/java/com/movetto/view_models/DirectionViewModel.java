package com.movetto.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.VehicleDto;
import com.movetto.repositories.DirectionRepository;

public class DirectionViewModel extends AndroidViewModel {

    private DirectionRepository directionRepository;

    public DirectionViewModel(@NonNull Application application) {
        super(application);
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplication().getApplicationContext());
        directionRepository = new DirectionRepository(requestQueue);
    }

    public MutableLiveData<DirectionDto> readDirection(int id) throws Exception {
        return directionRepository.readDirection(id);
    }

    public MutableLiveData<Boolean> saveDirection(DirectionDto direction) throws Exception {
        return directionRepository.saveDirection(direction);
    }

    public MutableLiveData<Boolean> updateDirection(DirectionDto direction) throws Exception{
        return directionRepository.updateDirection(direction);
    }

    public MutableLiveData<Boolean> deleteDirection(int id) throws Exception{
        return directionRepository.deleteDirection(id);
    }
}
