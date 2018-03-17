package com.john_yim.dormitory.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.john_yim.dormitory.R;
import com.john_yim.dormitory.ViewAdapter;
import com.john_yim.dormitory.constant.Authentication;
import com.john_yim.dormitory.constant.Constant;
import com.john_yim.dormitory.constant.EntityType;
import com.john_yim.dormitory.constant.ResultType;
import com.john_yim.dormitory.entity.Dormitory;
import com.john_yim.dormitory.entity.Notice;
import com.john_yim.dormitory.entity.Repair;
import com.john_yim.dormitory.entity.ResponseResult;
import com.john_yim.dormitory.entity.Student;
import com.john_yim.dormitory.entity.Violation;
import com.john_yim.dormitory.util.DateUtil;
import com.john_yim.dormitory.util.HttpUtil;
import com.john_yim.dormitory.util.ResponseUtil;
import com.john_yim.dormitory.util.TitleBarUtil;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.List;

public class InfoDetailActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    private Context context;
    private EditText searchContent;
    private PersistentCookieStore cookieStore;
    private String searchTarget;
    private EntityType entityType;
    private Authentication authentication;
    private String function;

    private String buildingNumStr;
    private String floorStr;
    private String roomStr;

    private RelativeLayout studentInfo;
    private TextView idTextView;
    private TextView nameTextView;
    private TextView genderTextView;
    private TextView gradeTextView;
    private TextView majorTextView;
    private TextView classTextView;
    private Spinner buildingNum;
    private Spinner roomNum;
    private Spinner floorNum;
    private Button confimBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = InfoDetailActivity.this;
        TextView title;
        Button searchBtn;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        authentication = (Authentication) bundle.getSerializable("auth");
        switch (authentication) {
            case STUDENT:
                EntityType type = (EntityType) bundle.getSerializable("entity");
                switch (type) {
                    case NOTICE:
                        Notice notice = (Notice) bundle.getSerializable(EntityType.NOTICE.name());
                        setContentView(R.layout.activity_detail_notice);
                        bindNoticeView(notice);
                        break;
                    case REPAIR:
                        Repair repair = (Repair) bundle.getSerializable(EntityType.REPAIR.name());
                        setContentView(R.layout.activity_detail_repair);
                        bindRepairView(repair);
                        break;
                    case VIOLATION:
                        Violation violation = (Violation) bundle.getSerializable(EntityType.VIOLATION.name());
                        setContentView(R.layout.activity_detail_violation);
                        bindViolationView(violation);
                        break;
                }
                break;
            case DORMITORY_ADMINISTRATOR:
                searchTarget = intent.getStringExtra("searchTarget");
                if (searchTarget.equals("student")) {
                    setContentView(R.layout.activity_detail_student_info);
                } else if (searchTarget.equals("dormitory")) {
                    setContentView(R.layout.activity_detail_dormitory);
                }
                title = (TextView) findViewById(R.id.activityTitle);
                searchBtn = (Button) findViewById(R.id.searchBtn);
                searchContent = (EditText) findViewById(R.id.searchStudentId);
                TitleBarUtil.setActivityTitleBar(authentication, title, searchContent, searchBtn);
                searchBtn.setOnClickListener(this);
                break;
            case ADMINISTRATOR:
                function = bundle.getString("function");
                if (function.equals("search")) {
                    entityType = (EntityType) bundle.getSerializable("entity");
                    if (entityType == EntityType.STUDENT) {
                        setContentView(R.layout.activity_detail_student_info);
                        studentInfo = (RelativeLayout) findViewById(R.id.studentInfoList);
                        studentInfo.setVisibility(View.INVISIBLE);
                    } else if (entityType == EntityType.DORMITORY_ADMIN) {
                        setContentView(R.layout.activity_detail_dormitory);
                    }
                } else if (function.equals("edit")) {
                    entityType = (EntityType) bundle.getSerializable("entity");
                    if (entityType == EntityType.STUDENT) {
                        setContentView(R.layout.activity_edit_student);
                        studentInfo = (RelativeLayout) findViewById(R.id.studentInfoList);
                        studentInfo.setVisibility(View.INVISIBLE);
                        buildingNum = (Spinner) findViewById(R.id.buildingEdit);
                        floorNum = (Spinner) findViewById(R.id.floorEdit);
                        roomNum = (Spinner) findViewById(R.id.roomEdit);
                        buildingNum.setOnItemSelectedListener(this);
                        floorNum.setOnItemSelectedListener(this);
                        roomNum.setOnItemSelectedListener(this);
                    } else if (entityType == EntityType.DORMITORY_ADMIN) {

                    }
                    confimBtn = (Button) findViewById(R.id.confirmBtn);
                    confimBtn.setOnClickListener(this);
                }
                title = (TextView) findViewById(R.id.activityTitle);
                searchBtn = (Button) findViewById(R.id.searchBtn);
                searchContent = (EditText) findViewById(R.id.searchStudentId);
                TitleBarUtil.setActivityTitleBar(authentication, title, searchContent, searchBtn);
                searchBtn.setOnClickListener(this);
        }

    }

    private void bindNoticeView(Notice notice) {
        TextView idText = (TextView) findViewById(R.id.noticeInfoId);
        TextView sendDateText = (TextView) findViewById(R.id.noticeInfoSendDate);
        TextView contentText = (TextView) findViewById(R.id.noticeInfoContent);
        idText.setText(String.valueOf(notice.getId()));
        sendDateText.setText(DateUtil.dateToString("yyyy-MM-dd",notice.getSendDate()));
        contentText.setText(notice.getContent());
    }

    private void bindRepairView(Repair repair) {
        TextView idText = (TextView) findViewById(R.id.repairInfoId);
        TextView sendDateText = (TextView) findViewById(R.id.repairInfoSendDate);
        TextView typeText = (TextView) findViewById(R.id.repairInfoType);
        TextView statusText = (TextView) findViewById(R.id.repairInfoStatus);
        TextView contentText = (TextView) findViewById(R.id.repairInfoContent);
        idText.setText(String.valueOf(repair.getId()));
        sendDateText.setText(DateUtil.dateToString("yyyy-MM-dd", repair.getSendDate()));
        typeText.setText(repair.getType().getValue());
        statusText.setText(repair.getStatus().getValue());
        contentText.setText(repair.getContent());
    }

    private void bindViolationView(Violation violation) {
        TextView idText = (TextView) findViewById(R.id.violationInfoId);
        TextView sendDateText = (TextView) findViewById(R.id.violationInfoSendDate);
        TextView typeText = (TextView) findViewById(R.id.violationInfoType);
        TextView contentText = (TextView) findViewById(R.id.violationInfoContent);
        idText.setText(String.valueOf(violation.getId()));
        sendDateText.setText(DateUtil.dateToString("yyyy-MM-dd", violation.getSendDate()));
        typeText.setText(violation.getType().getDescription());
        contentText.setText(violation.getContent());
    }

    private void bindStudentInfoView(Student student) {
        idTextView = (TextView) findViewById(R.id.idText);
        nameTextView = (TextView) findViewById(R.id.nameText);
        genderTextView = (TextView) findViewById(R.id.genderText);
        gradeTextView = (TextView) findViewById(R.id.gradeText);
        majorTextView = (TextView) findViewById(R.id.majorText);
        classTextView = (TextView) findViewById(R.id.classText);
        idTextView.setText(student.getId());
        nameTextView.setText(student.getName());
        genderTextView.setText(student.getGender().getValue());
        gradeTextView.setText(String.valueOf(student.getGrade()));
        majorTextView.setText(student.getMajor());
        classTextView.setText(String.valueOf(student.getClassNum()));
        if (function.equals("search")) {
            TextView buildingTextView = (TextView) findViewById(R.id.buildingText);
            TextView dorIdTextView = (TextView) findViewById(R.id.dorIdText);
            TextView checkInTextView = (TextView) findViewById(R.id.checkInText);
            Dormitory dormitory = student.getDormitory();
            if (dormitory != null) {
                buildingTextView.setText(String.valueOf(dormitory.getBuilding().getBuildingNum()));
                dorIdTextView.setText(dormitory.getLongId());
                checkInTextView.setText(DateUtil.dateToString("yyyy-MM-dd", student.getCheckInDate()));
            }
            studentInfo.setVisibility(View.VISIBLE);
        } else if (function.equals("edit")) {
            studentInfo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        cookieStore = new PersistentCookieStore(context);
        switch (view.getId()) {
            case R.id.searchBtn:
                if (searchTarget != null) {
                    if (searchTarget.equals("student")) {
                        String studentId = searchContent.getText().toString();
                        HttpUtil.asyncGet(Constant.DORADMIN_LOOK_STUDENT_URL + studentId, null,
                                new StudentInfoResponseHandler(), cookieStore);
                    } else if (searchTarget.equals("dormitory")) {
                        String studentId = searchContent.getText().toString();
                        HttpUtil.asyncGet(Constant.DORADMIN_LOOK_DORMITORY_URL + studentId, null,
                                new DormitoryInfoResponseHandler(), cookieStore);
                    }
                }
                if (entityType != null) {
                    if (entityType == EntityType.STUDENT) {
                        String studentId = searchContent.getText().toString();
                        HttpUtil.asyncGet(Constant.ADMIN_LOOK_STUDENT_URL + studentId, null,
                                new StudentInfoResponseHandler(), cookieStore);
                    }
                }
                break;
            case R.id.confirmBtn:
                if (entityType == EntityType.STUDENT) {
                    String studentId = idTextView.getText().toString();
                    if (buildingNumStr == null || floorStr == null || roomStr == null) {
                        Toast.makeText(context, "请完善宿舍信息", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        String dormitoryId = buildingNumStr + floorStr + roomStr;
                        RequestParams params = new RequestParams();
                        params.put("studentId", studentId);
                        params.put("dormitoryId", dormitoryId);
                        HttpUtil.asyncPost(Constant.ADMIN_ENTER_DORMITORY_URL, params,
                                new EnterDormitoryResponseHandler(), cookieStore);
                    }
                }
                break;
        }
    }

    private void bindDormitoryInfoView(Dormitory dormitory) {
        TextView buildingNumText = (TextView) findViewById(R.id.buildingNumText);
        TextView floorText = (TextView) findViewById(R.id.floorText);
        TextView idText = (TextView) findViewById(R.id.dormitoryIdText);
        buildingNumText.setText(String.valueOf(dormitory.getBuilding().getBuildingNum()));
        floorText.setText(String.valueOf(dormitory.getFloor()));
        idText.setText(String.valueOf(dormitory.getId()));
        if (dormitory.getStudents() == null || dormitory.getStudents().size() == 0) {
            HttpUtil.asyncGet(Constant.DORADMIN_LOOK_STUDENTS_URL + dormitory.getDormitoryId(), null,
                    new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Toast.makeText(context, "获取宿舍的学生列表失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSuccess(int i, Header[] headers, String s) {
                            if (i == 200) {
                                try {
                                    ResponseResult<List<Student>> result = ResponseUtil.getResult(s,
                                            new TypeToken<ResponseResult<List<Student>>>(){}.getType());
                                    BaseAdapter viewAdapter = new ViewAdapter<Student>(result.getResult(),
                                            R.layout.item_list_student) {
                                        @Override
                                        public void bindView(ViewHolder holder, Student obj) {
                                            holder.setText(R.id.studentPlane, obj.getId());
                                        }
                                    };
                                    ListView listView = (ListView) findViewById(R.id.studentList);
                                    listView.setAdapter(viewAdapter);
                                } catch (JsonParseException e) {
                                    ResponseResult<String> result = ResponseUtil.getResult(s,
                                            new TypeToken<ResponseResult<String>>(){}.getType());
                                    Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }, cookieStore);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0)
            return;
        switch (adapterView.getId()) {
            case R.id.buildingEdit:
                buildingNumStr = adapterView.getItemAtPosition(i).toString().substring(0, 2);
                break;
            case R.id.floorEdit:
                floorStr = String.valueOf(i);
                break;
            case R.id.roomEdit:
                roomStr = adapterView.getItemAtPosition(i).toString().substring(0, 2);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class StudentInfoResponseHandler extends TextHttpResponseHandler {

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            Toast.makeText(context, "获取信息失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {
            if (i == 200) {
                try {
                    ResponseResult<Student> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<Student>>(){}.getType());
                    Student student = result.getResult();
                    bindStudentInfoView(student);
                } catch (JsonParseException e) {
                    ResponseResult<String> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<String>>(){}.getType());
                    Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class DormitoryInfoResponseHandler extends TextHttpResponseHandler {

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            Toast.makeText(context, "获取宿舍信息失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {
            if (i == 200) {
                try {
                    ResponseResult<Dormitory> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<Dormitory>>(){}.getType());
                    Dormitory dormitory = result.getResult();
                    bindDormitoryInfoView(dormitory);
                } catch (JsonParseException e) {
                    ResponseResult<String> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<String>>(){}.getType());
                    Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT);
                }
            }
        }
    }

    private class ViolationInfoResponseHandler extends TextHttpResponseHandler {

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            Toast.makeText(context, "获取违规信息失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {
            if (i == 200) {

            }
        }
    }

    private class EnterDormitoryResponseHandler extends TextHttpResponseHandler {

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            Toast.makeText(context, "入宿操作失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {
            if (i == 200) {
                ResponseResult<String> result = ResponseUtil.getResult(s,
                        new TypeToken<ResponseResult<String>>(){}.getType());
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                if (result.getCode() == ResultType.SUCCESS.getCode()) {
                    finish();
                }
            }
        }
    }
}
