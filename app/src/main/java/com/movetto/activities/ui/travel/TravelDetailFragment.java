package com.movetto.activities.ui.travel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;
import com.movetto.R;
import com.movetto.adapters.ShipmentDetailAdapter;
import com.movetto.adapters.TravelDetailAdapter;
import com.movetto.dtos.TravelDto;
import com.movetto.dtos.TravelStatus;
import com.movetto.view_models.TravelViewModel;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.Formatter;

public class TravelDetailFragment extends Fragment implements TabLayout.OnTabSelectedListener,
        View.OnClickListener {

    private static final int TRAVEL_HASH = 213;

    private View root;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TravelViewModel travelViewModel;
    private Bundle data;
    private TravelDto travel;
    private TextView travelNumber;
    private TextView travelPrice;
    private Chip travelStatus;
    private Button payButton;
    private Button deleteButton;

    public TravelDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setListeners();
        getTravelData();
        return root;
    }

    private void setViewModels() {
        travelViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_travel_detail, container, false);
        tabLayout = root.findViewById(R.id.travel_detail_tab_layout);
        viewPager = root.findViewById(R.id.travel_detail_view_pager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setComponents() {
        travelNumber = root.findViewById(R.id.travel_detail_number);
        travelPrice = root.findViewById(R.id.travel_detail_price);
        travelStatus = root.findViewById(R.id.travel_detail_status);
        payButton = root.findViewById(R.id.travel_detail_pay_button);
        deleteButton = root.findViewById(R.id.travel_detail_delete_button);
    }

    private void setListeners(){
        tabLayout.addOnTabSelectedListener(this);
        payButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    private void getTravelData(){
        data = getArguments();
        if (data != null && data.getInt("serviceId") != 0){
            travelViewModel.readTravelById(data.getInt("serviceId"))
                    .observe(getViewLifecycleOwner(), new Observer<TravelDto>() {
                        @Override
                        public void onChanged(TravelDto travelDto) {
                            if (travelDto != null) {
                                travel = travelDto;
                                setShipmentDetailData();
                                setAdapter();
                                checkTravelStatus();
                            } else {
                                setShipmentDataNotFound();
                            }
                        }
                    });
        }
    }

    private void setShipmentDetailData(){
        travelNumber.setText(encodeTravelNumber());
        travelPrice.setText(getPriceTravel());
        setTravelStatus();
    }

    private String encodeTravelNumber(){
        Formatter formatter = new Formatter();
        int number = (travel.getId() * TRAVEL_HASH) - 23 ;
        return "" + formatter.format("%08d", number);
    }

    private String getPriceTravel(){
        return new DecimalFormat("0.00").format(travel.getPriceTravel());
    }

    private void setAdapter(){
        TravelDetailAdapter adapter = new TravelDetailAdapter(
                getChildFragmentManager(), tabLayout.getTabCount(), getContext());
        data.putInt("travelId", travel.getId());
        adapter.setData(data);
        viewPager.setAdapter(adapter);
    }

    private void checkTravelStatus(){
        if (travel.getStatus() == TravelStatus.SAVED) {
            payButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        }
    }

    private void setTravelStatus(){
        if (travel.getStatus() == TravelStatus.ACCEPTED)
            travelStatus.setText("Aceptado");
        if (travel.getStatus() == TravelStatus.PAID)
            travelStatus.setText("Pagado");
        if (travel.getStatus() == TravelStatus.FINISHED)
            travelStatus.setText("Entregado");
        if (travel.getStatus() == TravelStatus.DETAINED)
            travelStatus.setText("Retenido");
        if (travel.getStatus() == TravelStatus.SAVED)
            travelStatus.setText("Grabado");
        if (travel.getStatus() == TravelStatus.TRANSIT)
            travelStatus.setText("En Transito");
    }

    private void setShipmentDataNotFound(){
        Toast.makeText(root.getContext(),"No se han encontrado datos"
                ,Toast.LENGTH_LONG).show();
        Navigation.findNavController(root).navigate(
                R.id.action_nav_travel_detail_to_nav_travel_empty);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        //Nothing to Do
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        //Nothing to Do
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.travel_detail_pay_button) {
            setPayButtonListener();
        }
        if (v.getId() == R.id.travel_detail_delete_button) {
            try {
                setDeleteButtonListener();
            } catch (JsonProcessingException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPayButtonListener(){
        data.putString("serviceNumber", encodeTravelNumber());
        data.putDouble("amount", travel.getPriceTravel());

        Navigation.findNavController(root).navigate(
                R.id.action_nav_travel_detail_to_nav_service_payment_detail, data);
    }

    private void setDeleteButtonListener() throws JsonProcessingException, JSONException {
        travelViewModel.deleteTravel(travel).observe(getViewLifecycleOwner(), new Observer<TravelDto>() {
            @Override
            public void onChanged(TravelDto travelDto) {
                if (travelDto != null){
                    travelDeleteOk();
                } else {
                    travelDeleteError();
                }
            }
        });
    }

    private void travelDeleteOk(){
        Navigation.findNavController(root).navigate(
                R.id.action_nav_travel_detail_to_nav_travel_empty);
        Toast.makeText(root.getContext(),
                "Envío Borrado Correctamente",
                Toast.LENGTH_LONG).show();
    }

    private void travelDeleteError(){
        Navigation.findNavController(root).navigate(
                R.id.action_nav_travel_detail_to_nav_travel_empty);
        Toast.makeText(root.getContext(),
                "No se ha podido eliminar el envío",
                Toast.LENGTH_LONG).show();
    }
}
