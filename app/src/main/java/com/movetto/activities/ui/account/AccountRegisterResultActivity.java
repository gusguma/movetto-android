package com.movetto.activities.ui.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.movetto.R;
import com.movetto.activities.FirebaseUIActivity;
import com.movetto.activities.MainActivity;
import com.movetto.activities.MainMenuActivity;

public class AccountRegisterResultActivity extends AppCompatActivity {

    private Bundle bundle;
    private ImageView image;
    private TextView title;
    private TextView subtitle;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register_result);
        setComponents();
        setListeners();
    }

    private void setComponents(){
        bundle = getIntent().getExtras();
        if (bundle != null){
            image = findViewById(R.id.account_register_result_img);
            image.setImageResource(bundle.getInt("image"));
            title = findViewById(R.id.account_register_result_title);
            title.setText(bundle.getString("title"));
            subtitle = findViewById(R.id.account_register_result_subtitle);
            subtitle.setText(bundle.getString("subtitle"));
        }
        button = findViewById(R.id.account_register_result_continue_button);
    }

    private void setListeners(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainMenuActivity();
            }
        });
    }

    private void goMainMenuActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
