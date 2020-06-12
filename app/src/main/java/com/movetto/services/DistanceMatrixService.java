package com.movetto.services;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movetto.BuildConfig;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.TravelDto;
import com.movetto.handler.UrlHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class DistanceMatrixService {
    private static final String BASE_MAPS_API_URL = UrlHandler.MAPS_API_URL;
    private static final String API_KEY = BuildConfig.MAPS_API;
    private static final String ORIGIN = "&origins=";
    private static final String DESTINATION = "&destinations=";
    private static final String KEY = "&key=";

    private RequestQueue requestQueue;
    private MutableLiveData<TravelDto> travel;
    private ObjectMapper mapper;

    public DistanceMatrixService(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        this.travel = new MutableLiveData<>();
        this.mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public MutableLiveData<TravelDto> setTravelData(TravelDto travelDto) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,setUriRequest(travelDto), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            TravelDto travelData = setResponse(response,travelDto);
                            travel.setValue(travelData);
                        } catch (JSONException e) {
                            travel.setValue(null);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        travel.setValue(null);
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return travel;
    }

    private String setUriRequest(TravelDto travel) {
        DirectionDto origin = travel.getStartDirection();
        DirectionDto destination = travel.getEndDirection();
        String originDirection =
                origin.getStreet() + "," + origin.getPostalCode() + "+" +
                        origin.getCity();
        String destinationDirection =
                destination.getStreet() + "," + destination.getPostalCode() + "+" +
                        destination.getCity();
        System.out.println(BASE_MAPS_API_URL + ORIGIN + originDirection
                + DESTINATION + destinationDirection + KEY + API_KEY);
        return BASE_MAPS_API_URL + ORIGIN + originDirection
                + DESTINATION + destinationDirection + KEY + API_KEY;
    }

    private TravelDto setResponse(JSONObject response, TravelDto travel) throws JSONException {
        JSONObject distance = response.getJSONArray("rows")
                .getJSONObject(0)
                .getJSONArray("elements")
                .getJSONObject(0)
                .getJSONObject("distance");
        JSONObject duration = response.getJSONArray("rows")
                .getJSONObject(0)
                .getJSONArray("elements")
                .getJSONObject(0)
                .getJSONObject("duration");
        travel.setDistance(distance.getDouble("value") / 1000);
        travel.setEnd(travel.getStart().plusSeconds(duration.getLong("value")));
        travel.setTravelPrice();
        System.out.println(travel.toString());
        return travel;
    }
}
