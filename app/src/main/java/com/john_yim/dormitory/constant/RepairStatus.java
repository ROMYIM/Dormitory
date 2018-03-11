package com.john_yim.dormitory.constant;

/**
 * Created by MSI-PC on 2018/2/18.
 */

public enum RepairStatus {
    INTACT("维修中"), BROKEN("待维修"), REPAIRED("已维修");

    private String value;

    RepairStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
