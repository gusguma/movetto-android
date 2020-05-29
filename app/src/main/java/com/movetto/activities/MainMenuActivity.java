package com.movetto.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.movetto.R;
import com.movetto.view_models.PartnerViewModel;
import com.movetto.view_models.UserViewModel;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainMenuActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FirebaseUser user;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        user = FirebaseAuth.getInstance().getCurrentUser();
        System.out.println("El usuario es " + user.getUid());
        checkUserDatabase();
        actionBarBuilder();
        setmAppBarConfiguration();
        controllerBuilder();
        userDataUIBuilder();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void actionBarBuilder(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setmAppBarConfiguration(){
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_shipments_empty, R.id.nav_travel_empty, R.id.nav_packages,
                R.id.nav_travellers,R.id.nav_news, R.id.nav_account, R.id.nav_wallet)
                .setDrawerLayout(drawerLayout)
                .build();
    }

    private void controllerBuilder(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void userDataUIBuilder() {
        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_display_name);
        TextView userEmailTextView = headerView.findViewById(R.id.user_email);
        userNameTextView.setText(user.getDisplayName());
        userEmailTextView.setText(user.getEmail());
        getUserImage();
    }

    private void getUserImage() {
        //TODO
        new Thread(new Runnable() {
            String url;
            Bitmap avatarImage = null;
            ImageView imageView = navigationView
                    .getHeaderView(0)
                    .findViewById(R.id.user_avatar);
            {
                if (user.getPhotoUrl() != null)
                url = user.getPhotoUrl().toString();
            }
            public void run() {
                try {
                    URL aURL = new URL(url);
                    URLConnection conn = aURL.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    avatarImage = BitmapFactory.decodeStream(bis);
                    bis.close();
                    is.close();
                } catch (IOException e) {
                    Log.e("loadUserImageError", "Get Image User Bitmap error");
                }
                imageView.post(new Runnable() {
                    public void run() {
                        imageView.setImageBitmap(avatarImage);
                    }
                });
            }
        }).start();
    }

    public void closeSession(MenuItem item){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        getFirebaseUIActivity();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainMenuActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getFirebaseUIActivity(){
        startActivity(new Intent(this, FirebaseUIActivity.class));
        Toast.makeText(this, getString(R.string.session_closed),Toast.LENGTH_LONG).show();
        finish();
    }

    public void getSettingsActivity(MenuItem item){
        startActivity(new Intent(this, SettingsActivity.class));
        finish();
    }

    public void checkUserDatabase(){
        userViewModel = new ViewModelProvider(this).get(PartnerViewModel.class);
        userViewModel.readUser();
    }


}
