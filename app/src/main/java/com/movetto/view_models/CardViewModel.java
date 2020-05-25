package com.movetto.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.movetto.dtos.CardDto;
import com.movetto.repositories.CardRepository;

import org.json.JSONException;

import java.util.List;

public class CardViewModel extends AndroidViewModel {

    private CardRepository cardRepository;

    public CardViewModel(@NonNull Application application) {
        super(application);
        RequestQueue requestQueue = Volley
                .newRequestQueue(getApplication().getApplicationContext());
        cardRepository = new CardRepository(requestQueue);
    }

    public MutableLiveData<List<CardDto>> readCards() {
        return cardRepository.readCards();
    }

    public MutableLiveData<List<CardDto>> readCardsByUserId(int id){
        return cardRepository.readCardsByUserId(id);
    }

    public MutableLiveData<CardDto> readCardById(int id){
        return cardRepository.readCardById(id);
    }

    public MutableLiveData<CardDto> saveCard(CardDto card)
            throws JsonProcessingException, JSONException {
        return cardRepository.saveCard(card);
    }

    public MutableLiveData<CardDto> updateCard(CardDto card)
            throws JsonProcessingException, JSONException {
        return cardRepository.updateCard(card);
    }

    public MutableLiveData<CardDto> deleteCard(CardDto card)
            throws JsonProcessingException, JSONException {
        return cardRepository.deleteCard(card);
    }

}
