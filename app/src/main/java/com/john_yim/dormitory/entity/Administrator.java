package com.john_yim.dormitory.entity;

import com.john_yim.dormitory.constant.Authentication;

/**
 * Created by MSI-PC on 2018/2/16.
 */

public class Administrator extends User {
    public Administrator() {
        setAuthentication(Authentication.ADMINISTRATOR);
    }
}
