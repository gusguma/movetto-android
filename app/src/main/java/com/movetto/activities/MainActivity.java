package com.movetto.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.movetto.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkCurrentUser();
    }

    public void getFirebaseUIActivity(View view) {
        Intent intent = new Intent(this, FirebaseUIActivity.class);
        startActivity(intent);
        finish();
    }

    public void checkCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            getMainMenuActivity();
        } else {
            setContentView(R.layout.activity_main);
        }
    }

    private void getMainMenuActivity() {
        startActivity(new Intent(this, MainMenuActivity.class));
        finish();
    }
}
