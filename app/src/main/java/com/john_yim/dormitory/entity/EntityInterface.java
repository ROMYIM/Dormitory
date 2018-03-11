package com.john_yim.dormitory.entity;

import com.john_yim.dormitory.constant.PropertyName;

import java.util.Map;

/**
 * Created by MSI-PC on 2018/2/16.
 */

interface EntityInterface {
    PropertyName whichIsEmpty();
    Map<String, String> getPropertyMap();
}
