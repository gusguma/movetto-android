package com.movetto.activities.ui.packages;

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
import com.movetto.adapters.ShipmentDetailAdapter;
import com.movetto.dtos.PackageDto;
import com.movetto.dtos.ShipmentDto;
import com.movetto.states.ShipmentContext;
import com.movetto.view_models.ShipmentViewModel;

import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Set;

public class ShipmentAvailableDetailFragment extends Fragment {

    private static final int SHIPMENT_HASH = 213;

    private View root;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ShipmentViewModel shipmentViewModel;
    private ShipmentContext shipmentContext;
    private Bundle data;
    private TextView shipmentNumber;
    private TextView shipmentPrice;
    private ShipmentDto shipment;
    private Chip shipmentStatus;
    private Button redButton;
    private Button greenButton;
    private ConstraintLayout progressBar;

    public ShipmentAvailableDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setListeners();
        getShipmentData();
        return root;
    }

    private void setViewModels() {
        shipmentViewModel = new ViewModelProvider(this).get(ShipmentViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_shipment_available_detail, container, false);
        tabLayout = root.findViewById(R.id.shipment_available_detail_tab_layout);
        viewPager = root.findViewById(R.id.shipment_available_detail_view_pager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setComponents() {
        shipmentNumber = root.findViewById(R.id.shipment_available_detail_number);
        shipmentPrice = root.findViewById(R.id.shipment_available_detail_price);
        shipmentStatus = root.findViewById(R.id.shipment_available_detail_status);
        greenButton = root.findViewById(R.id.shipment_available_detail_green_button);
        redButton = root.findViewById(R.id.shipment_available_detail_red_button);
        progressBar = root.findViewById(R.id.shipment_available_detail_progress_bar);
    }

    private void setListeners(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        });
    }

    private void getShipmentData(){
        data = getArguments();
        if (data != null && data.getInt("serviceId") != 0){
            shipmentViewModel.readShipmentById(data.getInt("serviceId"))
                    .observe(getViewLifecycleOwner(), new Observer<ShipmentDto>() {
                        @Override
                        public void onChanged(ShipmentDto shipmentDto) {
                            if (shipmentDto != null) {
                                shipment = shipmentDto;
                                setShipmentDetailData();
                                setAdapter();
                                progressBar.setVisibility(View.GONE);
                            } else {
                                setShipmentDataNotFound();
                            }
                        }
                    });
        }
    }

    private void setShipmentDetailData(){
        shipmentNumber.setText(encodeShipmentNumber());
        shipmentPrice.setText(calculateShipmentPriceString());
        setShipmentStatus();
    }

    private String encodeShipmentNumber(){
        Formatter formatter = new Formatter();
        int number = (shipment.getId() * SHIPMENT_HASH) - 23 ;
        return "" + formatter.format("%08d", number);
    }

    private String calculateShipmentPriceString(){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        calculateShipmentPrice();
        return decimalFormat.format(shipment.getPriceShipment());
    }

    private void calculateShipmentPrice(){
        Set<PackageDto> packages = shipment.getPackages();
        packages.forEach(PackageDto::setPackagePrice);
        shipment.setShipmentPrice();
    }

    private void setAdapter(){
        ShipmentDetailAdapter adapter = new ShipmentDetailAdapter(
                getChildFragmentManager(), tabLayout.getTabCount(), getContext());
        data.putInt("shipmentId", shipment.getId());
        adapter.setData(data);
        viewPager.setAdapter(adapter);
    }

    private void setShipmentStatus(){
        shipmentContext = new ShipmentContext(shipment, root);
    }

    private void setShipmentDataNotFound(){
        Toast.makeText(root.getContext(),"No se han encontrado datos"
                ,Toast.LENGTH_LONG).show();
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipment_available_detail_to_nav_shipment_available_empty);
    }
}
