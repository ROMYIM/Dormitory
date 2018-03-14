package com.john_yim.dormitory.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MSI-PC on 2018/2/16.
 */

public class Dormitory implements Serializable {
    private String dormitoryId;
    private Integer id;
    private Integer floor;
    private Building building;
    private Float ebills;
    private Float wbills;
    private List<Student> students;
    private List<Repair> repairInformations;

    public Dormitory() {}

    public Dormitory(Integer id, Building building, Integer floor) {
        setId(id);
        setBuilding(building);
        setFloor(floor);
        setEbills(0.0F);
        setWbills(0.0F);
        dormitoryId = "" + building.getBuildingNum() + floor + id;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Building getBuilding() {
        return building;
    }

    public void setDormitoryId(String dormitoryId) {
        this.dormitoryId = dormitoryId;
    }

    public String getDormitoryId() {
        return dormitoryId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getLongId() {
        StringBuilder stringBuilder = new StringBuilder(getFloor()).append(getId());
        return stringBuilder.toString();
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setEbills(Float ebills) {
        this.ebills = ebills;
    }

    public Float getEbills() {
        return ebills;
    }

    public void setWbills(Float wbills) {
        this.wbills = wbills;
    }

    public Float getWbills() {
        return wbills;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setRepairInformations(List<Repair> repairInformations) {
        this.repairInformations = repairInformations;
    }

    public List<Repair> getRepairInformations() {
        return repairInformations;
    }
}
