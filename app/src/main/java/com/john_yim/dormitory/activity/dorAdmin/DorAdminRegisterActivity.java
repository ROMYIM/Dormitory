package com.john_yim.dormitory.activity.dorAdmin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.john_yim.dormitory.R;
import com.john_yim.dormitory.activity.WelcomeActivity;
import com.john_yim.dormitory.constant.Constant;
import com.john_yim.dormitory.constant.Gender;
import com.john_yim.dormitory.constant.PropertyName;
import com.john_yim.dormitory.constant.ResultType;
import com.john_yim.dormitory.entity.DorAdmin;
import com.john_yim.dormitory.entity.ResponseResult;
import com.john_yim.dormitory.util.HttpUtil;
import com.john_yim.dormitory.util.ResponseUtil;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.Map;

public class DorAdminRegisterActivity extends AppCompatActivity implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener{

    private EditText idText;
    private EditText passwordText;
    private EditText nameText;
    private RadioGroup genderGroup;
    private RadioButton maleBtn;
    private RadioButton femaleBtn;
    private Button registerBtn;
    private DorAdmin dorAdmin;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_doradmin);
        dorAdmin = new DorAdmin();
        context = this;
        idText = (EditText) findViewById(R.id.registerDorAdminId);
        nameText = (EditText) findViewById(R.id.registerDorAdminName);
        passwordText = (EditText) findViewById(R.id.registerDorAdminPassword);
        genderGroup = (RadioGroup) findViewById(R.id.registerDorAdminGender);
        maleBtn = (RadioButton) findViewById(R.id.DorAdminMale);
        femaleBtn = (RadioButton) findViewById(R.id.DorAdminFemale);
        registerBtn = (Button) findViewById(R.id.registerDorAdminBtn);
        registerBtn.setOnClickListener(this);
        genderGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.registerDorAdminBtn) {
            dorAdmin.setId(idText.getText().toString());
            dorAdmin.setName(nameText.getText().toString());
            dorAdmin.setPassword(passwordText.getText().toString());
            dorAdminRegister();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.DorAdminMale:
                dorAdmin.setGender(Gender.MALE);
                maleBtn.setChecked(true);
                break;
            case R.id.DorAdminFemale:
                dorAdmin.setGender(Gender.FEMALE);
                femaleBtn.setChecked(true);
                break;
        }
    }

    private void dorAdminRegister() {
        PropertyName propertyName = dorAdmin.whichIsEmpty();
        if (propertyName == null) {
            Map<String, String> dorAdminMap = dorAdmin.getPropertyMap();
            RequestParams params = new RequestParams(dorAdminMap);
            PersistentCookieStore cookieStore = new PersistentCookieStore(context);
            HttpUtil.asyncPost(Constant.DORADMIN_REGISTER_URL, params, new TextHttpResponseHandler() {
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
                            Intent intent = new Intent(context, WelcomeActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }, cookieStore);
        }
    }
}
