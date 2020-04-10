package com.movetto.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.movetto.dtos.DirectionDto;
import com.movetto.repositories.DirectionRepository;

public class DirectionViewModel extends AndroidViewModel {

    protected MutableLiveData<DirectionDto> directionDto;
    private DirectionRepository directionRepository;

    public DirectionViewModel(@NonNull Application application) {
        super(application);
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplication().getApplicationContext());
        directionRepository = new DirectionRepository(requestQueue);
    }

    public MutableLiveData<DirectionDto> readDirection(){
        directionRepository.readDirection(directionDto);
        return directionDto;
    }

    public void saveDirection(DirectionDto directionOutputDto) throws Exception {
        directionRepository.saveDirection(directionOutputDto);
    }


}
