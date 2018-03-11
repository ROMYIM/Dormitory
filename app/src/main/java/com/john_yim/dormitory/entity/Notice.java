package com.john_yim.dormitory.entity;

import com.john_yim.dormitory.util.DateUtil;

import java.util.List;

/**
 * Created by MSI-PC on 2018/2/18.
 */

public class Notice extends Information {
    List<Integer> buildings;

    public List<Integer> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Integer> buildings) {
        this.buildings = buildings;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("id：").append(getId()).append("，sendDate：").
                append(DateUtil.dateToString("yyyy-MM-dd", getSendDate())).append("，content：").
                append(getContent());
        return stringBuffer.toString();
    }
}
