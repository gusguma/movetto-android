package com.movetto.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.movetto.R;
import com.movetto.dtos.DirectionDto;
import com.movetto.dtos.TravelDto;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.TravelListHolder>{

    private List<TravelDto> travels = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TravelListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travel_item,parent,false);
        return new TravelListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelListHolder holder, int position) {
        TravelDto travel = travels.get(position);
        holder.directionStart.setText(travel.getStartDirection().getStreet());
        holder.directionEnd.setText(travel.getEndDirection().getStreet());
        holder.directionStartSubtitle.setText(setDirectionSubtitle(travel.getStartDirection()));
        holder.directionEndSubtitle.setText(setDirectionSubtitle(travel.getEndDirection()));
        holder.people.setText(String.valueOf(travel.getPeople()));
        holder.date.setText(setDate(travel.getStart()));
        holder.time.setText(setTime(travel.getStart()));
        holder.price.setText(decimalFormat(travel.getPriceTravel()));
    }

    @Override
    public int getItemCount() {
        return travels.size();
    }

    private String setDirectionSubtitle(DirectionDto direction){
        return direction.getPostalCode() + " | "
                + direction.getCity() + " | "
                + direction.getState();
    }

    private String setDate(LocalDateTime date) {
        return dateFormat(date.getDayOfMonth()) + "/"
                        + dateFormat(date.getMonthValue()) + "/"
                        + dateFormat(date.getYear());
    }

    private String setTime(LocalDateTime date) {
        return dateFormat(date.getHour()) + ":"
                + dateFormat(date.getMinute());
    }

    private String dateFormat(int time) {
        return (time < 10) ? "0" + time : String.valueOf(time);
    }

    private String decimalFormat(double number){
        return new DecimalFormat("0.00").format(number);
    }

    public void setTravels(List<TravelDto> travels) {
        this.travels = travels;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    class TravelListHolder extends RecyclerView.ViewHolder {
        private TextView directionStart;
        private TextView directionEnd;
        private TextView directionStartSubtitle;
        private TextView directionEndSubtitle;
        private TextView people;
        private TextView date;
        private TextView time;
        private TextView price;

        public TravelListHolder(@NonNull View itemView) {
            super(itemView);
            directionStart = itemView.findViewById(R.id.travel_item_start);
            directionEnd = itemView.findViewById(R.id.travel_item_end);
            directionStartSubtitle = itemView.findViewById(R.id.travel_item_start_subtitle);
            directionEndSubtitle = itemView.findViewById(R.id.travel_item_end_subtitle);
            people = itemView.findViewById(R.id.travel_item_people);
            date = itemView.findViewById(R.id.travel_item_date);
            time = itemView.findViewById(R.id.travel_item_time);
            price = itemView.findViewById(R.id.travel_item_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(travels.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(TravelDto travel);
    }
}
