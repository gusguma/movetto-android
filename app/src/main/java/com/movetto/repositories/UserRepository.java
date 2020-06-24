package com.movetto.repositories;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.movetto.dtos.UserDto;
import com.movetto.dtos.UserMinimumDto;
import com.movetto.handler.UrlHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class UserRepository {

    protected static final String BASE_USERS_URL = UrlHandler.API_URL + UrlHandler.USERS_URL;
    protected static final String BASE_USERS_EMAIL_URL =
            UrlHandler.API_URL + UrlHandler.USERS_URL + "email/";

    protected FirebaseUser user;
    protected RequestQueue requestQueue;
    private MutableLiveData<UserDto> userDtoMutable;
    private MutableLiveData<Boolean> isResponseOk;
    protected ObjectMapper mapper;

    public UserRepository(RequestQueue requestQueue) {
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.requestQueue = requestQueue;
        this.mapper = new ObjectMapper();
        this.isResponseOk = new MutableLiveData<Boolean>();
        this.userDtoMutable = new MutableLiveData<UserDto>();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public MutableLiveData<UserDto> readUser() {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, BASE_USERS_URL + user.getUid(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            UserDto userDto = mapper.readValue(response.toString(), UserDto.class);
                            userDtoMutable.setValue(userDto);
                        } catch (IOException e) {
                            userDtoMutable.setValue(null);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            saveUser();
                        } catch (Exception e) {
                            userDtoMutable.setValue(null);
                            e.printStackTrace();
                        }
                    }
                });
        requestQueue.add(request);
        return userDtoMutable;
    }

    public MutableLiveData<UserDto> readUserByEmail(UserDto destinationUser) {
        String uri = BASE_USERS_EMAIL_URL + destinationUser.getEmail();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            UserDto userDto = mapper.readValue(response.toString(), UserDto.class);
                            userDtoMutable.setValue(userDto);
                        } catch (IOException e) {
                            userDtoMutable.setValue(null);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        userDtoMutable.setValue(null);
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return userDtoMutable;
    }

    public MutableLiveData<UserDto> saveUser() throws Exception {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                BASE_USERS_URL, userRequest(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            UserDto userDto = mapper.readValue(response.toString(), UserDto.class);
                            userDtoMutable.setValue(userDto);
                        } catch (IOException e) {
                            userDtoMutable.setValue(null);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        userDtoMutable.setValue(null);
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return userDtoMutable;
    }

    public MutableLiveData<UserDto> saveUserByEmail(UserDto destinationUser) throws Exception {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                BASE_USERS_EMAIL_URL, updateRequestUser(destinationUser),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            UserDto userDto = mapper.readValue(response.toString(), UserDto.class);
                            userDtoMutable.setValue(userDto);
                        } catch (IOException e) {
                            e.printStackTrace();
                            userDtoMutable.setValue(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        userDtoMutable.setValue(null);
                    }
                });
        requestQueue.add(request);
        return userDtoMutable;
    }

    public MutableLiveData<UserDto> updateUser(UserDto updateUser) throws JsonProcessingException, JSONException {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,
                BASE_USERS_URL, updateRequestUser(updateUser),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            UserDto userDto = mapper.readValue(response.toString(), UserDto.class);
                            userDtoMutable.setValue(userDto);
                        } catch (IOException e) {
                            e.printStackTrace();
                            userDtoMutable.setValue(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        userDtoMutable.setValue(null);
                    }
                });
        requestQueue.add(request);
        return userDtoMutable;
    }

    public void deleteUser(final MutableLiveData<UserDto> userDtoMutableLiveData) {

    }

    private JSONObject userRequest() throws JSONException, JsonProcessingException {
        UserMinimumDto userMinimumDto = new UserMinimumDto(
                user.getDisplayName(), user.getEmail(), user.getUid());
        String json = mapper.writeValueAsString(userMinimumDto);
        return new JSONObject(json);
    }

    private JSONObject updateRequestUser(UserDto userUpdate) throws JsonProcessingException, JSONException {
        String json = mapper.writeValueAsString(userUpdate);
        System.out.println(json);
        return new JSONObject(json);
    }

    public FirebaseUser getUser() {
        return user;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
