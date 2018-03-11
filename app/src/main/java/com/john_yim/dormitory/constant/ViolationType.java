package com.john_yim.dormitory.constant;

/**
 * Created by MSI-PC on 2018/2/21.
 */

public enum ViolationType {
    VANDALISM(3, "破坏公物"), LATE_BACK(1, "晚归"), ABUSE(3, "滥用"), CLIMB_OVER(4, "翻墙");

    private int value;
    private String description;

    ViolationType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
