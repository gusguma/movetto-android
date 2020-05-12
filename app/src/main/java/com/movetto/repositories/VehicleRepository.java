package com.movetto.repositories;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.VehicleDto;
import com.movetto.handler.UrlHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class VehicleRepository {

    private static final String BASE_VEHICLES_URL = UrlHandler.API_URL + UrlHandler.VEHICLES_URL;

    private MutableLiveData<Boolean> isResponseOk;
    private MutableLiveData<VehicleDto> vehicleDtoMutableLiveData;
    private VehicleDto vehicleDto;
    private RequestQueue requestQueue;
    private ObjectMapper mapper;
    private FirebaseUser user;

    public VehicleRepository(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        this.mapper = new ObjectMapper();
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        isResponseOk = new MutableLiveData<>();
    }

    public MutableLiveData<VehicleDto> readVehicle(int id){
        return null;
    }

    public MutableLiveData<Boolean> saveVehicle(VehicleDto vehicleInputDto) throws Exception {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, BASE_VEHICLES_URL, vehicleRequest(vehicleInputDto),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VehicleDto vehicleDto = mapper.readValue(response.toString(), VehicleDto.class);
                            if (vehicleDto != null){
                                isResponseOk.setValue(true);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            isResponseOk.setValue(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        isResponseOk.setValue(false);
                    }
                });
        requestQueue.add(request);
        return isResponseOk;
    }

    public MutableLiveData<Boolean> updateVehicle(VehicleDto vehicleInputDto) throws Exception {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT, BASE_VEHICLES_URL, vehicleRequest(vehicleInputDto),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VehicleDto vehicleDto = mapper.readValue(response.toString(), VehicleDto.class);
                            if (vehicleDto != null){
                                isResponseOk.setValue(true);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            isResponseOk.setValue(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        isResponseOk.setValue(false);
                    }
                });
        requestQueue.add(request);
        return isResponseOk;
    }

    public MutableLiveData<Boolean> deleteVehicle(int id){
        String uri = BASE_VEHICLES_URL + id;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(!response.toString().isEmpty()){
                            isResponseOk.setValue(true);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        isResponseOk.setValue(false);
                    }
                });
        requestQueue.add(request);
        return isResponseOk;
    }

    private JSONObject vehicleRequest (VehicleDto vehicleDto) throws Exception {
        System.out.println(vehicleDto.toString());
        String json = mapper.writeValueAsString(vehicleDto);
        return new JSONObject(json);
    }
}
