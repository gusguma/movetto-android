package com.movetto.activities.ui.shipments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;
import com.movetto.R;
import com.movetto.adapters.ShipmentDetailAdapter;
import com.movetto.dtos.PackageDto;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.ShipmentStatus;
import com.movetto.view_models.ShipmentViewModel;

import java.util.Formatter;
import java.util.List;
import java.util.Set;

public class ShipmentDetailFragment extends Fragment implements TabLayout.OnTabSelectedListener,
        View.OnFocusChangeListener,
        View.OnClickListener {

    private static final int SHIPMENT_HASH = 213;

    private View root;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ShipmentViewModel shipmentViewModel;
    private Bundle data;
    private ShipmentDto shipment;
    private TextView shipmentNumber;
    private TextView shipmentPrice;
    private Chip shipmentStatus;

    public ShipmentDetailFragment() {
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
        root = inflater.inflate(R.layout.fragment_shipment_detail, container, false);
        tabLayout = root.findViewById(R.id.shipment_detail_tab_layout);
        viewPager = root.findViewById(R.id.shipment_detail_view_pager);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setComponents() {
        shipmentNumber = root.findViewById(R.id.shipment_detail_number);
        shipmentPrice = root.findViewById(R.id.shipment_detail_price);
        shipmentStatus = root.findViewById(R.id.shipment_detail_status);
    }

    private void setAdapter(){
        ShipmentDetailAdapter adapter = new ShipmentDetailAdapter(
                getChildFragmentManager(), tabLayout.getTabCount(), getContext());
        adapter.setShipmentId(shipment.getId());
        viewPager.setAdapter(adapter);
    }

    private void setListeners(){
        tabLayout.addOnTabSelectedListener(this);
    }

    private void getShipmentData(){
        data = getArguments();
        if (data != null && data.getInt("shipmentId") != 0){
            shipmentViewModel.readShipmentById(data.getInt("shipmentId"))
                    .observe(getViewLifecycleOwner(), new Observer<ShipmentDto>() {
                @Override
                public void onChanged(ShipmentDto shipmentDto) {
                    if (shipmentDto != null) {
                        shipment = shipmentDto;
                        setShipmentDetailData();
                        setAdapter();
                    } else {
                        setShipmentDataNotFound();
                    }
                }
            });
        }
    }

    private String encodeShipmentNumber(){
        Formatter formatter = new Formatter();
        int number = (shipment.getId() * SHIPMENT_HASH) - 23 ;
        return "" + formatter.format("%08d", number);
    }

    private void setShipmentDetailData(){
        shipmentNumber.setText(encodeShipmentNumber());
        shipmentPrice.setText(calculateShipmentPrice());
        setShipmentStatus();
    }

    private String calculateShipmentPrice(){
        Set<PackageDto> packages = shipment.getPackages();
        packages.forEach(PackageDto::setPackagePrice);
        shipment.setShipmentPrice();
        return String.valueOf(shipment.getPriceShipment());
    }

    private void setShipmentStatus(){
        if (shipment.getStatus() == ShipmentStatus.ACCEPTED)
            shipmentStatus.setText("Aceptado");
        if (shipment.getStatus() == ShipmentStatus.COLLECTED)
            shipmentStatus.setText("Recogido");
        if (shipment.getStatus() == ShipmentStatus.DELIVERED)
            shipmentStatus.setText("Entregado");
        if (shipment.getStatus() == ShipmentStatus.DETAINED)
            shipmentStatus.setText("Retenido");
        if (shipment.getStatus() == ShipmentStatus.PREPARED)
            shipmentStatus.setText("Preparado");
        if (shipment.getStatus() == ShipmentStatus.SAVED)
            shipmentStatus.setText("Grabado");
        if (shipment.getStatus() == ShipmentStatus.TRANSIT)
            shipmentStatus.setText("En Transito");
    }

    private View setIcon (int id){
        return root.findViewById(id);
    }

    private void setShipmentDataNotFound(){
        Toast.makeText(root.getContext(),"No se han encontrado datos"
                ,Toast.LENGTH_LONG).show();
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipment_detail_to_nav_shipments_list);
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
        //Nothing to Do
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //Nothing to Do
    }



}
