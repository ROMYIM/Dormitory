package com.john_yim.dormitory.constant;

/**
 * Created by MSI-PC on 2018/2/16.
 */

public enum PropertyName {
    ID("id", "id"), NAME("名字", "name"), PASSWORD("密码", "password"), AUTH("身份","authentication"),
    GENDER("性别", "gender"), MAJOR("专业", "major"), GRADE("年级", "grade"), CLASS("班级", "classNum"),
    TYPE("类型", "type"), STATUS("状态", "status");
    private String cnValue;
    private String enValue;
    PropertyName(String cnValue, String enValue) {
        this.cnValue = cnValue;
        this.enValue = enValue;
    }

    public final String getCnValue() {
        return cnValue;
    }

    public final String getEnValue() {
        return enValue;
    }
}
