package com.movetto.states;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.navigation.Navigation;

import com.google.android.material.chip.Chip;
import com.movetto.R;
import com.movetto.dtos.ShipmentDto;
import com.movetto.dtos.ShipmentStatus;

import java.util.Formatter;

public class ShipmentContext {

    private static final int SHIPMENT_HASH = 213;
    protected static final int SHIPMENT = 1;

    private ShipmentState shipmentState;
    protected View root;
    protected Button greenButton;
    protected Button redButton;
    protected Chip chipStatus;
    protected ShipmentDto shipment;
    protected Bundle data;

    public ShipmentContext(ShipmentDto shipment, View root) {
        this.shipment = shipment;
        this.root = root;
        setComponents();
        setShipmentState();
    }

    private void setComponents(){
        chipStatus = root.findViewById(R.id.shipment_available_detail_status);
        greenButton = root.findViewById(R.id.shipment_available_detail_green_button);
        redButton = root.findViewById(R.id.shipment_available_detail_red_button);
        setBundle();
    }

    public void setShipmentState() {
        if (shipment.getStatus() == ShipmentStatus.SAVED) {
            shipmentState = new ShipmentSaved(this);
        }
        if (shipment.getStatus() == ShipmentStatus.PAID) {
            shipmentState = new ShipmentPaid(this);
        }
        if (shipment.getStatus() == ShipmentStatus.ACCEPTED) {
            shipmentState = new ShipmentAccepted(this);
        }
        if (shipment.getStatus() == ShipmentStatus.COLLECTED) {
            shipmentState = new ShipmentCollected(this);
        }
        if (shipment.getStatus() == ShipmentStatus.TRANSIT) {
            shipmentState = new ShipmentTransit(this);
        }
        if (shipment.getStatus() == ShipmentStatus.DELIVERED) {
            shipmentState = new ShipmentDelivered(this);
        }
        if (shipment.getStatus() == ShipmentStatus.DETAINED) {
            shipmentState = new ShipmentDetained(this);
        }
        if (shipment.getStatus() == ShipmentStatus.FINISHED) {
            shipmentState = new ShipmentFinished(this);
        }
    }

    protected String encodeShipmentNumber(){
        Formatter formatter = new Formatter();
        int number = (shipment.getId() * SHIPMENT_HASH) - 23 ;
        return "" + formatter.format("%08d", number);
    }

    private void setBundle(){
        data = new Bundle();
        data.putString("serviceNumber", encodeShipmentNumber());
        data.putDouble("amount", shipment.getPriceShipment());
        data.putInt("serviceId", shipment.getId());
        data.putInt("shipmentId", shipment.getId());
        data.putInt("serviceType", SHIPMENT);
    }

    protected void navigate(){
        Navigation.findNavController(root).navigate(
                R.id.action_nav_shipment_available_detail_to_nav_shipment_available_action_detail, data);
    }
}
