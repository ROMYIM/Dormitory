package com.john_yim.dormitory.entity;

import java.io.Serializable;

/**
 * Created by MSI-PC on 2018/2/20.
 */

public class Building implements Serializable {
    private Integer buildingNum;

    public void setBuildingNum(Integer buildingNum) {
        this.buildingNum = buildingNum;
    }

    public Integer getBuildingNum() {
        return buildingNum;
    }
}
