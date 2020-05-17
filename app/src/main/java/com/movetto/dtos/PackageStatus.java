package com.movetto.dtos;

public enum PackageStatus {
    SAVED, PREPARED, COLLECTED, TRANSIT, DETAINED, DELIVERED, DELETED;

    public String packageStatusName(){
        return "PACKAGE_STATUS_" + this.toString();
    }
}
