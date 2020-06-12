package com.movetto.dtos.validations;

import android.util.Patterns;
public class Validation {

    public Validation(){
        //Empty for framework
    }

    public static boolean isPostalCodeValid(String postalcode){
        return postalcode.matches(Pattern.POSTAL_CODE);
    }

    public static boolean isRegisterIdValid(String registerId){
        return registerId.matches(Pattern.REGISTER_ID);
    }

    public static boolean isPhoneValid(String phone){
        return phone.matches(Pattern.MOBILE_NUMBER);
    }

    public static boolean isRegistrationIdValid(String registration){
        return registration.matches(Pattern.REGISTRATION_ID);
    }

    public static boolean isEmailValid(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
