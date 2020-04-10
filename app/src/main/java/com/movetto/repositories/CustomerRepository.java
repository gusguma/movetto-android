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

public class CustomerRepository extends UserRepository {

    private static final String BASE_CUSTOMERS_URL = BASE_USERS_URL + UrlHandler.CUSTOMERS_URL;

    public CustomerRepository(RequestQueue requestQueue) {
        super(requestQueue);
    }

    public void readCustomer(final MutableLiveData<UserDto> userDtoMutableLiveData){
        userDto = null;
        String uri = BASE_CUSTOMERS_URL + user.getUid();
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

    public void saveCustomer(UserDto userInputDto) throws Exception {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, BASE_CUSTOMERS_URL, customerRequest(userInputDto),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                userDto = mapper.readValue(response.toString(),UserDto.class);
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

    public void updateCustomer(final MutableLiveData<UserDto> userDtoMutableLiveData){
        //TODO
    }

    public void deleteCustomer(final MutableLiveData<UserDto> userDtoMutableLiveData){
        //TODO
    }

    private JSONObject customerRequest (UserDto customerDto) throws Exception {
        String json = mapper.writeValueAsString(customerDto);
        System.out.println(json);
        return new JSONObject(json);
    }
}
