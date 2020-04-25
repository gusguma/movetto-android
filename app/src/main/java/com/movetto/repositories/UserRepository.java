package com.movetto.repositories;

import android.content.res.Resources;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.UserMinimumDto;
import com.movetto.handler.UrlHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UserRepository {

    protected static final String BASE_USERS_URL = UrlHandler.API_URL + UrlHandler.USERS_URL;

    protected FirebaseUser user;
    protected RequestQueue requestQueue;
    private UserDto userDto;
    protected ObjectMapper mapper;

    public UserRepository(RequestQueue requestQueue){
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.requestQueue = requestQueue;
        this.mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public void readUser(final MutableLiveData<UserDto> userDtoMutableLiveData){
        userDto = null;
        String uri = BASE_USERS_URL + user.getUid();
        JsonObjectRequest request = new JsonObjectRequest (
                Request.Method.GET, uri, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            userDto = mapper.readValue(response.toString(), UserDto.class);
                            userDtoMutableLiveData.setValue(userDto);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            saveUser(userDtoMutableLiveData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        requestQueue.add(request);
    }

    private void saveUser(final MutableLiveData<UserDto> userDtoMutableLiveData) throws Exception {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    BASE_USERS_URL, userRequest(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                userDto = mapper.readValue(response.toString(), UserDto.class);
                                userDtoMutableLiveData.setValue(userDto);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }},
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
            requestQueue.add(request);
    }

    public void updateUser(final MutableLiveData<UserDto> userDtoMutableLiveData){

    }

    public void deleteUser(final MutableLiveData<UserDto> userDtoMutableLiveData){

    }

    private JSONObject userRequest () throws JSONException, JsonProcessingException {
        UserMinimumDto userMinimumDto = new UserMinimumDto(
                user.getDisplayName(),user.getEmail(),user.getUid());
        String json = mapper.writeValueAsString(userMinimumDto);
        return new JSONObject(json);
    }







    public FirebaseUser getUser() {
        return user;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
