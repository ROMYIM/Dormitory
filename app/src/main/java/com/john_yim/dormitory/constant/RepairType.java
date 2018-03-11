package com.john_yim.dormitory.constant;

/**
 * Created by MSI-PC on 2018/2/18.
 */

public enum RepairType {
    ELECTRICAL("电器"), WATER_HEATER("水器"), DOOR_WINDOW("门窗");

    private String value;

    RepairType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
