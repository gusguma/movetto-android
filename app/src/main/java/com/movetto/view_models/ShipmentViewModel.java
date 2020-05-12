package com.movetto.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.movetto.dtos.NewsDto;
import com.movetto.dtos.ShipmentDto;
import com.movetto.repositories.ShipmentRepository;

import java.util.List;

public class ShipmentViewModel extends AndroidViewModel {

    private ShipmentRepository shipmentRepository;
    private MutableLiveData<List<ShipmentDto>> shipments;

    public ShipmentViewModel(@NonNull Application application) {
        super(application);
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplication().getApplicationContext());
        shipmentRepository = new ShipmentRepository(requestQueue);
        shipments = new MutableLiveData<>();
    }

    public MutableLiveData<List<ShipmentDto>> readShipments() {
        shipments = shipmentRepository.readShipments();
        return shipments;
    }

}
