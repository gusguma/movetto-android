package com.movetto.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.movetto.R;
import com.movetto.dtos.CardDto;

import java.util.ArrayList;
import java.util.List;

public class WalletDetailCardAdapter
        extends RecyclerView.Adapter<WalletDetailCardAdapter.WalletDetailCardHolder>{

    private List<CardDto> cards = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public WalletDetailCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_detail_item,parent,false);
        return new WalletDetailCardAdapter.WalletDetailCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletDetailCardHolder holder, int position) {
        CardDto cardDto = cards.get(position);
        String cardExpire = cardDto.getExpireMonth() + "/" + cardDto.getExpireYear();
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

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    class WalletDetailCardHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView cardNumber;
        private TextView cardExpire;

        public WalletDetailCardHolder(@NonNull View itemView) {
            super(itemView);
            cardNumber = itemView.findViewById(R.id.card_detail_item_number);
            cardExpire = itemView.findViewById(R.id.card_detail_item_expirate_year);
            setListeners();
        }

        private void setListeners(){
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (listener != null && position != RecyclerView.NO_POSITION){
                listener.onItemClick(cards.get(position));
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(CardDto cardDto);
    }
}
