package com.movetto.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.AuthMethodPickerLayout;

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.movetto.R;
import com.movetto.services.user_services.UserCheckDatabaseService;

import java.util.Arrays;
import java.util.List;

public class FirebaseUIActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    AuthMethodPickerLayout customLayout;
    List<AuthUI.IdpConfig> providers;
    ActionCodeSettings actionCodeSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomLayout();
        setActionCodeSettings();
        setProviders();
        createSignInIntent();
    }

    public void createSignInIntent() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAuthMethodPickerLayout(customLayout)
                        .setAvailableProviders(providers)
                        .setTosAndPrivacyPolicyUrls(
                                "https://example.com/terms.html",
                                "https://example.com/privacy.html")
                        .setIsSmartLockEnabled(false)
                        .setTheme(R.style.Movetto_FirebaseUI)
                        .build()
                ,RC_SIGN_IN);
    }

    private void setCustomLayout(){
        customLayout = new AuthMethodPickerLayout
                .Builder(R.layout.activity_firebase_ui)
                .setGoogleButtonId(R.id.button_google)
                .setEmailButtonId(R.id.button_email)
                .setTosAndPrivacyPolicyId(R.id.privacy_policy)
                .build();
    }

    private void setActionCodeSettings(){
        actionCodeSettings = ActionCodeSettings.newBuilder()
                .setAndroidPackageName("com.movetto", true,"12")
                .setHandleCodeInApp(true)
                .setUrl("http://movetto.com")
                .build();
    }

    private void setProviders(){
        providers = Arrays.asList(
                new AuthUI.IdpConfig
                        .EmailBuilder()
                        .enableEmailLinkSignIn()
                        .setActionCodeSettings(actionCodeSettings)
                        .build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                checkUserDatabase();
                getEmailActivity();
            } else {
                getMainActivity();
            }
        }
    }

    public void getMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void getEmailActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void checkUserDatabase(){
        Toast.makeText(this,"Comprobando Usuario", Toast.LENGTH_SHORT).show();
        UserCheckDatabaseService userCheckDatabaseService = new UserCheckDatabaseService(this);
        userCheckDatabaseService.execute();

    }


}
