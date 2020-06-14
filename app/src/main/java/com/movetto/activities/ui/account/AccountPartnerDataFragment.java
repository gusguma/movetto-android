package com.movetto.activities.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.movetto.R;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.validations.ErrorStrings;
import com.movetto.view_models.PartnerViewModel;

import java.util.Objects;

public class AccountPartnerDataFragment extends Fragment
        implements View.OnFocusChangeListener,View.OnClickListener {

    private View root;
    private PartnerViewModel partnerViewModel;
    private EditText displayName;
    private EditText email;
    private EditText phone;
    private EditText partnerId;
    private UserDto userOutputDto;
    private FloatingActionButton buttonSave;
    private Boolean response;

    public AccountPartnerDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setViewModels();
        setLayout(inflater,container);
        setComponents();
        setFormFieldsListener();
        setUserDataInput();
        return root;
    }

    private void setViewModels() {
        partnerViewModel = new ViewModelProvider(this).get(PartnerViewModel.class);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        root = inflater.inflate(R.layout.fragment_account_partner_data, container, false);
    }

    private void setComponents() {
        displayName = root.findViewById(R.id.account_partner_name_edit);
        email = root.findViewById(R.id.account_partner_email_edit);
        email.setEnabled(false);
        phone = root.findViewById(R.id.account_partner_phone_edit);
        phone.setEnabled(false);
        partnerId = root.findViewById(R.id.account_partner_id_edit);
        partnerId.setEnabled(false);
        buttonSave = root.findViewById(R.id.account_partner_data_save_button);
    }

    private void setFormFieldsListener() {
        displayName.setOnFocusChangeListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void setUserDataInput(){
        partnerViewModel.readPartner().observe(getViewLifecycleOwner(), new Observer<UserDto>() {
            @Override
            public void onChanged(UserDto userDto) {
                userOutputDto = userDto;
                displayName.setText(userDto.getDisplayName());
                email.setText(userDto.getEmail());
                phone.setText((String)userDto.getPhone());
                partnerId.setText(userDto.getPartner().getPartnerId());
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText editText = (EditText) v;
        if (editText.hasFocus())
            editText.setError(null);
        if(editText.getText().toString().isEmpty()){
            editText.setError(ErrorStrings.EMPTY);
        }
    }

    private boolean isFormValidate(){
        boolean isValidate = true;
        String text = displayName.getText().toString();
        CharSequence error = displayName.getError();
        if (error != null || text.isEmpty())
            isValidate = false;
        return isValidate;
    }

    private void setPartnerDataOutput() {
        userOutputDto.setDisplayName(displayName.getText().toString());
    }

    @Override
    public void onClick(View v) {
        try {
            setPartnerDataOutput();
            if (isFormValidate()){
                setResponseResult();
            } else {
                Toast.makeText(root.getContext()
                        ,"Verifique los datos del formulario",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setResponseResult() throws Exception {
        partnerViewModel.updatePartner(userOutputDto).observe(getViewLifecycleOwner(),
                new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean responseResult) {
                        response = responseResult;
                        if (response) {
                            getRegisterOkFragment();
                        } else {
                            getRegisterErrorFragment();
                        }
                    }
                });
    }

    private void getRegisterOkFragment() {
        Bundle data = new Bundle();
        data.putInt("image", R.drawable.ic_check_circle_black_24dp);
        data.putString("title", "Socio Actualizado");
        data.putString("subtitle", "La cuenta de Socio se ha actualizado correctamente.");
        Navigation.findNavController(root).navigate(
                R.id.action_nav_account_partner_to_nav_account_reg_result, data);
        Toast.makeText(root.getContext(),
                "Socio Actualizado",
                Toast.LENGTH_LONG).show();
        Objects.requireNonNull(getActivity()).finish();
    }

    private void getRegisterErrorFragment() {
        Bundle data = new Bundle();
        data.putInt("image", R.drawable.ic_warning_black_24dp);
        data.putString("title", "Hemos tenido un problema");
        data.putString("subtitle", "La cuenta de Socio no se ha podido actualizar.");
        Navigation.findNavController(root).navigate(
                R.id.action_nav_account_partner_to_nav_account_reg_result, data);
        Toast.makeText(root.getContext(),
                "Socio No Actualizado",
                Toast.LENGTH_LONG).show();
        Objects.requireNonNull(getActivity()).finish();
    }
}
