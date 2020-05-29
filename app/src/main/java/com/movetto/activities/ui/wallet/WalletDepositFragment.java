package com.movetto.activities.ui.wallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.movetto.R;
import com.movetto.dtos.CardDto;
import com.movetto.dtos.DepositDto;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.WalletDto;
import com.movetto.view_models.CardViewModel;
import com.movetto.view_models.CustomerViewModel;
import com.movetto.view_models.WalletViewModel;

import org.json.JSONException;

public class WalletDepositFragment extends Fragment {

    private View root;
    private CustomerViewModel customerViewModel;
    private WalletViewModel walletViewModel;
    private CardViewModel cardViewModel;
    private double paymentAmount;
    private TextView name;
    private TextView amount;
    private TextView cardNumber;
    private UserDto customer;
    private CardDto card;
    private WalletDto wallet;
    private Button buttonPay;
    private Button buttonCancel;
    private Bundle data;

    public WalletDepositFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setPaymentData();
        setButtonPayListener();
        setButtonCancelListener();
        return root;
    }

    private void setViewModels(){
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        walletViewModel = new ViewModelProvider(this).get(WalletViewModel.class);
        cardViewModel = new ViewModelProvider(this).get(CardViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_wallet_deposit, container, false);
    }

    private void setComponents() {
        name = root.findViewById(R.id.wallet_deposit_detail_name_edit);
        amount = root.findViewById(R.id.wallet_deposit_detail_amount_edit);
        cardNumber = root.findViewById(R.id.wallet_deposit_detail_card_edit);
        buttonPay = root.findViewById(R.id.wallet_deposit_detail_pay_button);
        buttonCancel = root.findViewById(R.id.wallet_deposit_detail_cancel_button);
        data = getArguments();
    }

    private void setPaymentData(){
        if (data != null && data.getInt("customerId") != 0) {
            setCustomer();
        }
        if (data != null && data.getDouble("amount") != 0) {
            paymentAmount = data.getDouble("amount");
            amount.setText(String.valueOf(paymentAmount));
        }
        if (data != null && data.getInt("cardId") != 0) {
            setCardNumber();
        }
    }

    private void setCustomer() {
        customerViewModel.readCustomer().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                if (userDto != null) {
                    customer = userDto;
                    name.setText(customer.getDisplayName());
                    setWallet();
                }
            }
        });
    }

    private void setCardNumber() {
        cardViewModel.readCardById(data.getInt("cardId")).observe(getViewLifecycleOwner(), new Observer<CardDto>() {
            @Override
            public void onChanged(CardDto cardDto) {
                if (cardDto != null) {
                    card = cardDto;
                    cardNumber.setText(card.getCardNumber());
                }
            }
        });
    }

    private void setWallet(){
        walletViewModel.readWallet(customer.getUid()).observe(getViewLifecycleOwner(), new Observer<WalletDto>() {
            @Override
            public void onChanged(WalletDto walletDto) {
                if (walletDto != null) {
                    wallet = walletDto;
                }
            }
        });
    }

    private void setButtonPayListener() {
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wallet != null) {
                    createTransaction();
                    try {
                        updateWallet();
                    } catch (JsonProcessingException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    wallet = new WalletDto(customer);
                    createTransaction();
                    try {
                        saveWallet();
                    } catch (JsonProcessingException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setButtonCancelListener() {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(
                        R.id.action_nav_wallet_deposit_detail_to_nav_wallet
                );
            }
        });
    }

    private void createTransaction(){
        DepositDto depositDto = new DepositDto(paymentAmount, card);
        wallet.getTransactions().add(depositDto);
        System.out.println(wallet.toString());
    }

    private void saveWallet() throws JsonProcessingException, JSONException {
        walletViewModel.saveWallet(wallet).observe(getViewLifecycleOwner(), new Observer<WalletDto>() {
            @Override
            public void onChanged(WalletDto walletDto) {
                if (walletDto != null) {
                    getDepositOk();
                }
            }
        });
    }

    private void updateWallet() throws JsonProcessingException, JSONException {
        walletViewModel.updateWallet(wallet).observe(getViewLifecycleOwner(), new Observer<WalletDto>() {
            @Override
            public void onChanged(WalletDto walletDto) {
                if (walletDto != null) {
                    getDepositOk();
                } else {
                    getDepositError();
                }
            }
        });
    }

    private void getDepositOk() {
        data = new Bundle();
        data.putInt("image", R.drawable.ic_check_circle_black_24dp);
        data.putString("title", "Deposito Realizado");
        data.putString("subtitle", "El deposito se ha realizado correctamente.");
        Navigation.findNavController(root).navigate(
                R.id.action_nav_wallet_deposit_detail_to_nav_wallet_deposit_result, data);
        Toast.makeText(root.getContext(),
                "El deposito se ha realizado correctamente",
                Toast.LENGTH_LONG).show();
    }

    private void getDepositError() {
        data = new Bundle();
        data.putInt("image", R.drawable.ic_warning_black_24dp);
        data.putString("title", "Hemos tenido un problema");
        data.putString("subtitle", "No se ha podido realizar el depósito.");
        Navigation.findNavController(root).navigate(
                R.id.action_nav_wallet_deposit_detail_to_nav_wallet_deposit_result, data);
        Toast.makeText(root.getContext(),
                "No se ha podido realizar el depósito.",
                Toast.LENGTH_LONG).show();
    }
}
