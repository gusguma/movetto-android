package com.movetto.repositories;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movetto.dtos.WalletDto;
import com.movetto.handler.UrlHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WalletRepository {

    private static final String BASE_WALLET_URL = UrlHandler.API_URL + UrlHandler.WALLET_URL;

    private RequestQueue requestQueue;
    private MutableLiveData<WalletDto> walletMutable;
    private ObjectMapper mapper;

    public WalletRepository(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        this.walletMutable = new MutableLiveData<>();
        this.mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public MutableLiveData<WalletDto> readWallet(String uid) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, BASE_WALLET_URL + uid, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            WalletDto walletDto = mapper
                                    .readValue(response.toString()
                                            ,WalletDto.class);
                            walletMutable.setValue(walletDto);
                        } catch (IOException e) {
                            walletMutable.setValue(null);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        walletMutable.setValue(null);
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return walletMutable;
    }

    public MutableLiveData<WalletDto> saveWallet(WalletDto walletDto)
            throws JsonProcessingException, JSONException {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, BASE_WALLET_URL, walletRequest(walletDto),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            WalletDto walletDto = mapper
                                    .readValue(response.toString()
                                            ,WalletDto.class);
                            walletMutable.setValue(walletDto);
                        } catch (IOException e) {
                            walletMutable.setValue(null);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        walletMutable.setValue(null);
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return walletMutable;
    }

    public MutableLiveData<WalletDto> updateWallet(WalletDto walletDto)
            throws JsonProcessingException, JSONException {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT, BASE_WALLET_URL, walletRequest(walletDto),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            WalletDto walletDto = mapper
                                    .readValue(response.toString()
                                            ,WalletDto.class);
                            walletMutable.setValue(walletDto);
                        } catch (IOException e) {
                            walletMutable.setValue(null);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        walletMutable.setValue(null);
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return walletMutable;
    }

    private JSONObject walletRequest(WalletDto walletDto) throws JsonProcessingException, JSONException {
        String json = mapper.writeValueAsString(walletDto);
        System.out.println(json);
        return new JSONObject(json);
    }


}
