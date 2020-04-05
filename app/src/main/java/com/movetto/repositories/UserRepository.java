package com.movetto.repositories;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.movetto.handler.UrlHandler;

import org.json.JSONObject;

public class UserRepository {

    private static final String BASE_USERS_URL = UrlHandler.API_URL + UrlHandler.USERS_URL;
    private static final String BASE_CUSTOMERS_URL = BASE_USERS_URL + UrlHandler.CUSTOMERS_URL;
    private static final String BASE_PARTNERS_URL = BASE_USERS_URL + UrlHandler.PARTNERS_URL;

    private FirebaseUser user;
    private RequestQueue requestQueue;
    private boolean exist;

    public UserRepository(){
        //Empty for Framework
    }

    public UserRepository(RequestQueue requestQueue){
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.requestQueue = requestQueue;
    }

    public boolean userCustomerExist(){
        String uri = BASE_CUSTOMERS_URL + user.getUid();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        exist = response.has("uid");
                        System.out.println("Usuario Encontrado");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Cliente No Encontrado");
                    }
                });
        requestQueue.add(request);
        return this.exist;
    }

    public boolean userPartnerExist(){
        String uri = BASE_PARTNERS_URL + user.getUid();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        exist = response.has("uid");
                        System.out.println("Usuario Encontrado");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Socio No Encontrado");
                    }
                });
        requestQueue.add(request);
        return this.exist;
    }
}
