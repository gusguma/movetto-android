package com.movetto.activities.ui.shipments;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;
import com.movetto.R;
import com.movetto.adapters.ShipmentDetailAdapter;
import com.movetto.dtos.PackageDto;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.ShipmentStatus;
import com.movetto.view_models.ShipmentViewModel;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Set;

public class ShipmentDetailFragment extends Fragment implements TabLayout.OnTabSelectedListener,
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
    private Button payButton;
    private Button deleteButton;
    private ConstraintLayout progressBar;

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
        payButton = root.findViewById(R.id.shipment_detail_pay_button);
        deleteButton = root.findViewById(R.id.shipment_detail_delete_button);
        progressBar = root.findViewById(R.id.shipment_detail_progress_bar);
    }

    private void setListeners(){
        tabLayout.addOnTabSelectedListener(this);
        payButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
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
                        checkShipmentStatus();
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

    private void checkShipmentStatus(){
        if (shipment.getStatus() == ShipmentStatus.SAVED) {
            payButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        }
    }

    private void setShipmentStatus(){
        if (shipment.getStatus() == ShipmentStatus.PAID)
            shipmentStatus.setText("Pagado");
        if (shipment.getStatus() == ShipmentStatus.COLLECTED)
            shipmentStatus.setText("Recogido");
        if (shipment.getStatus() == ShipmentStatus.DELIVERED)
            shipmentStatus.setText("Entregado");
        if (shipment.getStatus() == ShipmentStatus.DETAINED)
            shipmentStatus.setText("Retenido");
        if (shipment.getStatus() == ShipmentStatus.ACCEPTED)
            shipmentStatus.setText("Aceptado");
        if (shipment.getStatus() == ShipmentStatus.SAVED)
            shipmentStatus.setText("Grabado");
        if (shipment.getStatus() == ShipmentStatus.TRANSIT)
            shipmentStatus.setText("En Transito");
    }

    private void setShipmentDataNotFound(){
        Toast.makeText(root.getContext(),"No se han encontrado datos"
                ,Toast.LENGTH_LONG).show();
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipment_detail_to_nav_shipments_empty);
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
        if (v.getId() == R.id.shipment_detail_pay_button) {
            setPayButtonListener();
        }
        if (v.getId() == R.id.shipment_detail_delete_button) {
            try {
                setDeleteButtonListener();
            } catch (JsonProcessingException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPayButtonListener(){
        data.putString("serviceNumber", encodeShipmentNumber());
        data.putDouble("amount", shipment.getPriceShipment());
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipment_detail_to_nav_service_payment_detail, data);
    }

    private void setDeleteButtonListener() throws JsonProcessingException, JSONException {
        shipmentViewModel.deleteShipment(shipment).observe(this, new Observer<ShipmentDto>() {
            @Override
            public void onChanged(ShipmentDto shipmentDto) {
                if (shipmentDto != null){
                    shipmentDeleteOk();
                } else {
                    shipmentDeleteError();
                }
            }
        });
    }

    private void shipmentDeleteOk(){
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipment_detail_to_nav_shipments_empty);
        Toast.makeText(root.getContext(),
                "Envío Borrado Correctamente",
                Toast.LENGTH_LONG).show();
    }

    private void shipmentDeleteError(){
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipment_detail_to_nav_shipments_empty);
        Toast.makeText(root.getContext(),
                "No se ha podido eliminar el envío",
                Toast.LENGTH_LONG).show();
    }

}
