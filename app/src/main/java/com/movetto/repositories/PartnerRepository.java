package com.movetto.repositories;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.movetto.dtos.UserDto;
import com.movetto.handler.UrlHandler;

import org.json.JSONObject;

import java.io.IOException;

public class PartnerRepository extends UserRepository {

    private static final String BASE_PARTNERS_URL = BASE_USERS_URL + UrlHandler.PARTNERS_URL;

    private MutableLiveData<Boolean> isResponseOk;
    private MutableLiveData<UserDto> userDtoMutableLiveData;

    public PartnerRepository(RequestQueue requestQueue) {
        super(requestQueue);
        userDtoMutableLiveData = new MutableLiveData<>();
        isResponseOk = new MutableLiveData<>();
    }

    public MutableLiveData<UserDto> readPartner(){
        String uri = BASE_PARTNERS_URL + user.getUid();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            UserDto userDto = mapper.readValue(response.toString(),UserDto.class);
                            userDtoMutableLiveData.setValue(userDto);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
        return userDtoMutableLiveData;
    }

    public MutableLiveData<Boolean> savePartner(UserDto userInputDto) throws Exception {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, BASE_PARTNERS_URL, partnerRequest(userInputDto),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            UserDto userDto = mapper.readValue(response.toString(),UserDto.class);
                            if ((userDto != null))
                                isResponseOk.setValue(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                            isResponseOk.setValue(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        isResponseOk.setValue(false);
                    }
                });
        requestQueue.add(request);
        return isResponseOk;
    }

    public MutableLiveData<Boolean> updatePartner(UserDto userInputDto) throws Exception {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT, BASE_PARTNERS_URL, partnerRequest(userInputDto),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            UserDto userDto = mapper.readValue(response.toString(),UserDto.class);
                            if ((userDto != null))
                                isResponseOk.setValue(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                            isResponseOk.setValue(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        isResponseOk.setValue(false);
                    }
                });
        requestQueue.add(request);
        return isResponseOk;
    }

    public void deletePartner(final MutableLiveData<UserDto> userDtoMutableLiveData){
        //TODO
    }

    private JSONObject partnerRequest (UserDto customerDto) throws Exception {
        String json = mapper.writeValueAsString(customerDto);
        System.out.println(json);
        return new JSONObject(json);
    }


}
