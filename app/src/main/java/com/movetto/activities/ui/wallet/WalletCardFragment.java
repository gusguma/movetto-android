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
import com.movetto.adapters.WalletCardAdapter;
import com.movetto.dtos.CardDto;
import com.movetto.view_models.CardViewModel;

import java.util.List;

public class WalletCardFragment extends Fragment {

    private View root;
    private CardViewModel cardViewModel;
    private WalletCardAdapter adapter;
    private Bundle data;
    private TextView cardEmptyText;
    private Button continueButton;
    private FloatingActionButton addCardButton;

    public WalletCardFragment() {
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
        root = inflater.inflate(R.layout.fragment_wallet_card
                , container, false);
    }

    private void setComponents(){
        data = getArguments();
        RecyclerView recyclerView = root.findViewById(R.id.wallet_deposit_card_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new WalletCardAdapter();
        recyclerView.setAdapter(adapter);
        cardEmptyText = root.findViewById(R.id.wallet_deposit_card_empty);
        continueButton = root.findViewById(R.id.wallet_deposit_card_button);
        addCardButton = root.findViewById(R.id.wallet_deposit_add_card_button);
    }

    private void setAddCardButtonListener(){
        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.putInt("cardId", 0);
                Navigation.findNavController(root).navigate(
                        R.id.action_nav_wallet_deposit_card_to_nav_wallet_card_edit, data
                );
            }
        });
    }

    private void setContinueButtonListener(){
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBundle();
            }
        });
    }

    private void setAdapterListener(){
        adapter.setOnItemClickListener(new WalletCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CardDto cardDto) {
                if (cardDto != null) {
                    data.putInt("cardId", cardDto.getId());
                    Navigation.findNavController(root).navigate(
                            R.id.action_nav_wallet_deposit_card_to_nav_wallet_card_edit, data);
                } else {
                    Toast.makeText(root.getContext()
                            , "Debe seleccionar una Tarjeta", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setBundle(){
        CardDto card = adapter.getCardSelected();
        if (card != null) {
            data.putInt("cardId", card.getId());
            System.out.println(data.getInt("cardId"));
            Navigation.findNavController(root).navigate(
                    R.id.action_nav_wallet_deposit_card_to_nav_wallet_deposit_detail, data);
        } else {
            Toast.makeText(root.getContext()
                    , "Debe seleccionar una Tarjeta", Toast.LENGTH_LONG).show();
        }
    }

    private void setObservers(){
        cardViewModel.readCardsByUserId(data.getInt("customerId")).observe(getViewLifecycleOwner(), new Observer<List<CardDto>>() {
            @Override
            public void onChanged(List<CardDto> cardDtos) {
                if (!cardDtos.isEmpty()) {
                    adapter.setCards(cardDtos);
                    cardEmptyText.setVisibility(View.GONE);
                    setContinueButtonListener();
                    continueButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}
