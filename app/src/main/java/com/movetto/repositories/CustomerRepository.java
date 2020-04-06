package com.movetto.repositories;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.movetto.dtos.UserDto;

import org.json.JSONObject;

import java.io.IOException;

public class CustomerRepository extends UserRepository {

    private UserDto userDto;

    public CustomerRepository(RequestQueue requestQueue) {
        super(requestQueue);
    }

    public UserDto readCustomerByUid(){
        String uri = UserRepository.BASE_CUSTOMERS_URL + getUser().getUid();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            userDto = CustomerRepository
                                    .super.getMapper()
                                    .readValue(response.toString(),UserDto.class);
                            System.out.println("Cliente Encontrado");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Cliente No Encontrado");
                    }
                });
        super.getRequestQueue().add(request);
        return userDto;
    }
}
