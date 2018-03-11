package com.john_yim.dormitory.entity;

import com.john_yim.dormitory.constant.Authentication;
import com.john_yim.dormitory.constant.PropertyName;

/**
 * Created by MSI-PC on 2018/2/16.
 */

public class DorAdmin extends User {

    public DorAdmin() {
        setAuthentication(Authentication.DORMITORY_ADMINISTRATOR);
    }

    @Override
    public PropertyName whichIsEmpty() {
        return super.whichIsEmpty();
    }
}
