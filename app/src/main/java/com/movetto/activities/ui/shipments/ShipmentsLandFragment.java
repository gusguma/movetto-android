package com.movetto.activities.ui.shipments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.movetto.R;

public class ShipmentsLandFragment extends Fragment {

    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_shipment_land, container, false);
        setButtonCreate();
        return root;
    }

    public void setButtonCreate(){
        Button buttonCreate = root.findViewById(R.id.shipment_land_new_button);
        buttonCreate.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_nav_shipments_to_nav_shipments_create,null)
        );
    }
}