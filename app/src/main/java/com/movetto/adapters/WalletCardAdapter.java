package com.movetto.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.movetto.R;
import com.movetto.dtos.CardDto;

import java.util.ArrayList;
import java.util.List;

public class WalletCardAdapter
        extends RecyclerView.Adapter<WalletCardAdapter.WalletDepositCardHolder> {

    private List<CardDto> cards = new ArrayList<>();
    private OnItemClickListener listener;
    private int lastPosition = -1;

    @NonNull
    @Override
    public WalletDepositCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item,parent,false);
        return new WalletDepositCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletDepositCardHolder holder, int position) {
        CardDto cardDto = cards.get(position);
        String cardExpire = cardDto.getExpireMonth() + "/" + cardDto.getExpireYear();
        holder.radioButton.setChecked(lastPosition == position);
        holder.cardNumber.setText(cardDto.getCardNumber());
        holder.cardExpire.setText(cardExpire);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void setCards (List<CardDto> cards){
        this.cards = cards;
        notifyDataSetChanged();
    }

    public CardDto getCardSelected(){
        if (lastPosition != -1) {
            return cards.get(lastPosition);
        } else {
            return null;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    class WalletDepositCardHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView cardNumber;
        private TextView cardExpire;
        private RadioButton radioButton;

        public WalletDepositCardHolder(@NonNull View itemView) {
            super(itemView);
            cardNumber = itemView.findViewById(R.id.card_item_number);
            cardExpire = itemView.findViewById(R.id.card_item_expirate_year);
            radioButton = itemView.findViewById(R.id.card_item_radio_button);
            setListeners();
        }

        private void setListeners(){
            radioButton.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == itemView.getId()) {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION){
                    listener.onItemClick(cards.get(position));
                }
            }
            if (v.getId() == radioButton.getId()) {
                lastPosition = getAdapterPosition();
                notifyDataSetChanged();
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(CardDto cardDto);
    }
}
