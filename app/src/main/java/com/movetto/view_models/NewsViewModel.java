package com.movetto.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.movetto.dtos.NewsDto;
import com.movetto.repositories.NewsRepository;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository newsRepository;
    private MutableLiveData<List<NewsDto>> news;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplication().getApplicationContext());
        newsRepository = new NewsRepository(requestQueue);
        news = new MutableLiveData<>();
    }

    public MutableLiveData<List<NewsDto>> readNews() {
        news = newsRepository.readNews();
        return news;
    }
}
