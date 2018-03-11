package com.john_yim.dormitory.entity;

import com.john_yim.dormitory.constant.RepairType;
import com.john_yim.dormitory.constant.RepairStatus;

/**
 * Created by MSI-PC on 2018/2/18.
 */

public class Repair extends Information {
    private RepairType type;
    private RepairStatus status;

    public Repair() {}

    public void setType(RepairType type) {
        this.type = type;
    }

    public void setStatus(RepairStatus status) {
        this.status = status;
    }

    public RepairType getType() {
        return type;
    }

    public RepairStatus getStatus() {
        return status;
    }
}
