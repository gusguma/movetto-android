package com.movetto.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.movetto.R;
import com.movetto.dtos.PackageDto;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class ShipmentDetailPackagesAdapter
        extends RecyclerView.Adapter<ShipmentDetailPackagesAdapter
        .ShipmentDetailPackagesHolder> {

    private static final int PACKAGE_HASH = 273;

    private List<PackageDto> packages = new ArrayList<PackageDto>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ShipmentDetailPackagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.package_item, parent,false);

        return new ShipmentDetailPackagesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShipmentDetailPackagesHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("0.00");
        PackageDto packageDto = packages.get(position);
        holder.packageNumber.setText(encodeShipmentNumber(packageDto.getId()));
        holder.packageVolume.setText(df.format(packageDto.getPackageVolume()));
        holder.packageWeight.setText(df.format(packageDto.getWeight()));
    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    private String encodeShipmentNumber(int id){
        Formatter formatter = new Formatter();
        int number = (id * PACKAGE_HASH) - 41 ;
        return "" + formatter.format("%011d", number);
    }

    public void setPackages(List<PackageDto> packages) {
        this.packages = packages;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    class ShipmentDetailPackagesHolder extends RecyclerView.ViewHolder {

        private TextView packageNumber;
        private TextView packageWeight;
        private TextView packageVolume;

        public ShipmentDetailPackagesHolder(@NonNull View itemView) {
            super(itemView);
            packageNumber = itemView.findViewById(R.id.packages_list_number);
            packageWeight = itemView.findViewById(R.id.packages_list_weight);
            packageVolume = itemView.findViewById(R.id.packages_list_volume);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(packages.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(PackageDto packageDto);
    }
}
