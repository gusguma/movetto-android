package com.movetto.activities.ui.travellers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TravellersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TravellersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is travellers fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}