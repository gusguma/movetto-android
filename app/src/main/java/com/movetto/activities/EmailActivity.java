package com.movetto.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.auth.AuthUI;
import com.movetto.R;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class EmailActivity extends AppCompatActivity {

    List<AuthUI.IdpConfig> providers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        getProviders();
    }

    private boolean emailValidate(String email){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public void goPrivacyActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void continueButtonListener(Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailField = findViewById(R.id.email_field);
                String emailFieldText = emailField.getText().toString();
                if (!emailValidate(emailFieldText) || emailFieldText.equals("")){
                    emailField.setError(getString(R.string.email_validate_ko));
                }
            }
        });
    }

    private void privacyButtonListener(Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPrivacyActivity(v);
            }
        });
    }

    private void getProviders(){
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

    }
}