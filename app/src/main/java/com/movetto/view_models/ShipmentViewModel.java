package com.movetto.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.movetto.dtos.NewsDto;
import com.movetto.dtos.ShipmentDto;
import com.movetto.repositories.ShipmentRepository;

import org.json.JSONException;

import java.util.List;

public class ShipmentViewModel extends AndroidViewModel {

    private ShipmentRepository shipmentRepository;

    public ShipmentViewModel(@NonNull Application application) {
        super(application);
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplication().getApplicationContext());
        shipmentRepository = new ShipmentRepository(requestQueue);
    }

    public MutableLiveData<List<ShipmentDto>> readShipments() {
        return shipmentRepository.readShipments();
    }

    public MutableLiveData<ShipmentDto> readShipmentById(int id){
        return shipmentRepository.readShipmentById(id);
    }

    public MutableLiveData<List<ShipmentDto>> readShipmentsByUid(String uid){
        return shipmentRepository.readShipmentsByUid(uid);
    }

    public MutableLiveData<List<ShipmentDto>> readShipmentsByPartnerUid(String uid) {
        return shipmentRepository.readShipmentsByPartnerUid(uid);
    }

    public MutableLiveData<List<ShipmentDto>> readShipmentsAvailable(String uid) {
        return shipmentRepository.readShipmentsAvailable(uid);
    }

    public MutableLiveData<List<ShipmentDto>> readShipmentsPending(String uid) {
        return shipmentRepository.readShipmentsPending(uid);
    }

    public MutableLiveData<List<ShipmentDto>> readShipmentsFinished(String uid) {
        return shipmentRepository.readShipmentsFinished(uid);
    }

    public MutableLiveData<ShipmentDto> saveShipment(ShipmentDto shipment)
            throws JsonProcessingException, JSONException {
        return shipmentRepository.saveShipment(shipment);
    }

    public MutableLiveData<ShipmentDto>  updateShipment(ShipmentDto shipment)
            throws JsonProcessingException, JSONException {
        return shipmentRepository.updateShipment(shipment);
    }

    public MutableLiveData<ShipmentDto> deleteShipment(ShipmentDto shipment)
            throws JsonProcessingException, JSONException {
        return shipmentRepository.deleteShipment(shipment);
    }
}
