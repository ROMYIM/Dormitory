package com.john_yim.dormitory.constant;

import java.io.Serializable;

/**
 * Created by MSI-PC on 2018/2/15.
 */

public enum Authentication implements Serializable{
    ADMINISTRATOR, DORMITORY_ADMINISTRATOR, STUDENT, MAINTENAACE_WORKER;
    public final String getValue() {
        return name();
    }
}
