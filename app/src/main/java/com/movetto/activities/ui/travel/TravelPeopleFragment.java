package com.movetto.activities.ui.travel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movetto.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TravelPeopleFragment extends Fragment {

    public TravelPeopleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_travel_people, container, false);
    }
}
