package com.john_yim.dormitory.entity;

import com.john_yim.dormitory.constant.Authentication;
import com.john_yim.dormitory.constant.Gender;
import com.john_yim.dormitory.constant.PropertyName;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by MSI-PC on 2018/2/15.
 */

public class User implements Serializable, EntityInterface {
    private String id;
    private String name;
    private String password;
    private Gender gender;
    private Authentication authentication;

    public User() {}

    public User(String id, String password, Authentication authentication) {
        this.id = id;
        this.password = password;
        this.authentication = authentication;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public PropertyName whichIsEmpty() {
        if (id == null || id.length() < 5)
            return PropertyName.ID;
        if (name == null || name.length() < 2)
            return PropertyName.NAME;
        if (authentication == null)
            return PropertyName.AUTH;
        if (gender == null)
            return PropertyName.GENDER;
        if (password == null || password.length() < 6)
            return PropertyName.PASSWORD;
        return null;
    }

    @Override
    public Map<String, String> getPropertyMap() {
        Map<String, String> propertyMap = new ConcurrentHashMap<>();
        propertyMap.put("id", id);
        propertyMap.put("name", name);
        propertyMap.put("password", password);
        propertyMap.put("authentication", authentication.toString());
        propertyMap.put("gender", gender.toString());
        return propertyMap;
    }
}
