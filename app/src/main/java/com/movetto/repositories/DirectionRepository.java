package com.movetto.repositories;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movetto.dtos.DirectionDto;
import com.movetto.handler.UrlHandler;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

public class DirectionRepository {

    private static final String BASE_DIRECTIONS_URL = UrlHandler.API_URL + UrlHandler.DIRECTIONS_URL;

    protected DirectionDto directionDto;
    protected RequestQueue requestQueue;
    protected ObjectMapper mapper;

    public DirectionRepository(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        this.mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public void readDirection(final MutableLiveData<DirectionDto> directionDtoMutableLiveData){
        //TODO
    }

    public void saveDirection(DirectionDto directionInputDto) throws Exception {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, BASE_DIRECTIONS_URL, directionRequest(directionInputDto),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            directionDto = mapper.readValue(response.toString(), DirectionDto.class);
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
    }

    public void updateDirection(){
        //TODO
    }

    public void deleteDirection(){
        //TODO
    }

    private JSONObject directionRequest (DirectionDto directionDto) throws Exception {
        String json = mapper.writeValueAsString(directionDto);
        return new JSONObject(json);
    }
}