package com.john_yim.dormitory.entity;

import com.john_yim.dormitory.constant.Authentication;
import com.john_yim.dormitory.constant.PropertyName;
import com.john_yim.dormitory.util.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by MSI-PC on 2018/2/15.
 */

public class Student extends User {
    private String major;
    private Integer classNum;
    private Integer grade;
    private Date checkInDate;
    private Date moveDate;
    private Dormitory dormitory;
    private List<Violation> violationRecords;

    public Student() {
        setAuthentication(Authentication.STUDENT);
    }

    public Student(String id, String password, Authentication authentication) {
        super(id, password, authentication);
    }

    public String getMajor() {
        return major;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public Integer getGrade() {
        return grade;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getMoveDate() {
        return moveDate;
    }

    public Dormitory getDormitory() {
        return dormitory;
    }

    public List<Violation> getViolationRecords() {
        return violationRecords;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setMoveDate(Date moveDate) {
        this.moveDate = moveDate;
    }

    public void setDormitory(Dormitory dormitory) {
        this.dormitory = dormitory;
    }

    public void setViolationRecords(List<Violation> violationRecords) {
        this.violationRecords = violationRecords;
    }

    @Override
    public PropertyName whichIsEmpty() {
        if (major == null || major.length() < 4)
            return PropertyName.MAJOR;
        if (classNum == null)
            return PropertyName.CLASS;
        if (grade == null)
            return PropertyName.GRADE;
        return super.whichIsEmpty();
    }

    @Override
    public Map<String, String> getPropertyMap() {
        Map<String, String> propertyMap = super.getPropertyMap();
        propertyMap.put("grade", String.valueOf(grade));
        propertyMap.put("major", major);
        propertyMap.put("classNum", String.valueOf(classNum));
        propertyMap.put("checkInDate", DateUtil.dateToString("yyyy-MM-dd", checkInDate));
        propertyMap.put("moveDate", DateUtil.dateToString("yyyy-MM-dd", moveDate));
        if (dormitory != null) {
            propertyMap.put("dormitoryId", dormitory.getDormitoryId());
            propertyMap.put("id", String.valueOf(dormitory.getId()));
            propertyMap.put("floor", String.valueOf(dormitory.getFloor()));
            propertyMap.put("buildingNum", String.valueOf(dormitory.getBuilding().getBuildingNum()));
            propertyMap.put("eBills", String.valueOf(dormitory.getEbills()));
            propertyMap.put("wBills", String.valueOf(dormitory.getWbills()));
        }
        return propertyMap;
    }
}
