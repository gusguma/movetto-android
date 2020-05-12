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
import com.movetto.repositories.VehicleRepository;

public class VehicleViewModel extends AndroidViewModel {

    private VehicleRepository vehicleRepository;

    public VehicleViewModel(@NonNull Application application) {
        super(application);
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplication().getApplicationContext());
        vehicleRepository = new VehicleRepository(requestQueue);
    }

    public MutableLiveData<VehicleDto> readVehicle(int id) throws Exception {
        return vehicleRepository.readVehicle(id);
    }

    public MutableLiveData<Boolean> saveVehicle(VehicleDto vehicle) throws Exception {
        return vehicleRepository.saveVehicle(vehicle);
    }

    public MutableLiveData<Boolean> updateVehicle(VehicleDto vehicle) throws Exception{
        return vehicleRepository.updateVehicle(vehicle);
    }

    public MutableLiveData<Boolean> deleteVehicle(int id) throws Exception{
        return vehicleRepository.deleteVehicle(id);
    }
}
