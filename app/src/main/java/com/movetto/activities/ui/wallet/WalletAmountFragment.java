package com.movetto.activities.ui.wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.movetto.R;
import com.movetto.dtos.validations.ErrorStrings;

public class WalletAmountFragment extends Fragment
        implements View.OnFocusChangeListener, View.OnClickListener {

    private View root;
    private EditText amount;
    private Button buttonContinue;

    public WalletAmountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setLayout(inflater, container);
        setComponents();
        setFormFieldsListener();
        return root;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_wallet_amount, container, false);
    }

    private void setComponents() {
        amount = root.findViewById(R.id.wallet_deposit_amount_edit);
        buttonContinue = root.findViewById(R.id.wallet_deposit_amount_button);
    }

    private void setFormFieldsListener() {
        buttonContinue.setOnClickListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;
        if (editText.hasFocus()){
            editText.setError(null);
        } else {
            if(editText.getText().toString().isEmpty()){
                editText.setError(ErrorStrings.EMPTY);
            }
        }
    }

    private boolean isFormValidate(){
        boolean isValidate = true;
        if (amount.getText().toString().isEmpty())
            isValidate = false;
        return isValidate;
    }

    private void setBundle(){
        Bundle data = new Bundle();
        double amountForm = Double.parseDouble(amount.getText().toString());
        data.putDouble("amount", amountForm);
        Navigation.findNavController(root).navigate(
                R.id.action_nav_wallet_deposit_amount_to_nav_wallet_deposit_card, data);
    }

    @Override
    public void onClick(View v) {
        if (isFormValidate()) {
            setBundle();
        } else {
            Toast.makeText(root.getContext()
                    , "Verifique los datos del formulario", Toast.LENGTH_LONG).show();
        }
    }
}
