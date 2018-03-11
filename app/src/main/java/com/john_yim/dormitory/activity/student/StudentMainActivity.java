package com.john_yim.dormitory.activity.student;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.john_yim.dormitory.R;
import com.john_yim.dormitory.SectionPageAdapter;
import com.john_yim.dormitory.constant.Constant;

public class StudentMainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener{

    private SectionPageAdapter pageAdapter;
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private RadioButton mainBtn;
    private RadioButton noticeBtn;
    private RadioButton groupBtn;
    private RadioButton personalBtn;
    private TextView titleTextView;

    private void bindViews() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        titleTextView = (TextView) findViewById(R.id.titleText);
        radioGroup = (RadioGroup) findViewById(R.id.sectionGroup);
        mainBtn = (RadioButton) findViewById(R.id.mainSection);
        noticeBtn = (RadioButton) findViewById(R.id.noticeSection);
        groupBtn = (RadioButton) findViewById(R.id.groupSection);
        personalBtn = (RadioButton) findViewById(R.id.personalSection);
        radioGroup.setOnCheckedChangeListener(this);
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(Constant.HOME_PAGE);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_student);
        pageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        bindViews();
        mainBtn.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.mainSection:
                viewPager.setCurrentItem(Constant.HOME_PAGE);
                break;
            case R.id.noticeSection:
                viewPager.setCurrentItem(Constant.NOTICE_PAGE);
                break;
            case R.id.groupSection:
                viewPager.setCurrentItem(Constant.GROUP_PAGE);
                break;
            case R.id.personalSection:
                viewPager.setCurrentItem(Constant.PERSONAL_PAGE);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 2) {
            switch (viewPager.getCurrentItem()) {
                case Constant.HOME_PAGE:
                    titleTextView.setText("首页");
                    mainBtn.setChecked(true);
                    break;
                case Constant.NOTICE_PAGE:
                    titleTextView.setText("消息");
                    noticeBtn.setChecked(true);
                    break;
                case Constant.GROUP_PAGE:
                    titleTextView.setText("论坛");
                    groupBtn.setChecked(true);
                    break;
                case Constant.PERSONAL_PAGE:
                    titleTextView.setText("我的");
                    personalBtn.setChecked(true);
                    break;
            }
        }
    }
}

