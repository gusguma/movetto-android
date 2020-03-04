package com.movetto.activities.ui.travel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.movetto.R;

public class TravelLandFragment extends Fragment {

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_travel_land, container, false);
        setButtonCreate();
        return root;
    }

    public void setButtonCreate(){
        Button buttonCreate = root.findViewById(R.id.travel_land_new_button);
        buttonCreate.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_nav_travel_to_nav_travel_create,null)
        );
    }
}