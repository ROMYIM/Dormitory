package com.john_yim.dormitory.entity;

import com.john_yim.dormitory.constant.ViolationType;

/**
 * Created by MSI-PC on 2018/2/20.
 */

public class Violation extends Information {

    private ViolationType type;

    public Violation() {

    }

    public void setType(ViolationType type) {
        this.type = type;
    }

    public ViolationType getType() {
        return type;
    }
}
