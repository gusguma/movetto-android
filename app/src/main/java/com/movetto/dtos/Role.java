package com.movetto.dtos;

public enum Role {

    ADMIN,USER,DESTINATION,CUSTOMER,PARTNER;

    public String roleName(){
        return "ROLE_" + this.toString();
    }

}
