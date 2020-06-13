package com.movetto.activities.ui.travellers;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
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

import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;
import com.movetto.R;
import com.movetto.adapters.TravelDetailAdapter;
import com.movetto.dtos.TravelDto;
import com.movetto.states.TravelContext;
import com.movetto.view_models.TravelViewModel;

import java.text.DecimalFormat;
import java.util.Formatter;

public class TravellerAvailableDetailFragment extends Fragment implements
    TabLayout.OnTabSelectedListener {

    private static final int TRAVEL_HASH = 213;

    private View root;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TravelViewModel travelViewModel;
    private TravelContext travelContext;
    private Bundle data;
    private TextView travelNumber;
    private TextView travelPrice;
    private TravelDto travel;
    private Chip travelStatus;
    private Button redButton;
    private Button greenButton;
    private ConstraintLayout progressBar;

    public TravellerAvailableDetailFragment() {
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
        root = inflater.inflate(R.layout.fragment_traveller_available_detail, container, false);
        tabLayout = root.findViewById(R.id.travel_available_detail_tab_layout);
        viewPager = root.findViewById(R.id.travel_available_detail_view_pager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setComponents() {
        travelNumber = root.findViewById(R.id.travel_available_detail_number);
        travelPrice = root.findViewById(R.id.travel_available_detail_price);
        travelStatus = root.findViewById(R.id.travel_available_detail_status);
        greenButton = root.findViewById(R.id.travel_available_detail_green_button);
        redButton = root.findViewById(R.id.travel_available_detail_red_button);
        progressBar = root.findViewById(R.id.travel_available_detail_progress_bar);
    }

    private void setListeners(){
        tabLayout.addOnTabSelectedListener(this);
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
                                setTravelDetailData();
                                setAdapter();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                setTravelDataNotFound();
                            }
                        }
                    });
        }
    }

    private void setTravelDetailData(){
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

    private void setTravelStatus(){
        travelContext = new TravelContext(travel, root);
    }

    private void setTravelDataNotFound(){
        Toast.makeText(root.getContext(),"No se han encontrado datos"
                ,Toast.LENGTH_LONG).show();
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipment_available_detail_to_nav_shipment_available_empty);
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
}
