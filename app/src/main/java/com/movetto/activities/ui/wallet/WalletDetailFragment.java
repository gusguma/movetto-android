package com.movetto.activities.ui.wallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.movetto.R;
import com.movetto.adapters.WalletDetailAdapter;
import com.movetto.dtos.WalletDto;
import com.movetto.view_models.WalletViewModel;

import java.text.DecimalFormat;

public class WalletDetailFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private View root;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private WalletViewModel walletViewModel;
    private Bundle data;
    private WalletDto wallet;
    private TextView balance;

    public WalletDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setListeners();
        getWalletData();
        return root;
    }

    private void setViewModels() {
        walletViewModel = new ViewModelProvider(this).get(WalletViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_wallet_detail, container, false);
        tabLayout = root.findViewById(R.id.wallet_detail_tab_layout);
        viewPager = root.findViewById(R.id.wallet_detail_view_pager);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setComponents() {
        balance = root.findViewById(R.id.wallet_detail_balance);
    }

    private void setAdapter(){
        WalletDetailAdapter adapter = new WalletDetailAdapter(
                getChildFragmentManager(), tabLayout.getTabCount(), getContext());
        adapter.setWalletId(wallet.getId());
        adapter.setCustomerId(data.getInt("customerId"));
        viewPager.setAdapter(adapter);
    }

    private void setListeners(){
        tabLayout.addOnTabSelectedListener(this);
    }

    private void getWalletData(){
        data = getArguments();
        if (data != null && data.getString("customerUid") != null) {
            walletViewModel.readWallet(data.getString("customerUid"))
                    .observe(getViewLifecycleOwner(), new Observer<WalletDto>() {
                @Override
                public void onChanged(WalletDto walletDto) {
                    if (walletDto != null) {
                        wallet = walletDto;
                        setWalletDetailData();
                        setAdapter();
                    }
                }
            });
        }
    }

    private void setWalletDetailData(){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        balance.setText(decimalFormat.format(wallet.getBalance()));
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
