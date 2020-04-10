package com.movetto.dtos.validations;

public final class Pattern {

    public static final String MOBILE_NUMBER = "\\d{9}";
    public static final String REGISTER_ID = "/^[a-z]{3}[0-9]{6}[a-z]?$/i";
    public static final String POSTAL_CODE = "/^(?:0[1-9]\\d{3}|[1-4]\\d{4}|5[0-2]\\d{3})$/";

    private Pattern(){
        //Empty for Framework
    }

}
