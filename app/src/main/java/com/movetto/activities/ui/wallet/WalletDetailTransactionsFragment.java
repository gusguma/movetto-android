package com.movetto.activities.ui.wallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.movetto.R;
import com.movetto.adapters.WalletDetailTransactionAdapter;
import com.movetto.dtos.TransactionDto;
import com.movetto.dtos.WalletDto;
import com.movetto.view_models.WalletViewModel;

import java.util.ArrayList;
import java.util.List;

public class WalletDetailTransactionsFragment extends Fragment {

    private View root;
    private WalletViewModel walletViewModel;
    private WalletDetailTransactionAdapter adapter;
    private Bundle data;
    private TextView transactionEmptyText;
    private FloatingActionButton addDepositButton;

    public WalletDetailTransactionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setAddCardButtonListener();
        setObservers();
        return root;
    }

    private void setViewModels(){
        walletViewModel = new ViewModelProvider(this).get(WalletViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container){
        root = inflater.inflate(R.layout.fragment_wallet_detail_transactions
                , container, false);
    }

    private void setComponents(){
        RecyclerView recyclerView = root.findViewById(R.id.wallet_detail_transaction_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new WalletDetailTransactionAdapter();
        recyclerView.setAdapter(adapter);
        transactionEmptyText = root.findViewById(R.id.wallet_detail_transaction_empty);
        addDepositButton = root.findViewById(R.id.wallet_detail_add_deposit_button);
    }

    private void setAddCardButtonListener(){
        addDepositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(
                        R.id.action_nav_wallet_detail_to_nav_wallet_deposit_amount, data
                );
            }
        });
    }

    private int getWalletId(){
        data = getArguments();
        if (data != null && data.getInt("walletId") != 0){
            return data.getInt("walletId");
        }
        return 0;
    }

    private void setObservers(){
        walletViewModel.readWalletById(getWalletId()).observe(getViewLifecycleOwner(), new Observer<WalletDto>() {
            @Override
            public void onChanged(WalletDto walletDto) {
                if (walletDto != null) {
                    List<TransactionDto> transactions = new ArrayList<TransactionDto>(walletDto.getTransactions());
                    adapter.setTransactions(transactions);
                    transactionEmptyText.setVisibility(View.GONE);
                }
            }
        });
    }
}
