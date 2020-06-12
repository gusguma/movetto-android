package com.movetto.activities.ui.packages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;
import com.movetto.R;
import com.movetto.adapters.ShipmentAvailableListAdapter;
import com.movetto.adapters.ShipmentDetailAdapter;
import com.movetto.adapters.ShipmentListAdapter;
import com.movetto.dtos.ShipmentDto;
import com.movetto.view_models.ShipmentViewModel;

public class ShipmentAvailableListFragment extends Fragment
        implements TabLayout.OnTabSelectedListener {

    private View root;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ShipmentViewModel shipmentViewModel;
    private Bundle data;

    public ShipmentAvailableListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setListeners();
        setAdapter();
        return root;
    }

    private void setViewModels() {
        shipmentViewModel = new ViewModelProvider(this).get(ShipmentViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_shipment_available_list, container, false);
        tabLayout = root.findViewById(R.id.shipment_available_detail_tab_layout);
        viewPager = root.findViewById(R.id.shipment_available_detail_view_pager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setComponents() {
        data = getArguments();
    }

    private void setListeners(){
        tabLayout.addOnTabSelectedListener(this);
    }

    private void setAdapter(){
        ShipmentAvailableListAdapter adapter = new ShipmentAvailableListAdapter(
                getChildFragmentManager(), tabLayout.getTabCount(), getContext());
        adapter.setData(data);
        viewPager.setAdapter(adapter);
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
