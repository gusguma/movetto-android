package com.movetto.activities.ui.packages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PackagesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PackagesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is packages fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}