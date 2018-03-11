package com.john_yim.dormitory.fragment.student;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.john_yim.dormitory.R;
import com.john_yim.dormitory.activity.WelcomeActivity;
import com.john_yim.dormitory.constant.Constant;
import com.john_yim.dormitory.constant.ResultType;
import com.john_yim.dormitory.entity.Dormitory;
import com.john_yim.dormitory.entity.ResponseResult;
import com.john_yim.dormitory.entity.Student;
import com.john_yim.dormitory.entity.Violation;
import com.john_yim.dormitory.util.DateUtil;
import com.john_yim.dormitory.util.HttpUtil;
import com.john_yim.dormitory.util.ResponseUtil;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentPersonalFragment extends Fragment implements View.OnClickListener{

    private Context context;
    private PersonalInfoResponseHandler responseHandler;
    private View rootView;

    public StudentPersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_student_personal, container, false);
        context = getContext();
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        responseHandler = new PersonalInfoResponseHandler();
        HttpUtil.asyncGet(Constant.STUDENT_LOOK_INFO_URL, null, responseHandler, cookieStore);
        return rootView;
    }

    private void bindStudentView(View view, Student student) throws NullPointerException {
        TextView idTextView = view.findViewById(R.id.idText);
        TextView nameTextView = view.findViewById(R.id.nameText);
        TextView genderTextView = view.findViewById(R.id.genderText);
        TextView gradeTextView = view.findViewById(R.id.gradeText);
        TextView majorTextView = view.findViewById(R.id.majorText);
        TextView classTextView = view.findViewById(R.id.classText);
        TextView buildingTextView = view.findViewById(R.id.buildingText);
        TextView dorIdTextView = view.findViewById(R.id.dorIdText);
        TextView checkInTextView = view.findViewById(R.id.checkInText);
        TextView violationNumText = view.findViewById(R.id.violationText);
        RelativeLayout logoutBtn = view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(this);
        idTextView.setText(student.getId());
        nameTextView.setText(student.getName());
        genderTextView.setText(student.getGender().name());
        gradeTextView.setText(String.valueOf(student.getGrade()));
        majorTextView.setText(student.getMajor());
        classTextView.setText(String.valueOf(student.getClassNum()));
        Dormitory dormitory = student.getDormitory();
        List<Violation> violations = student.getViolationRecords();
        if (dormitory != null) {
            buildingTextView.setText(String.valueOf(dormitory.getBuilding().getBuildingNum()));
            dorIdTextView.setText(dormitory.getLongId());
            checkInTextView.setText(DateUtil.dateToString("yyyy-MM-dd", student.getCheckInDate()));
        }
        if (violations != null && violations.size() > 0) {
            violationNumText.setText(violations.size());
        }
    }

    @Override
    public void onClick(View view) {
        final PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        HttpUtil.asyncGet(Constant.STUDENT_LOGOUT_URL, null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(context, "登出失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (i == 200) {
                    ResponseResult<String> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<String>>(){}.getType());
                    if (result.getCode() == ResultType.SUCCESS.getCode()) {
                        cookieStore.getCookies().clear();
                        Intent intent = new Intent(context, WelcomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, cookieStore);
    }

    private class PersonalInfoResponseHandler extends TextHttpResponseHandler {

        Student infoResult;

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            Toast.makeText(context, "个人查询信息失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {
            if (i == 200) {
                try {
                    ResponseResult<Student> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<Student>>(){}.getType());
                    infoResult = result.getResult();
                    bindStudentView(rootView, infoResult);
                } catch (JsonParseException e) {
                    ResponseResult<String> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<String>>(){}.getType());
                    Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    Toast.makeText(context, "个人查询信息失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
