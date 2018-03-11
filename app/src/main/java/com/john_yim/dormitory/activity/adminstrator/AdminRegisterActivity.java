package com.john_yim.dormitory.activity.adminstrator;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.john_yim.dormitory.R;
import com.john_yim.dormitory.constant.AcitivtyCode;
import com.john_yim.dormitory.constant.Constant;
import com.john_yim.dormitory.constant.Gender;
import com.john_yim.dormitory.constant.PropertyName;
import com.john_yim.dormitory.constant.ResultType;
import com.john_yim.dormitory.entity.Administrator;
import com.john_yim.dormitory.entity.ResponseResult;
import com.john_yim.dormitory.util.HttpUtil;
import com.john_yim.dormitory.util.ResponseUtil;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.Map;

public class AdminRegisterActivity extends AppCompatActivity implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener {

    private Context context;
    private EditText idText;
    private EditText nameText;
    private EditText passwordText;
    private RadioButton maleBtn;
    private RadioButton femaleBtn;
    private Administrator administrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);
        context = this;
        administrator = new Administrator();
        bindView();
    }

    @Override
    public void onClick(View view) {
        administrator.setId(idText.getText().toString());
        administrator.setPassword(passwordText.getText().toString());
        administrator.setName(nameText.getText().toString());
        PropertyName propertyName = administrator.whichIsEmpty();
        if (propertyName == null) {
            Map<String, String> adminMap = administrator.getPropertyMap();
            RequestParams params = new RequestParams(adminMap);
            PersistentCookieStore cookieStore = new PersistentCookieStore(context);
            HttpUtil.asyncPost(Constant.ADMIN_REGISTER_URL, params, new ResponseHandler(), cookieStore);
        } else {
            Toast.makeText(context, propertyName.getCnValue() + "不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.AdminMale:
                maleBtn.setChecked(true);
                administrator.setGender(Gender.MALE);
                break;
            case R.id.AdminFemale:
                femaleBtn.setChecked(true);
                administrator.setGender(Gender.FEMALE);
                break;
        }
    }

    private void bindView() {
        idText = (EditText) findViewById(R.id.registerAdminId);
        nameText = (EditText) findViewById(R.id.registerAdminName);
        passwordText = (EditText) findViewById(R.id.registerAdminPassword);
        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.registerAdminGender);
        Button registerButton = (Button) findViewById(R.id.registerAdminBtn);
        maleBtn = (RadioButton) findViewById(R.id.AdminMale);
        femaleBtn = (RadioButton) findViewById(R.id.AdminFemale);
        genderGroup.setOnCheckedChangeListener(this);
        registerButton.setOnClickListener(this);
    }

    private class ResponseHandler extends TextHttpResponseHandler {

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            Toast.makeText(context, "注册失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {
            if (i == 200) {
                ResponseResult<String> result = ResponseUtil.getResult(s,
                        new TypeToken<ResponseResult<String>>(){}.getType());
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                if (result.getCode() == ResultType.SUCCESS.getCode()) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString(PropertyName.ID.name(), administrator.getId());
                    bundle.putString(PropertyName.NAME.name(), administrator.getName());
                    bundle.putString(PropertyName.PASSWORD.name(), administrator.getPassword());
                    bundle.putSerializable(PropertyName.AUTH.name(), administrator.getAuthentication());
                    intent.putExtras(bundle);
                    setResult(AcitivtyCode.REGISTER.ordinal(), intent);
                    finish();
                }
            }
        }
    }
}
