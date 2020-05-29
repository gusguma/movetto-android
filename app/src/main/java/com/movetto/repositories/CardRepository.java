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
import com.movetto.dtos.CardDto;
import com.movetto.handler.UrlHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {

    private static final String BASE_CARDS_URL = UrlHandler.API_URL + UrlHandler.CARD_URL;

    private RequestQueue requestQueue;
    private MutableLiveData<List<CardDto>> cards;
    private MutableLiveData<CardDto> cardMutable;
    private ObjectMapper mapper;

    public CardRepository(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        cards = new MutableLiveData<>();
        cardMutable = new MutableLiveData<>();
        this.mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public MutableLiveData<List<CardDto>> readCards() {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, BASE_CARDS_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<CardDto> cardDtoList = mapper
                                    .readValue(response.toString()
                                            ,new TypeReference<List<CardDto>>(){});
                            cards.setValue(cardDtoList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        cards.setValue(new ArrayList<>());
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return cards;
    }

    public MutableLiveData<List<CardDto>> readCardsByUserId(int id) {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, BASE_CARDS_URL + id, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<CardDto> cardDtoList = mapper
                                    .readValue(response.toString()
                                            ,new TypeReference<List<CardDto>>(){});
                            cards.setValue(cardDtoList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        cards.setValue(new ArrayList<>());
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return cards;
    }

    public MutableLiveData<CardDto> readCardById(int id) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, BASE_CARDS_URL + "id/" + id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            CardDto cardDto = mapper.readValue(response.toString(),CardDto.class);
                            cardMutable.setValue(cardDto);
                        } catch (IOException e) {
                            cardMutable.setValue(null);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        cardMutable.setValue(null);
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return cardMutable;
    }

    public MutableLiveData<CardDto> saveCard(CardDto card) throws JsonProcessingException, JSONException {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, BASE_CARDS_URL, cardRequest(card),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            CardDto cardDto = mapper.readValue(response.toString(),CardDto.class);
                            cardMutable.setValue(cardDto);
                        } catch (IOException e) {
                            e.printStackTrace();
                            cardMutable.setValue(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        cardMutable.setValue(null);
                    }
                });
        requestQueue.add(request);
        return cardMutable;
    }

    public MutableLiveData<CardDto> updateCard(CardDto card) throws JsonProcessingException, JSONException {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT, BASE_CARDS_URL, cardRequest(card),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            CardDto cardDto = mapper.readValue(response.toString(),CardDto.class);
                            cardMutable.setValue(cardDto);
                        } catch (IOException e) {
                            e.printStackTrace();
                            cardMutable.setValue(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        cardMutable.setValue(null);
                    }
                });
        requestQueue.add(request);
        return cardMutable;
    }

    public MutableLiveData<CardDto> deleteCard(CardDto card)
            throws JsonProcessingException, JSONException {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE, BASE_CARDS_URL + card.getId()
                , cardRequest(card),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            CardDto cardDto = mapper
                                    .readValue(response.toString(),CardDto.class);
                            cardMutable.setValue(cardDto);
                        } catch (IOException e) {
                            e.printStackTrace();
                            cardMutable.setValue(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        cardMutable.setValue(null);
                    }
                });
        requestQueue.add(request);
        return cardMutable;
    }

    private JSONObject cardRequest(CardDto cardDto) throws JsonProcessingException, JSONException {
        String json = mapper.writeValueAsString(cardDto);
        System.out.println(json);
        return new JSONObject(json);
    }

}
