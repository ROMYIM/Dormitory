package com.john_yim.dormitory.util;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.john_yim.dormitory.constant.Authentication;

/**
 * Created by MSI-PC on 2018/3/11.
 */

public class TitleBarUtil {
    public static void setActivityTitleBar(Authentication authentication, TextView title,
                                           EditText searchContent, Button searchBtn) {
        switch (authentication) {
            case STUDENT:
                title.setVisibility(View.VISIBLE);
                searchBtn.setVisibility(View.INVISIBLE);
                searchContent.setVisibility(View.INVISIBLE);
                break;
            case DORMITORY_ADMINISTRATOR:
                title.setVisibility(View.INVISIBLE);
                searchContent.setVisibility(View.VISIBLE);
                searchBtn.setVisibility(View.VISIBLE);
                break;
        }
    }
}
