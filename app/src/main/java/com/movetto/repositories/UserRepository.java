package com.movetto.repositories;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.movetto.dtos.UserDto;
import com.movetto.handler.UrlHandler;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Future;

public class UserRepository {

    public static final String BASE_USERS_URL = UrlHandler.API_URL + UrlHandler.USERS_URL;
    public static final String BASE_CUSTOMERS_URL = BASE_USERS_URL + UrlHandler.CUSTOMERS_URL;
    public static final String BASE_PARTNERS_URL = BASE_USERS_URL + UrlHandler.PARTNERS_URL;

    private FirebaseUser user;
    private RequestQueue requestQueue;
    private UserDto userDto;
    private ObjectMapper mapper;

    public UserRepository(RequestQueue requestQueue){
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.requestQueue = requestQueue;
        this.mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public void readUser(final MutableLiveData<UserDto> userDtoMutableLiveData){
        String uri = BASE_USERS_URL + user.getUid();
        System.out.println(uri);
        JsonObjectRequest request = new JsonObjectRequest (
                Request.Method.GET, uri, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            userDto = mapper.readValue(response.toString(), UserDto.class);
                            userDtoMutableLiveData.setValue(userDto);
                            System.out.println("Usuario Encontrado");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Usuario No Encontrado");
                    }
                });
        requestQueue.add(request);
    }

    public FirebaseUser getUser() {
        return user;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public UserDto getUserDto(){
        return userDto;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
