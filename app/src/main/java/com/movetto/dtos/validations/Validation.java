package com.movetto.dtos.validations;

public class Validation {

    public Validation(){

    }

    public static boolean isEmpty(String string){
        return string.isEmpty();
    }

    public static boolean isPostalCodeValid(String postalcode){
        return postalcode.matches(Pattern.POSTAL_CODE);
    }

    public static boolean isRegisterIdValid(String postalcode){
        return postalcode.matches(Pattern.REGISTER_ID);
    }

    public static boolean isPhoneValid(String postalcode){
        return postalcode.matches(Pattern.MOBILE_NUMBER);
    }




}
