package com.movetto.activities.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.tabs.TabLayout;

import com.movetto.R;
import com.movetto.adapters.AccountPartnerAdapter;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.UserDto;
import com.movetto.view_models.PartnerViewModel;

import java.util.Objects;

public class AccountPartnerFragment extends Fragment
        implements TabLayout.OnTabSelectedListener,
        View.OnFocusChangeListener,
        View.OnClickListener {

    private View root;
    private PartnerViewModel partnerViewModel;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AccountPartnerAdapter adapter;

    private EditText displayName;
    private EditText email;
    private EditText phone;
    private EditText street;
    private EditText postalCode;
    private EditText city;
    private EditText state;
    private EditText country;
    private EditText customerId;
    private UserDto userOutputDto;
    private DirectionDto directionOutputDto;
    private Button buttonSave;
    private Boolean response;

    public AccountPartnerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setAdapter();
        setComponents();
        setListeners();
        return root;
    }

    private void setViewModels() {
        partnerViewModel = new ViewModelProvider(this).get(PartnerViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_account_partner, container, false);
        tabLayout = root.findViewById(R.id.account_partner_tab_layout);
        viewPager = root.findViewById(R.id.account_partner_view_pager);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setComponents() {
        displayName = root.findViewById(R.id.account_partner_name_edit);
        email = root.findViewById(R.id.account_partner_email_edit);
        phone = root.findViewById(R.id.account_partner_phone_edit);
        street = root.findViewById(R.id.account_partner_street_edit);
        postalCode = root.findViewById(R.id.account_partner_cp_edit);
        city = root.findViewById(R.id.account_partner_city_edit);
        state = root.findViewById(R.id.account_partner_state_edit);
        country = root.findViewById(R.id.account_partner_country_edit);
        customerId = root.findViewById(R.id.account_partner_id_edit);
        customerId.setEnabled(false);
        buttonSave = root.findViewById(R.id.account_partner_save_button);
    }

    private void setAdapter(){
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity())
                .getSupportFragmentManager();
        adapter = new AccountPartnerAdapter(fragmentManager
                ,tabLayout.getTabCount(), getContext());
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

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }
}
