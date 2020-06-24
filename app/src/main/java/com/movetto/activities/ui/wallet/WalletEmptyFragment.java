package com.movetto.activities.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.movetto.R;
import com.movetto.activities.MainActivity;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.WalletDto;
import com.movetto.view_models.CustomerViewModel;
import com.movetto.view_models.WalletViewModel;

import java.util.Objects;

public class WalletEmptyFragment extends Fragment {

    private View root;
    private CustomerViewModel customerViewModel;
    private WalletViewModel walletViewModel;
    private UserDto customer;
    private ConstraintLayout progressBar;
    private Button buttonContinue;
    private Button buttonLater;
    private Bundle data;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater,container);
        setComponents();
        getCustomer();
        setButtonLater();
        return root;
    }

    private void setViewModels() {
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        walletViewModel = new ViewModelProvider(this).get(WalletViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_wallet_empty, container, false);
    }

    private void setComponents() {
        buttonContinue = root.findViewById(R.id.wallet_land_continue_button);
        buttonLater = root.findViewById(R.id.wallet_land_later_button);
        progressBar = root.findViewById(R.id.wallet_land_progress_bar);
    }

    private void getCustomer() {
        customerViewModel.readCustomer().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                if(userDto != null){
                    customer = userDto;
                    setBundle();
                    setButtonContinueExist();
                    readWallet();
                } else {
                    progressBar.setVisibility(View.GONE);
                    setButtonContinueEmpty();
                }
            }
        });
    }

    private void readWallet(){
        walletViewModel.readWallet(customer.getUid()).observe(getViewLifecycleOwner(), new Observer<WalletDto>() {
            @Override
            public void onChanged(WalletDto walletDto) {
                if (walletDto != null && !walletDto.getTransactions().isEmpty()){
                    Navigation.findNavController(root)
                            .navigate(R.id.action_nav_wallet_to_nav_wallet_detail, data);
                    getParentFragmentManager()
                            .beginTransaction()
                            .remove(WalletEmptyFragment.this)
                            .commit();
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setBundle(){
        data = new Bundle();
        data.putInt("customerId", customer.getId());
        data.putString("customerUid", customer.getUid());
    }

    private void setButtonContinueExist(){
        buttonContinue.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_nav_wallet_to_nav_wallet_deposit_amount,data)
        );
    }

    private void setButtonContinueEmpty(){
        buttonContinue.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        R.id.action_nav_wallet_to_nav_account_customer_empty,null)
        );
    }

    private void setButtonLater(){
        buttonLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(root.getContext(), MainActivity.class));
                Objects.requireNonNull(getActivity()).finish();
            }
        });
    }
}