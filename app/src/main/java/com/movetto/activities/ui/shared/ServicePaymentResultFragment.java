package com.movetto.activities.ui.shared;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.movetto.R;

public class ServicePaymentResultFragment extends Fragment {
    private static final int SHIPMENT = 1;
    private static final int TRAVEL = 2;

    private View root;
    private Bundle bundle;
    private ImageView image;
    private TextView title;
    private TextView subtitle;
    private Button button;

    public ServicePaymentResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setLayout(inflater,container);
        setComponents();
        setListeners();
        return root;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container){
        root = inflater.inflate(R.layout.fragment_service_payment_result
                , container, false);
    }

    private void setComponents(){
        bundle = getArguments();
        if (bundle != null){
            image = root.findViewById(R.id.payment_service_result_img);
            image.setImageResource(bundle.getInt("image"));
            title = root.findViewById(R.id.payment_service_result_title);
            title.setText(bundle.getString("title"));
            subtitle = root.findViewById(R.id.payment_service_result_subtitle);
            subtitle.setText(bundle.getString("subtitle"));
        }
        button = root.findViewById(R.id.payment_service_result_continue_button);
    }

    private void setListeners(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bundle != null && bundle.getInt("serviceType") == SHIPMENT) {
                    setButtonListenerShipment();
                }
                if (bundle != null && bundle.getInt("serviceType") == TRAVEL) {
                    setButtonListenerShipment();
                }
            }
        });
    }

    private void setButtonListenerShipment() {
        Navigation.findNavController(root).navigate(
                R.id.action_nav_service_payment_result_to_nav_shipment_detail, bundle);
    }

    private void setButtonListenerTravel() {
        //TODO
    }
}
