package com.movetto.adapters;

import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.movetto.R;
import com.movetto.dtos.BikeDto;
import com.movetto.dtos.CarDto;
import com.movetto.dtos.MotorcycleDto;
import com.movetto.dtos.VanDto;
import com.movetto.dtos.VehicleDto;

import java.util.ArrayList;
import java.util.List;

public class AccountPartnerVehiclesAdapter extends
        RecyclerView.Adapter<AccountPartnerVehiclesAdapter.VehicleHolder> {

    private List<VehicleDto> vehicleDtoList = new ArrayList<>();

    @NonNull
    @Override
    public VehicleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicle_item, parent,false);
        return new VehicleHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleHolder holder, int position) {
        VehicleDto vehicle = vehicleDtoList.get(position);
        if (vehicle.getClass() == BikeDto.class)
            setBike(vehicle, holder);
        if (vehicle.getClass() == CarDto.class)
            setCar(vehicle, holder);
        if (vehicle.getClass() == MotorcycleDto.class)
            setMotorcycle(vehicle, holder);
        if (vehicle.getClass() == VanDto.class)
            setVan(vehicle, holder);
        holder.name.setText(vehicle.getName());
    }

    @Override
    public int getItemCount() {
        return vehicleDtoList.size();
    }

    private void setBike(VehicleDto vehicleDto, VehicleHolder holder){
        BikeDto bike = (BikeDto) vehicleDto;
        holder.icon.setImageResource(R.drawable.ic_directions_bike_black_24dp);
        holder.makeModel.setText(bike.getMake());
    }

    private void setCar(VehicleDto vehicleDto, VehicleHolder holder){
        CarDto car = (CarDto) vehicleDto;
        String makeModel = car.getMake() + " " + car.getModel();
        holder.icon.setImageResource(R.drawable.ic_directions_car_black_24dp);
        holder.makeModel.setText(makeModel);
    }

    private void setMotorcycle(VehicleDto vehicleDto, VehicleHolder holder){
        MotorcycleDto motorcycle = (MotorcycleDto) vehicleDto;
        String makeModel = motorcycle.getMake() + " " + motorcycle.getModel();
        holder.icon.setImageResource(R.drawable.ic_directions_motorcycle_black_24dp);
        holder.makeModel.setText(makeModel);
    }

    private void setVan(VehicleDto vehicleDto, VehicleHolder holder){
        VanDto van = (VanDto) vehicleDto;
        String makeModel = van.getMake() + " " + van.getModel();
        holder.icon.setImageResource(R.drawable.ic_directions_bus_black_24dp);
        holder.makeModel.setText(makeModel);
    }

    public void setVehicles(List<VehicleDto> vehicleDtoList) {
        this.vehicleDtoList = vehicleDtoList;
        notifyDataSetChanged();
    }

    public class VehicleHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView name;
        private TextView makeModel;

        public VehicleHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.account_partner_vehicles_icon);
            name = itemView.findViewById(R.id.account_partner_vehicles_title);
            makeModel = itemView.findViewById(R.id.account_partner_vehicles_subtitle);
        }
    }
}
