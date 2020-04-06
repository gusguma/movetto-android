package com.movetto.repositories;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.movetto.dtos.UserDto;

import org.json.JSONObject;

import java.io.IOException;

public class PartnerRepository extends UserRepository {

    private UserDto userDto;

    public PartnerRepository(RequestQueue requestQueue) {
        super(requestQueue);
    }

    public UserDto readPartnerByUid(){
        String uri = BASE_PARTNERS_URL + getUser().getUid();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            userDto = PartnerRepository
                                    .super.getMapper()
                                    .readValue(response.toString(),UserDto.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Usuario Encontrado");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Socio No Encontrado");
                    }
                });
        super.getRequestQueue().add(request);
        return this.userDto;
    }
}
