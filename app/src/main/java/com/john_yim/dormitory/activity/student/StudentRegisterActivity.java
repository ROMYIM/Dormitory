package com.john_yim.dormitory.activity.student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.john_yim.dormitory.R;
import com.john_yim.dormitory.constant.AcitivtyCode;
import com.john_yim.dormitory.constant.Constant;
import com.john_yim.dormitory.constant.Gender;
import com.john_yim.dormitory.constant.PropertyName;
import com.john_yim.dormitory.constant.ResultType;
import com.john_yim.dormitory.entity.ResponseResult;
import com.john_yim.dormitory.entity.Student;
import com.john_yim.dormitory.util.HttpUtil;
import com.john_yim.dormitory.util.ResponseUtil;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.Map;

public class StudentRegisterActivity extends AppCompatActivity implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    private EditText idText;
    private EditText passwordText;
    private EditText nameText;
    private RadioGroup genderGroup;
    private Spinner gradeSpinner;
    private Spinner majorSpinner;
    private Spinner classSpinner;
    private Button registerBtn;
    private Student student;
    private PersistentCookieStore cookieStore;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);
        bindStudentView();
        context = StudentRegisterActivity.this;
        cookieStore = new PersistentCookieStore(context);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerBtn:
                studentRegister();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (radioGroup.getId()) {
            case R.id.regiterStudentGender:
                switch (i) {
                    case R.id.studentMale:
                        student.setGender(Gender.MALE);
                        break;
                    case R.id.studentFemale:
                        student.setGender(Gender.FEMALE);
                        break;
                }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.registerStudentGrade:
                if (i == 0)
                    return;
                student.setGrade(Integer.valueOf(adapterView.getItemAtPosition(i).toString().
                        substring(0, 4)));
                break;
            case R.id.registerStudentMajor:
                if (i == 0)
                    return;
                student.setMajor(adapterView.getItemAtPosition(i).toString());
                break;
            case R.id.registerStudentClass:
                if (i == 0)
                    return;
                student.setClassNum(Integer.valueOf(adapterView.getItemAtPosition(i).toString().
                        substring(0,1)));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void bindStudentView() {
        idText = (EditText) findViewById(R.id.registerStudentId);
        nameText = (EditText) findViewById(R.id.registerStudentName);
        passwordText = (EditText) findViewById(R.id.registerStudentPassword);
        majorSpinner = (Spinner) findViewById(R.id.registerStudentMajor);
        gradeSpinner = (Spinner) findViewById(R.id.registerStudentGrade);
        classSpinner = (Spinner) findViewById(R.id.registerStudentClass);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        genderGroup = (RadioGroup) findViewById(R.id.regiterStudentGender);
        gradeSpinner.setOnItemSelectedListener(this);
        majorSpinner.setOnItemSelectedListener(this);
        classSpinner.setOnItemSelectedListener(this);
        genderGroup.setOnCheckedChangeListener(this);
        registerBtn.setOnClickListener(this);
        student = new Student();
    }

    private void doRegister(String url, RequestParams params) {
        HttpUtil.asyncPost(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(context, "注册失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (i == 200) {
                    ResponseResult<String> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<String>>(){}.getType());
                    if (result.getCode() == ResultType.SUCCESS.getCode()) {
                        Toast.makeText(context, result.getMessage() + "自动返回登陆界面", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putCharSequence(PropertyName.ID.name(), student.getId());
                        bundle.putCharSequence(PropertyName.PASSWORD.name(), student.getPassword());
                        bundle.putSerializable(PropertyName.AUTH.name(), student.getAuthentication());
                        Intent intent = new Intent();
                        intent.putExtras(bundle);
                        setResult(AcitivtyCode.REGISTER.ordinal(), intent);
                        finish();
                    } else {
                        Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, cookieStore);
    }

    private void studentRegister() {
        student.setId(idText.getText().toString());
        student.setName(nameText.getText().toString());
        student.setPassword(passwordText.getText().toString());
        PropertyName propertyName = student.whichIsEmpty();
        if (propertyName == null) {
            Map<String, String> studentMap = student.getPropertyMap();
            studentMap.remove("checkInDate");
            studentMap.remove("moveDate");
            RequestParams params = new RequestParams(studentMap);
            doRegister(Constant.STUDENT_REGISTER_URL, params);
        } else {
            Toast.makeText(context, propertyName.getCnValue() + "不能为空或者格式错误", Toast.LENGTH_SHORT).show();
        }
    }
}
