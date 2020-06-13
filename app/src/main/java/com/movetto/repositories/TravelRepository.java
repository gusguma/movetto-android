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
import com.movetto.dtos.TravelDto;
import com.movetto.handler.UrlHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TravelRepository {

    private static final String BASE_TRAVELS_URL = UrlHandler.API_URL + UrlHandler.TRAVELS_URL;
    private static final String TRAVEL_PARTNER_URL = BASE_TRAVELS_URL + UrlHandler.PARTNERS_URL;
    private static final String TRAVEL_AVAILABLE_URL = BASE_TRAVELS_URL + UrlHandler.AVAILABLE;
    private static final String TRAVEL_PENDING_URL = BASE_TRAVELS_URL + UrlHandler.PENDING;
    private static final String TRAVEL_FINISHED_URL = BASE_TRAVELS_URL + UrlHandler.FINISHED;

    private RequestQueue requestQueue;
    private MutableLiveData<List<TravelDto>> travels;
    private MutableLiveData<TravelDto> travelMutable;
    private MutableLiveData<Boolean> isResponseOk;
    private ObjectMapper mapper;

    public TravelRepository(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        this.travels = new MutableLiveData<>();
        this.isResponseOk = new MutableLiveData<>();
        this.travelMutable = new MutableLiveData<>();
        this.mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public MutableLiveData<List<TravelDto>> readTravels() {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, BASE_TRAVELS_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<TravelDto> travelDtoList = mapper
                                    .readValue(response.toString()
                                            ,new TypeReference<List<TravelDto>>(){});
                            travels.setValue(travelDtoList);
                        } catch (IOException e) {
                            travels.setValue(new ArrayList<>());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        travels.setValue(new ArrayList<>());
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return travels;
    }

    public MutableLiveData<List<TravelDto>> readTravelsByUid(String uid) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, BASE_TRAVELS_URL + uid, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<TravelDto> travelDtoList = mapper
                                    .readValue(response.toString()
                                            ,new TypeReference<List<TravelDto>>(){});
                            travels.setValue(travelDtoList);
                        } catch (IOException e) {
                            travels.setValue(new ArrayList<>());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        travels.setValue(new ArrayList<>());
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return travels;
    }

    public MutableLiveData<TravelDto> readTravelById(int id) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, BASE_TRAVELS_URL + "id/" + id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            TravelDto travelDto = mapper.readValue(response.toString(),TravelDto.class);
                            travelMutable.setValue(travelDto);
                        } catch (IOException e) {
                            travelMutable.setValue(null);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        travelMutable.setValue(null);
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return travelMutable;
    }

    public MutableLiveData<List<TravelDto>> readTravelsByPartnerUid(String uid) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, TRAVEL_PARTNER_URL + uid, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<TravelDto> travelDtoList = mapper
                                    .readValue(response.toString()
                                            ,new TypeReference<List<TravelDto>>(){});
                            travels.setValue(travelDtoList);
                        } catch (IOException e) {
                            travels.setValue(new ArrayList<>());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        travels.setValue(new ArrayList<>());
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return travels;
    }

    public MutableLiveData<List<TravelDto>> readTravelsAvailable(String uid) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, TRAVEL_AVAILABLE_URL + uid, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<TravelDto> travelDtoList = mapper
                                    .readValue(response.toString()
                                            ,new TypeReference<List<TravelDto>>(){});
                            travels.setValue(travelDtoList);
                        } catch (IOException e) {
                            e.printStackTrace();
                            travels.setValue(new ArrayList<>());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        travels.setValue(new ArrayList<>());
                    }
                });
        requestQueue.add(request);
        return travels;
    }

    public MutableLiveData<List<TravelDto>> readTravelsPending(String uid) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, TRAVEL_PENDING_URL + uid, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<TravelDto> travelDtoList = mapper
                                    .readValue(response.toString()
                                            ,new TypeReference<List<TravelDto>>(){});
                            travels.setValue(travelDtoList);
                        } catch (IOException e) {
                            e.printStackTrace();
                            travels.setValue(new ArrayList<>());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        travels.setValue(new ArrayList<>());
                    }
                });
        requestQueue.add(request);
        return travels;
    }

    public MutableLiveData<List<TravelDto>> readTravelsFinished(String uid) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, TRAVEL_FINISHED_URL + uid, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<TravelDto> travelDtoList = mapper
                                    .readValue(response.toString()
                                            ,new TypeReference<List<TravelDto>>(){});
                            travels.setValue(travelDtoList);
                        } catch (IOException e) {
                            e.printStackTrace();
                            travels.setValue(new ArrayList<>());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        travels.setValue(new ArrayList<>());
                    }
                });
        requestQueue.add(request);
        return travels;
    }

    public MutableLiveData<TravelDto> saveTravel(TravelDto travel) throws JsonProcessingException, JSONException {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, BASE_TRAVELS_URL, travelRequest(travel),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            TravelDto travelDto = mapper.readValue(response.toString(),TravelDto.class);
                            travelMutable.setValue(travelDto);
                        } catch (IOException e) {
                            e.printStackTrace();
                            travelMutable.setValue(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        travelMutable.setValue(null);
                    }
                });
        requestQueue.add(request);
        return travelMutable;
    }

    public MutableLiveData<TravelDto> updateTravel(TravelDto travel) throws JsonProcessingException, JSONException {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT, BASE_TRAVELS_URL, travelRequest(travel),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            TravelDto travelDto = mapper.readValue(response.toString(),TravelDto.class);
                            travelMutable.setValue(travelDto);
                        } catch (IOException e) {
                            e.printStackTrace();
                            travelMutable.setValue(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        travelMutable.setValue(null);
                    }
                });
        requestQueue.add(request);
        return travelMutable;
    }

    public MutableLiveData<TravelDto> deleteTravel(TravelDto travel)
            throws JsonProcessingException, JSONException {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE, BASE_TRAVELS_URL + "id/"
                + travel.getId(), travelRequest(travel),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            TravelDto travelDto = mapper
                                    .readValue(response.toString(),TravelDto.class);
                            travelMutable.setValue(travelDto);
                        } catch (IOException e) {
                            e.printStackTrace();
                            travelMutable.setValue(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        travelMutable.setValue(null);
                    }
                });
        requestQueue.add(request);
        return travelMutable;
    }

    private JSONObject travelRequest(TravelDto travelDto) throws JsonProcessingException, JSONException {
        String json = mapper.writeValueAsString(travelDto);
        System.out.println(json);
        return new JSONObject(json);
    }
}
