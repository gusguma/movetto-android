package com.movetto.repositories;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.movetto.dtos.UserDto;
import com.movetto.handler.UrlHandler;

import org.json.JSONObject;

import java.io.IOException;

public class PartnerRepository extends UserRepository {

    private static final String BASE_PARTNERS_URL = BASE_USERS_URL + UrlHandler.PARTNERS_URL;

    public PartnerRepository(RequestQueue requestQueue) {
        super(requestQueue);
    }

    public void readPartner(final MutableLiveData<UserDto> userDtoMutableLiveData){
        userDto = null;
        String uri = BASE_PARTNERS_URL + user.getUid();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            userDto = mapper.readValue(response.toString(),UserDto.class);
                            userDtoMutableLiveData.setValue(userDto);
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

    public void savePartner(final MutableLiveData<UserDto> userDtoMutableLiveData){
        //TODO
    }

    public void updatePartner(final MutableLiveData<UserDto> userDtoMutableLiveData){
        //TODO
    }

    public void deletePartner(final MutableLiveData<UserDto> userDtoMutableLiveData){
        //TODO
    }


}
