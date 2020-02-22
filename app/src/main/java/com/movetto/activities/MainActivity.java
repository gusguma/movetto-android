package com.movetto.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.movetto.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getFirebaseUIActivity(View view) {
        Intent intent = new Intent(this, FirebaseUIActivity.class);
        startActivity(intent);
        finish();
    }
}
