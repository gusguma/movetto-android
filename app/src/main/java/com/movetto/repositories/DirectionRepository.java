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
import com.movetto.handler.UrlHandler;

import org.json.JSONObject;

import java.io.IOException;

public class DirectionRepository {

    private static final String BASE_DIRECTIONS_URL = UrlHandler.API_URL + UrlHandler.DIRECTIONS_URL;

    private DirectionDto directionDto;
    private RequestQueue requestQueue;
    private ObjectMapper mapper;
    private FirebaseUser user;

    public DirectionRepository(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        this.mapper = new ObjectMapper();
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public void readDirection(final MutableLiveData<DirectionDto> directionDtoMutableLiveData){
        //TODO
    }

    public void saveDirection(DirectionDto directionInputDto) throws Exception {
        String uri = BASE_DIRECTIONS_URL + user.getUid();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, uri, directionRequest(directionInputDto),
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
        System.out.println(directionDto.toString());
        String json = mapper.writeValueAsString(directionDto);
        return new JSONObject(json);
    }
}