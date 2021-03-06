package com.movetto.activities.ui.travellers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.movetto.R;
import com.movetto.adapters.TravelAvailableListAdapter;
import com.movetto.view_models.TravelViewModel;

public class TravellerAvailableListFragment extends Fragment
        implements TabLayout.OnTabSelectedListener {

    private View root;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TravelViewModel travelViewModel;
    private Bundle data;

    public TravellerAvailableListFragment() {
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
        travelViewModel = new ViewModelProvider(this).get(TravelViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_travellers_available_list, container, false);
        tabLayout = root.findViewById(R.id.travel_available_detail_tab_layout);
        viewPager = root.findViewById(R.id.travel_available_detail_view_pager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setComponents() {
        data = getArguments();
    }

    private void setListeners(){
        tabLayout.addOnTabSelectedListener(this);
    }

    private void setAdapter(){
        TravelAvailableListAdapter adapter = new TravelAvailableListAdapter(
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
