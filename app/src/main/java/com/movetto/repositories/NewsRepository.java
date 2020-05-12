package com.movetto.repositories;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movetto.dtos.NewsDto;
import com.movetto.handler.UrlHandler;

import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

public class NewsRepository {

    private static final String BASE_NEWS_URL = UrlHandler.API_URL + UrlHandler.NEWS_URL;

    private RequestQueue requestQueue;
    private MutableLiveData<List<NewsDto>> news;
    private ObjectMapper mapper;

    public NewsRepository(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        this.mapper = new ObjectMapper();
        this.news = new MutableLiveData<>();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public MutableLiveData<List<NewsDto>> readNews() {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, BASE_NEWS_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<NewsDto> newsDtoList = mapper
                                    .readValue(response.toString()
                                            ,new TypeReference<List<NewsDto>>(){});
                            news.setValue(newsDtoList);
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
        return news;
    }
}
