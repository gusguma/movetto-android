package com.movetto.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.movetto.R;
import com.movetto.dtos.PackageDto;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.ShipmentStatus;

import java.util.ArrayList;
import java.util.List;

public class ShipmentListAdapter
        extends RecyclerView.Adapter<ShipmentListAdapter.ShipmentListHolder> {

    private List<ShipmentDto> shipments = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ShipmentListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shipment_item,parent,false);
        return new ShipmentListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipmentListHolder holder, int position) {
        ShipmentDto currentShipment = shipments.get(position);
        holder.directionStart.setText(currentShipment.getStartDirection().getCity());
        holder.directionEnd.setText(currentShipment.getEndDirection().getCity());
        holder.directionStartSubtitle.setText(currentShipment.getStartDirection().getState());
        holder.directionEndSubtitle.setText(currentShipment.getEndDirection().getState());
        holder.packagesCount.setText(String.valueOf(currentShipment.getPackages().size()));
        holder.weightCount.setText(String.valueOf(getPackagesWeight(currentShipment)));
        holder.shipmentStatus.setText(getShipmentStatus(currentShipment));
    }

    @Override
    public int getItemCount() {
        return shipments.size();
    }

    private double getPackagesWeight(ShipmentDto shipmentDto){
        return shipmentDto.getPackages().stream().mapToDouble(PackageDto::getWeight).sum();
    }

    private String getShipmentStatus(ShipmentDto shipmentDto){
        if (shipmentDto.getStatus() == ShipmentStatus.ACCEPTED)
            return "Aceptado";
        if (shipmentDto.getStatus() == ShipmentStatus.COLLECTED)
            return "Recogido";
        if (shipmentDto.getStatus() == ShipmentStatus.DELETED)
            return "Borrado";
        if (shipmentDto.getStatus() == ShipmentStatus.DELIVERED)
            return "Entregado";
        if (shipmentDto.getStatus() == ShipmentStatus.DETAINED)
            return "Retenido";
        if (shipmentDto.getStatus() == ShipmentStatus.PREPARED)
            return "Preparado";
        if (shipmentDto.getStatus() == ShipmentStatus.SAVED)
            return "Grabado";
        if (shipmentDto.getStatus() == ShipmentStatus.TRANSIT)
            return "En Transito";
        return "";
    }

    public void setShipments(List<ShipmentDto> shipments) {
        this.shipments = shipments;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(ShipmentListAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    class ShipmentListHolder extends RecyclerView.ViewHolder {
        private TextView directionStart;
        private TextView directionEnd;
        private TextView directionStartSubtitle;
        private TextView directionEndSubtitle;
        private TextView packagesCount;
        private TextView weightCount;
        private TextView shipmentStatus;

        public ShipmentListHolder(@NonNull View itemView) {
            super(itemView);
            directionStart = itemView.findViewById(R.id.shipment_list_start);
            directionEnd = itemView.findViewById(R.id.shipment_list_end);
            directionStartSubtitle = itemView.findViewById(R.id.shipment_list_start_subtitle);
            directionEndSubtitle = itemView.findViewById(R.id.shipment_list_end_subtitle);
            packagesCount = itemView.findViewById(R.id.shipment_list_packages_count);
            weightCount = itemView.findViewById(R.id.shipment_list_weight_count);
            shipmentStatus = itemView.findViewById(R.id.shipment_list_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(shipments.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(ShipmentDto shipment);
    }
}
