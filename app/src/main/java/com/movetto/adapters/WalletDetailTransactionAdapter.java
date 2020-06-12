package com.movetto.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.movetto.R;
import com.movetto.dtos.CardDto;
import com.movetto.dtos.DepositDto;
import com.movetto.dtos.PaymentDto;
import com.movetto.dtos.TransactionDto;
import com.movetto.dtos.TransactionStatus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class WalletDetailTransactionAdapter
        extends RecyclerView.Adapter<WalletDetailTransactionAdapter.WalletDetailTransactionHolder>{

    private List<TransactionDto> transactions = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public WalletDetailTransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item,parent,false);
        return new WalletDetailTransactionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletDetailTransactionHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        TransactionDto transactionDto = transactions.get(position);
        holder.transactionIcon.setImageResource(setTransactionIcon(transactionDto));
        holder.transactionType.setText(setTransactionChip(transactionDto));
        holder.transactionAmount.setText(
                decimalFormat.format(Math.abs(transactionDto.getAmount())));
        holder.transactionText.setText(setTransactionText(transactionDto));
        holder.transactionDate.setText(setTransactionDate(transactionDto));
        holder.transactionStatus.setText(transactionDto.getStatus().toString());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void setTransactions(List<TransactionDto> transactions){
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    private int setTransactionIcon(TransactionDto transactionDto){
        if (transactionDto.getClass() == PaymentDto.class) {
            return R.drawable.ic_file_upload_black_24dp;
        }
        if (transactionDto.getClass() == DepositDto.class) {
            return R.drawable.ic_file_download_black_24dp;
        }
        return 0;
    }

    private String setTransactionChip(TransactionDto transactionDto){
        if (transactionDto.getClass() == PaymentDto.class) {
            return "Pago";
        }
        if (transactionDto.getClass() == DepositDto.class) {
            return "Deposito";
        }
        return null;
    }

    private String setTransactionText(TransactionDto transaction){
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return setTransactionChip(transaction)
                + " - "
                + transaction.getRegistrationDate().getDayOfMonth()
                + "-" + transaction.getRegistrationDate().getMonth()
                + "-" + transaction.getRegistrationDate().getYear()
                + " | " + decimalFormat.format(transaction.getAmount()) + "€.";
    }

    private String setTransactionDate(TransactionDto transaction){
        return transaction.getRegistrationDate().getDayOfMonth()
                + "-" + transaction.getRegistrationDate().getMonth()
                + "-" + transaction.getRegistrationDate().getYear();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    class WalletDetailTransactionHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private ImageView transactionIcon;
        private Chip transactionType;
        private TextView transactionAmount;
        private TextView transactionText;
        private TextView transactionDate;
        private TextView transactionStatus;

        public WalletDetailTransactionHolder(@NonNull View itemView) {
            super(itemView);
            transactionIcon = itemView.findViewById(R.id.wallet_detail_transaction_icon);
            transactionType = itemView.findViewById(R.id.wallet_detail_transaction_chip);
            transactionAmount = itemView.findViewById(R.id.wallet_detail_transaction_amount);
            transactionText = itemView.findViewById(R.id.wallet_detail_transaction_text);
            transactionDate = itemView.findViewById(R.id.wallet_detail_transaction_time);
            transactionStatus = itemView.findViewById(R.id.wallet_detail_transaction_status);
            setListeners();
        }

        private void setListeners(){
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (listener != null && position != RecyclerView.NO_POSITION){
                listener.onItemClick(transactions.get(position));
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(TransactionDto transactionDto);
    }
}
