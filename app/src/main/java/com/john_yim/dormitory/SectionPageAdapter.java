package com.john_yim.dormitory;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.john_yim.dormitory.constant.Constant;
import com.john_yim.dormitory.fragment.GroupFragment;
import com.john_yim.dormitory.fragment.student.StudentHomeFragment;
import com.john_yim.dormitory.fragment.student.StudentNewsFragment;
import com.john_yim.dormitory.fragment.student.StudentPersonalFragment;

/**
 * Created by MSI-PC on 2018/2/11.
 */

public class SectionPageAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 4;

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case Constant.HOME_PAGE: return new StudentHomeFragment();
            case Constant.NOTICE_PAGE: return new StudentNewsFragment();
            case Constant.GROUP_PAGE: return new GroupFragment();
            case Constant.PERSONAL_PAGE: return new StudentPersonalFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
