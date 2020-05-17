package com.movetto.repositories;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movetto.dtos.ShipmentDto;
import com.movetto.handler.UrlHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShipmentRepository {

    private static final String BASE_SHIPMENTS_URL = UrlHandler.API_URL + UrlHandler.SHIPMENTS_URL;

    private RequestQueue requestQueue;
    private MutableLiveData<List<ShipmentDto>> shipments;
    private MutableLiveData<ShipmentDto> shipmentMutable;
    private MutableLiveData<Boolean> isResponseOk;
    private ObjectMapper mapper;

    public ShipmentRepository(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        this.shipments = new MutableLiveData<>();
        this.isResponseOk = new MutableLiveData<>();
        this.shipmentMutable = new MutableLiveData<>();
        this.mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public MutableLiveData<List<ShipmentDto>> readShipments() {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, BASE_SHIPMENTS_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<ShipmentDto> shipmentDtoList = mapper
                                    .readValue(response.toString()
                                            ,new TypeReference<List<ShipmentDto>>(){});
                            shipments.setValue(shipmentDtoList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        shipments.setValue(new ArrayList<>());
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return shipments;
    }

    public MutableLiveData<List<ShipmentDto>> readShipmentsByUid(String uid) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, BASE_SHIPMENTS_URL + uid, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<ShipmentDto> shipmentDtoList = mapper
                                    .readValue(response.toString()
                                            ,new TypeReference<List<ShipmentDto>>(){});
                            shipments.setValue(shipmentDtoList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        shipments.setValue(new ArrayList<>());
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return shipments;
    }

    public MutableLiveData<ShipmentDto> readShipmentById(int id) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, BASE_SHIPMENTS_URL + "id/" + id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ShipmentDto shipmentDto = mapper.readValue(response.toString(),ShipmentDto.class);
                            shipmentMutable.setValue(shipmentDto);
                        } catch (IOException e) {
                            shipmentMutable.setValue(null);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        shipmentMutable.setValue(null);
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return shipmentMutable;
    }

    public MutableLiveData<ShipmentDto> saveShipment(ShipmentDto shipment) throws JsonProcessingException, JSONException {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, BASE_SHIPMENTS_URL, shipmentRequest(shipment),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ShipmentDto shipmentDto = mapper.readValue(response.toString(),ShipmentDto.class);
                            shipmentMutable.setValue(shipmentDto);
                        } catch (IOException e) {
                            e.printStackTrace();
                            shipmentMutable.setValue(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        shipmentMutable.setValue(null);
                    }
                });
        requestQueue.add(request);
        return shipmentMutable;
    }

    public MutableLiveData<ShipmentDto> updateShipment(ShipmentDto shipment) throws JsonProcessingException, JSONException {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT, BASE_SHIPMENTS_URL, shipmentRequest(shipment),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ShipmentDto shipmentDto = mapper.readValue(response.toString(),ShipmentDto.class);
                            shipmentMutable.setValue(shipmentDto);
                        } catch (IOException e) {
                            e.printStackTrace();
                            shipmentMutable.setValue(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        shipmentMutable.setValue(null);
                    }
                });
        requestQueue.add(request);
        return shipmentMutable;
    }

    public MutableLiveData<ShipmentDto> deleteShipment(ShipmentDto shipment)
            throws JsonProcessingException, JSONException {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE, BASE_SHIPMENTS_URL + "id/"
                + shipment.getId(), shipmentRequest(shipment),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ShipmentDto shipmentDto = mapper
                                    .readValue(response.toString(),ShipmentDto.class);
                            shipmentMutable.setValue(shipmentDto);
                        } catch (IOException e) {
                            e.printStackTrace();
                            shipmentMutable.setValue(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        shipmentMutable.setValue(null);
                    }
                });
        requestQueue.add(request);
        return shipmentMutable;
    }

    private JSONObject shipmentRequest(ShipmentDto shipmentDto) throws JsonProcessingException, JSONException {
        String json = mapper.writeValueAsString(shipmentDto);
        System.out.println(json);
        return new JSONObject(json);
    }
}
