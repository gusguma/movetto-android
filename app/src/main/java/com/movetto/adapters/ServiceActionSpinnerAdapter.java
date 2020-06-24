package com.movetto.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.movetto.R;
import com.movetto.dtos.BikeDto;
import com.movetto.dtos.CarDto;
import com.movetto.dtos.MotorcycleDto;
import com.movetto.dtos.VanDto;
import com.movetto.dtos.VehicleDto;

import java.util.ArrayList;
import java.util.List;

public class ServiceActionSpinnerAdapter extends BaseAdapter {

    private static final String BIKE = "Bicicleta";
    private static final String CAR = "Coche";
    private static final String MOTORCYCLE = "Motocicleta";
    private static final String VAN = "Furgoneta";

    private VehicleHolder holder;
    private List<VehicleDto> vehicles = new ArrayList<VehicleDto>();
    private VehicleDto vehicle;
    private Context context;

    public ServiceActionSpinnerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        vehicle = vehicles.get(position);
        View vehicleView = convertView;
        if (vehicleView == null) {
            vehicleView = LayoutInflater.from(context)
                    .inflate(R.layout.vehicle_spinner_item,parent,false);
            holder = new VehicleHolder();
            holder.textVehicle = vehicleView.findViewById(R.id.vehicle_spinner_item);
            vehicleView.setTag(holder);
        } else {
            holder = (VehicleHolder) vehicleView.getTag();
        }
        getViewData();
        return vehicleView;
    }

    private void getViewData(){
        checkVehicleType();
        holder.textVehicle.setText(checkVehicleType());
    }

    public void setVehicles(List<VehicleDto> vehicles){
        this.vehicles = vehicles;
        notifyDataSetChanged();
    }

    public VehicleDto getVehicle() {
        return vehicle;
    }

    private String checkVehicleType(){
        if (vehicle.getClass() == BikeDto.class) {
            BikeDto bike = (BikeDto) vehicle;
            return BIKE + " - " + bike.getMake();
        }
        if (vehicle.getClass() == CarDto.class) {
            CarDto car = (CarDto) vehicle;
            return CAR + " - " + car.getMake() + " - "
                    + car.getModel() + " - " + car.getRegistration();
        }
        if (vehicle.getClass() == MotorcycleDto.class) {
            MotorcycleDto motorcycle = (MotorcycleDto) vehicle;
            return MOTORCYCLE + " - " + motorcycle.getMake()
                    + " - " + motorcycle.getModel() + motorcycle.getRegistration();
        }
        if (vehicle.getClass() == VanDto.class) {
            VanDto van = (VanDto) vehicle;
            return VAN + " - " + van.getMake() + " - " + " - " + van.getRegistration();
        }
        return null;
    }

    @Override
    public int getCount() {
        return vehicles.size();
    }

    @Override
    public Object getItem(int position) {
        return vehicles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class VehicleHolder {
        private TextView textVehicle;
    }
}
