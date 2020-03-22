package com.movetto.services.user_services;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserCheckDatabaseService extends AsyncTask<String, String, String> {

    private Context context;
    private FirebaseUser user;
    private HttpURLConnection connection;

    public UserCheckDatabaseService(Context context) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder result = new StringBuilder();
        String uri = "http://localhost:8080/users/uid/";
        String userUid = user.getUid();
        try {
            URL url = new URL(uri + userUid);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                result.append(line);            }
        } catch (IOException e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            connection.disconnect();
        }
        return result.toString();
    }

    @Override
    protected void onPreExecute() {
        //Do Nothing
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, "El Usuario " + user.getDisplayName() + " está Registrado", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        //Do Nothing
    }

    private void jsonInsertLocal(){

    }
}
