package com.movetto.activities.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import com.movetto.R;
import com.movetto.adapters.AccountPartnerAdapter;

public class AccountPartnerFragment extends Fragment
        implements TabLayout.OnTabSelectedListener,
        View.OnFocusChangeListener,
        View.OnClickListener {

    private View root;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public AccountPartnerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setLayout(inflater, container);
        setAdapter();
        setListeners();
        return root;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_account_partner, container, false);
        tabLayout = root.findViewById(R.id.account_partner_tab_layout);
        viewPager = root.findViewById(R.id.account_partner_view_pager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setAdapter(){
        AccountPartnerAdapter adapter = new AccountPartnerAdapter(
                getChildFragmentManager(), tabLayout.getTabCount(), getContext());
        viewPager.setAdapter(adapter);
    }

    private void setListeners(){
        tabLayout.addOnTabSelectedListener(this);
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
