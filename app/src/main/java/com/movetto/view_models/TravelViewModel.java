package com.movetto.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.TravelDto;
import com.movetto.repositories.TravelRepository;
import com.movetto.services.DistanceMatrixService;

import org.json.JSONException;

import java.util.List;

public class TravelViewModel extends AndroidViewModel {

    private TravelRepository travelRepository;
    private DistanceMatrixService distanceMatrixService;

    public TravelViewModel(@NonNull Application application) {
        super(application);
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplication().getApplicationContext());
        travelRepository = new TravelRepository(requestQueue);
        distanceMatrixService = new DistanceMatrixService(requestQueue);
    }

    public MutableLiveData<List<TravelDto>> readTravels() {
        return travelRepository.readTravels();
    }

    public MutableLiveData<TravelDto> readTravelById(int id){
        return travelRepository.readTravelById(id);
    }

    public MutableLiveData<List<TravelDto>> readTravelsByUid(String uid){
        return travelRepository.readTravelsByUid(uid);
    }

    public MutableLiveData<List<TravelDto>> readTravelsByPartnerUid(String uid) {
        return travelRepository.readTravelsByPartnerUid(uid);
    }

    public MutableLiveData<List<TravelDto>> readTravelsAvailable(String uid) {
        return travelRepository.readTravelsAvailable(uid);
    }

    public MutableLiveData<List<TravelDto>> readTravelsPending(String uid) {
        return travelRepository.readTravelsPending(uid);
    }

    public MutableLiveData<List<TravelDto>> readTravelsFinished(String uid) {
        return travelRepository.readTravelsFinished(uid);
    }

    public MutableLiveData<TravelDto> saveTravel(TravelDto travel)
            throws JsonProcessingException, JSONException {
        return travelRepository.saveTravel(travel);
    }

    public MutableLiveData<TravelDto>  updateTravel(TravelDto travel)
            throws JsonProcessingException, JSONException {
        return travelRepository.updateTravel(travel);
    }

    public MutableLiveData<TravelDto> deleteTravel(TravelDto travel)
            throws JsonProcessingException, JSONException {
        return travelRepository.deleteTravel(travel);
    }

    public MutableLiveData<TravelDto> getTravelDistance(TravelDto travel) {
        return distanceMatrixService.setTravelData(travel);
    }

}
