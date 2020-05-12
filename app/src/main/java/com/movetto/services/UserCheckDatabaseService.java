package com.movetto.services;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.UserMinimumDto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UserCheckDatabaseService extends AsyncTask<String, String, String> {

    private static final String URL_USER_RESOURCE = "http://192.168.1.60:8080/users/";

    private FirebaseUser user;
    private RequestQueue queue;
    private Context context;
    private ObjectMapper mapper;
    private UserDto userDto;

    public UserCheckDatabaseService(final Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Toast.makeText(context,"Comprobando Usuario", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {
        queue = Volley.newRequestQueue(context);
        checkUser();
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    private void checkUser(){
        String uri = URL_USER_RESOURCE + user.getUid();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, uri,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            UserDto userDatabase = mapper.readValue(response.toString(), UserDto.class);
                            System.out.println(userDatabase.getDisplayName() + " " + userDatabase.getEmail() + " " + userDatabase.getUid());
                            Toast.makeText(context,"Usuario Encontrado", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Usuario no encontrado " + user.getUid());
                try {
                    saveUser();
                } catch (JSONException | JsonProcessingException e) {
                    e.printStackTrace();
                }
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    private void saveUser() throws JSONException, JsonProcessingException {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL_USER_RESOURCE ,userRequest(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            UserDto userDatabase = mapper.readValue(response.toString(), UserDto.class);
                            System.out.println(userDatabase.getDisplayName() + " " + userDatabase.getEmail() + " " + userDatabase.getUid());
                            Toast.makeText(context,"Usuario Encontrado", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Usuario no encontrado " + user.getUid());
                error.printStackTrace();
            }
        });
        queue.add(request);
        System.out.println("Usuario Guardado " + queue.toString());
    }

    private JSONObject userRequest () throws JSONException, JsonProcessingException {
        UserMinimumDto userMinimumDto = new UserMinimumDto(user.getDisplayName(),user.getEmail(),user.getUid());
        String json = mapper.writeValueAsString(userMinimumDto);
        return new JSONObject(json);
    }
}
