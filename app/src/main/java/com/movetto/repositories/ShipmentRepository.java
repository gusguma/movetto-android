package com.movetto.repositories;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movetto.dtos.NewsDto;
import com.movetto.dtos.ShipmentDto;
import com.movetto.handler.UrlHandler;

import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

public class ShipmentRepository {

    private static final String BASE_SHIPMENTS_URL = UrlHandler.API_URL + UrlHandler.SHIPMENTS_URL;

    private RequestQueue requestQueue;
    private MutableLiveData<List<ShipmentDto>> shipments;
    private ObjectMapper mapper;

    public ShipmentRepository(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public MutableLiveData<List<ShipmentDto>> readShipments() {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, BASE_SHIPMENTS_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<ShipmentDto> shipmentDtoList = mapper
                                    .readValue(response.toString()
                                            ,new TypeReference<List<ShipmentDto>>(){});
                            shipments.setValue(shipmentDtoList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return shipments;
    }
}
