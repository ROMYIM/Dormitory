package com.john_yim.dormitory.constant;

/**
 * Created by MSI-PC on 2018/2/15.
 */

public enum Gender {
    MALE("男"), FEMALE("女");
    private String value;
    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
