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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.movetto.R;
import com.movetto.adapters.ShipmentDetailPackagesAdapter;
import com.movetto.adapters.WalletCardAdapter;
import com.movetto.adapters.WalletDetailCardAdapter;
import com.movetto.dtos.CardDto;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.WalletDto;
import com.movetto.view_models.CardViewModel;
import com.movetto.view_models.ShipmentViewModel;

import java.util.List;

public class WalletDetailCardsFragment extends Fragment {

    private View root;
    private CardViewModel cardViewModel;
    private WalletDetailCardAdapter adapter;
    private Bundle data;
    private TextView cardEmptyText;
    private FloatingActionButton addCardButton;

    public WalletDetailCardsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setAddCardButtonListener();
        setAdapterListener();
        setObservers();
        return root;
    }

    private void setViewModels(){
        cardViewModel = new ViewModelProvider(this).get(CardViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container){
        root = inflater.inflate(R.layout.fragment_wallet_detail_cards
                , container, false);
    }

    private void setComponents(){
        RecyclerView recyclerView = root.findViewById(R.id.wallet_detail_card_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new WalletDetailCardAdapter();
        recyclerView.setAdapter(adapter);
        cardEmptyText = root.findViewById(R.id.wallet_detail_card_empty);
        addCardButton = root.findViewById(R.id.wallet_detail_add_card_button);
    }

    private void setAddCardButtonListener(){
        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.putInt("cardId", 0);
                Navigation.findNavController(root).navigate(
                        R.id.action_nav_wallet_detail_to_nav_wallet_card_edit, data
                );
            }
        });
    }

    private void setAdapterListener(){
        adapter.setOnItemClickListener(new WalletDetailCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CardDto cardDto) {
                if (cardDto != null) {
                    data.putInt("cardId", cardDto.getId());
                    data.putInt("detail", 1);
                    Navigation.findNavController(root).navigate(
                            R.id.action_nav_wallet_detail_to_nav_wallet_card_edit, data);
                } else {
                    Toast.makeText(root.getContext()
                            , "Tarjeta No Encontrada", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private int getCustomerId(){
        assert getParentFragment() != null;
        data = getParentFragment().getArguments();
        if (data != null && data.getInt("customerId") != 0){
            return data.getInt("customerId");
        }
        return 0;
    }

    private void setObservers(){
        cardViewModel.readCardsByUserId(getCustomerId()).observe(getViewLifecycleOwner(), new Observer<List<CardDto>>() {
            @Override
            public void onChanged(List<CardDto> cardDtos) {
                if (!cardDtos.isEmpty()) {
                    adapter.setCards(cardDtos);
                    cardEmptyText.setVisibility(View.GONE);
                }
            }
        });
    }
}
