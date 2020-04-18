package com.movetto.dtos.validations;

public final class Pattern {

    public static final String MOBILE_NUMBER = "\\d{9}";
    public static final String REGISTER_ID = "/^[a-z]{3}[0-9]{6}[a-z]?$/i";
    public static final String POSTAL_CODE = "0[1-9][0-9]{3}|[1-4][0-9]{4}|5[0-2][0-9]{3}";
    public static final String REGISTRATION_ID = "^[0-9]{1,4}(?!.*(LL|CH))[BCDFGHJKLMNPRSTVWXYZ]{3}";

    private Pattern(){
        //Empty for Framework
    }

}
