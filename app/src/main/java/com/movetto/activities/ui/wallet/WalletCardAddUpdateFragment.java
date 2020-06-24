package com.movetto.activities.ui.wallet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.movetto.R;
import com.movetto.dtos.CardDto;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.view_models.CardViewModel;
import com.movetto.view_models.CustomerViewModel;

import org.json.JSONException;

public class WalletCardAddUpdateFragment extends Fragment
        implements View.OnFocusChangeListener, View.OnClickListener{

    private View root;
    private CardViewModel cardViewModel;
    private CustomerViewModel customerViewModel;
    private EditText cardNumber;
    private EditText expireMonth;
    private EditText expireYear;
    private EditText cvc;
    private Bundle data;
    private UserDto customer;
    private CardDto card;
    private Button buttonSave;
    private Button buttonDelete;

    public WalletCardAddUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater, container);
        setComponents();
        setListeners();
        getBundleData();
        return root;
    }

    private void setViewModels() {
        cardViewModel = new ViewModelProvider(this).get(CardViewModel.class);
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_wallet_card_add_update, container, false);
    }

    private void setComponents() {
        cardNumber = root.findViewById(R.id.card_create_update_number_edit);
        expireMonth = root.findViewById(R.id.card_create_update_month_edit);
        expireYear = root.findViewById(R.id.card_create_update_year_edit);
        cvc = root.findViewById(R.id.card_create_update_cvc_edit);
        buttonSave = root.findViewById(R.id.card_create_update_save_button);
        buttonDelete = root.findViewById(R.id.card_create_update_delete_button);
        data = getArguments();
    }

    private void setListeners(){
        cardNumber.setOnFocusChangeListener(this);
        expireMonth.setOnFocusChangeListener(this);
        expireYear.setOnFocusChangeListener(this);
        cvc.setOnFocusChangeListener(this);
        buttonSave.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
    }

    private void getBundleData() {
        if (data != null && data.getInt("customerId") != 0){
            getCustomerData();
        }
        if (data != null && data.getInt("cardId") != 0){
            getCardData();
        }
    }

    private void getCustomerData(){
        customerViewModel.readCustomer().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                if (userDto != null) {
                    customer = userDto;
                }
            }
        });
    }

    private void getCardData(){
        cardViewModel.readCardById(data.getInt("cardId")).observe(getViewLifecycleOwner(), new Observer<CardDto>() {
            @Override
            public void onChanged(CardDto cardDto) {
                if (cardDto != null) {
                    card = cardDto;
                    setCardFormData();
                    buttonDelete.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setCardFormData(){
        cardNumber.setText(String.valueOf(card.getCardNumber()));
        expireMonth.setText(String.valueOf(card.getExpireMonth()));
        expireYear.setText(String.valueOf(card.getExpireYear()));
        cvc.setText(String.valueOf(card.getCvc()));
    }

    private void setCardData(){
        if (card == null) {
            card = new CardDto(
                    cardNumber.getText().toString(),
                    expireMonth.getText().toString(),
                    expireYear.getText().toString(),
                    cvc.getText().toString(),
                    customer
            );
        } else {
            card.setCardNumber(cardNumber.getText().toString());
            card.setExpireMonth(expireMonth.getText().toString());
            card.setExpireYear(expireYear.getText().toString());
            card.setCvc(cvc.getText().toString());
        }
    }

    private boolean isFormValidate(){
        boolean isValidate = true;
        EditText[] editTexts = new EditText[]{
                cardNumber,expireMonth,expireYear,cvc
        };
        for (EditText editText:editTexts) {
            String text = editText.getText().toString();
            CharSequence error = editText.getError();
            if (error != null || text.isEmpty())
                isValidate = false;
        }
        return isValidate;
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

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonSave.getId()) {
            try {
                setButtonSaveListener();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch ( JSONException e) {
                e.printStackTrace();
            }
        }
        if (v.getId() == buttonDelete.getId()) {
            try {
                setButtonDeleteListener();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch ( JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setButtonSaveListener() throws JsonProcessingException, JSONException {
        if (isFormValidate()) {
            if (card == null) {
                setCardData();
                saveCard();
            } else {
                setCardData();
                updateCard();
            }
        } else {
            Toast.makeText(root.getContext()
                    , "Verifique los datos del formulario", Toast.LENGTH_LONG).show();
        }
    }

    private void setButtonDeleteListener() throws JsonProcessingException, JSONException {
        cardViewModel.deleteCard(card).observe(getViewLifecycleOwner(), new Observer<CardDto>() {
            @Override
            public void onChanged(CardDto cardDto) {
                if (cardDto != null) {
                    getCardDeleteOk();
                } else {
                    getCardDeleteError();
                }
            }
        });
    }

    private void saveCard() throws JsonProcessingException, JSONException {
        cardViewModel.saveCard(card).observe(getViewLifecycleOwner(), new Observer<CardDto>() {
            @Override
            public void onChanged(CardDto cardDto) {
                if (cardDto != null) {
                    getCardSaveOk();
                } else {
                    getCardSaveError();
                }
            }
        });
    }

    private void updateCard() throws JsonProcessingException, JSONException {
        cardViewModel.updateCard(card).observe(getViewLifecycleOwner(), new Observer<CardDto>() {
            @Override
            public void onChanged(CardDto cardDto) {
                if (cardDto != null) {
                    getCardUpdateOk();
                } else {
                    getCardUpdateError();
                }
            }
        });
    }

    private void getCardSaveOk() {
        if (data != null && data.getInt("detail") == 1) {
            Navigation.findNavController(root).navigate(
                    R.id.action_nav_wallet_card_edit_to_nav_wallet_detail, data);
        } else {
            Navigation.findNavController(root).navigate(
                    R.id.action_nav_wallet_card_edit_to_nav_wallet_deposit_card, data);
        }
        Toast.makeText(root.getContext(),"Tarjeta Creada", Toast.LENGTH_LONG).show();
    }

    private void getCardSaveError() {
        if (data != null && data.getInt("detail") == 1) {
            Navigation.findNavController(root).navigate(
                    R.id.action_nav_wallet_card_edit_to_nav_wallet_detail, data);
        } else {
            Navigation.findNavController(root).navigate(
                    R.id.action_nav_wallet_card_edit_to_nav_wallet_deposit_card, data);
        }
        Toast.makeText(root.getContext(),"No se ha podido crear la tarjeta"
                ,Toast.LENGTH_LONG).show();

    }

    private void getCardUpdateOk() {
        if (data != null && data.getInt("detail") == 1) {
            Navigation.findNavController(root).navigate(
                    R.id.action_nav_wallet_card_edit_to_nav_wallet_detail, data);
        } else {
            Navigation.findNavController(root).navigate(
                    R.id.action_nav_wallet_card_edit_to_nav_wallet_deposit_card, data);
        }
        Toast.makeText(root.getContext(),
                "Tarjeta Actualizada",
                Toast.LENGTH_LONG).show();
    }

    private void getCardUpdateError() {
        if (data != null && data.getInt("detail") == 1) {
            Navigation.findNavController(root).navigate(
                    R.id.action_nav_wallet_card_edit_to_nav_wallet_detail, data);
        } else {
            Navigation.findNavController(root).navigate(
                    R.id.action_nav_wallet_card_edit_to_nav_wallet_deposit_card, data);
        }
        Toast.makeText(root.getContext(),
                "No se ha podido actualizar la tarjeta",
                Toast.LENGTH_LONG).show();
    }

    private void getCardDeleteOk() {
        if (data != null && data.getInt("detail") == 1) {
            Navigation.findNavController(root).navigate(
                    R.id.action_nav_wallet_card_edit_to_nav_wallet_detail, data);
        } else {
            Navigation.findNavController(root).navigate(
                    R.id.action_nav_wallet_card_edit_to_nav_wallet_deposit_card, data);
        }
        Toast.makeText(root.getContext(),
                "Tarjeta Eliminada",
                Toast.LENGTH_LONG).show();
    }

    private void getCardDeleteError() {
        if (data != null && data.getInt("detail") == 1) {
            Navigation.findNavController(root).navigate(
                    R.id.action_nav_wallet_card_edit_to_nav_wallet_detail, data);
        } else {
            Navigation.findNavController(root).navigate(
                    R.id.action_nav_wallet_card_edit_to_nav_wallet_deposit_card, data);
        }
        Toast.makeText(root.getContext(),
                "No se ha podido eliminar la tarjeta",
                Toast.LENGTH_LONG).show();
    }

}
