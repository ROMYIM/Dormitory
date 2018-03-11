package com.john_yim.dormitory.activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.john_yim.dormitory.R;
import com.john_yim.dormitory.activity.dorAdmin.DorAdminMainActivity;
import com.john_yim.dormitory.activity.dorAdmin.DorAdminRegisterActivity;
import com.john_yim.dormitory.activity.student.StudentMainActivity;
import com.john_yim.dormitory.activity.student.StudentRegisterActivity;
import com.john_yim.dormitory.constant.AcitivtyCode;
import com.john_yim.dormitory.constant.Authentication;
import com.john_yim.dormitory.constant.Constant;
import com.john_yim.dormitory.constant.PropertyName;
import com.john_yim.dormitory.constant.ResultType;
import com.john_yim.dormitory.entity.ResponseResult;
import com.john_yim.dormitory.entity.User;
import com.john_yim.dormitory.util.HttpUtil;
import com.john_yim.dormitory.util.ResponseUtil;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WelcomeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        View.OnClickListener{

    private Button loginBtn;
    private EditText idText;
    private EditText passwordText;
    private TextView registerTextView;
    private RadioGroup authGroup;
    private RadioButton studentBtn;
    private RadioButton dorAdminBtn;
    private RadioButton adminBtn;
    private Context context;
    private PersistentCookieStore cookieStore;
    private boolean isFirst;
    public static User userTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        userTemp = new User();
        context = WelcomeActivity.this;
        cookieStore = new PersistentCookieStore(context);
        List<Cookie> cookies = cookieStore.getCookies();
        if (cookies == null || cookies.size() == 0) {
            setContentView(R.layout.activity_welcome_login);
            isFirst = true;
            bindLoginView();
        } else {
            isFirst = false;
            login(cookies);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AcitivtyCode.WELCOME.ordinal()) {
            if (resultCode == AcitivtyCode.REGISTER.ordinal()) {
                Bundle bundle = data.getExtras();
                idText.setText(bundle.getCharSequence(PropertyName.ID.name()));
                passwordText.setText(bundle.getCharSequence(PropertyName.PASSWORD.name()));
                switch ((Authentication) bundle.getSerializable(PropertyName.AUTH.name())) {
                    case STUDENT:
                        studentBtn.setChecked(true);
                        break;
                    case DORMITORY_ADMINISTRATOR:
                        dorAdminBtn.setChecked(true);
                        break;
                    case ADMINISTRATOR:
                        adminBtn.setChecked(true);
                        break;
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.studentRadioBtn:
                studentBtn.setChecked(true);
                break;
            case R.id.dorAdminRadioBtn:
                dorAdminBtn.setChecked(true);
                break;
            case R.id.adminRadioBtn:
                adminBtn.setChecked(true);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                loginBtnClick();
                break;
            case R.id.registerLink:
                registerLinkClick();
                break;
        }
    }

    private void bindLoginView() {
        loginBtn = (Button) findViewById(R.id.loginBtn);
        idText = (EditText) findViewById(R.id.loginIdText);
        passwordText = (EditText) findViewById(R.id.loginPasswordText);
        authGroup = (RadioGroup) findViewById(R.id.loginAuthGroup);
        studentBtn = (RadioButton) findViewById(R.id.studentRadioBtn);
        dorAdminBtn = (RadioButton) findViewById(R.id.dorAdminRadioBtn);
        adminBtn = (RadioButton) findViewById(R.id.adminRadioBtn);
        registerTextView = (TextView) findViewById(R.id.registerLink);
        authGroup.setOnCheckedChangeListener(this);
        loginBtn.setOnClickListener(this);
        registerTextView.setOnClickListener(this);
    }

    private void loginBtnClick() {
        if (idText.getText() == null || idText.getText().length() < 5) {
            Toast.makeText(context, "id不能为空或id格式不对", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordText.getText() == null || passwordText.getText().length() <= 0) {
            Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "";
        Map<String, String> paramMap = new ConcurrentHashMap<>();
        paramMap.put(PropertyName.ID.getEnValue(), idText.getText().toString());
        paramMap.put(PropertyName.PASSWORD.getEnValue(), passwordText.getText().toString());
        userTemp.setId(idText.getText().toString());
        switch (authGroup.getCheckedRadioButtonId()) {
            case R.id.studentRadioBtn:
                url = Constant.STUDENT_LOGIN_URL;
                paramMap.put(PropertyName.AUTH.getEnValue(), Authentication.STUDENT.name());
                userTemp.setAuthentication(Authentication.STUDENT);
                break;
            case R.id.dorAdminRadioBtn:
                url = Constant.DORADMIN_LOGIN_URL;
                paramMap.put(PropertyName.AUTH.getEnValue(), Authentication.DORMITORY_ADMINISTRATOR.name());
                userTemp.setAuthentication(Authentication.DORMITORY_ADMINISTRATOR);
                break;
            case R.id.adminRadioBtn:
                url = Constant.ADMIN_LOGIN_URL;
                paramMap.put(PropertyName.AUTH.getEnValue(), Authentication.ADMINISTRATOR.name());
                userTemp.setAuthentication(Authentication.ADMINISTRATOR);
                break;
            default:
                Toast.makeText(context, "请选择登录身份", Toast.LENGTH_SHORT).show();
                return;
        }
        RequestParams params = new RequestParams(paramMap);
        doLogin(url, params);
    }

    private void doLogin(String url, RequestParams params) {
        HttpUtil.addHeader("isFirst", String.valueOf(isFirst));
        HttpUtil.asyncPost(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_welcome_login);
                isFirst = true;
                bindLoginView();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (i == 200) {
                    ResponseResult<String> responseResult = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<String>>(){}.getType());
                    if (responseResult.getCode() == ResultType.SUCCESS.getCode()) {
                        Intent intent = new Intent();
                        switch (userTemp.getAuthentication()) {
                            case STUDENT:
                                intent = new Intent(context, StudentMainActivity.class);
                                break;
                            case DORMITORY_ADMINISTRATOR:
                                intent = new Intent(context, DorAdminMainActivity.class);
                                break;
                        }
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, responseResult.getMessage(), Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.activity_welcome_login);
                        bindLoginView();
                    }
                }
            }
        }, cookieStore);
    }

    private void registerLinkClick() {
        Intent intent = new Intent();
        switch (authGroup.getCheckedRadioButtonId()) {
            case R.id.studentRadioBtn:
                intent.setClass(context, StudentRegisterActivity.class);
                startActivityForResult(intent, AcitivtyCode.WELCOME.ordinal());
                break;
            case R.id.dorAdminRadioBtn:
                intent.setClass(context, DorAdminRegisterActivity.class);
                startActivityForResult(intent, AcitivtyCode.WELCOME.ordinal());
                break;
            case R.id.adminRadioBtn:

                break;
            default:
                Toast.makeText(context, "请选择身份", Toast.LENGTH_SHORT).show();
                return;
        }
    }

    private void login(List<Cookie> cookies) {
        RequestParams params = new RequestParams();
        String url = "";
        int paramsCount = 0;
        for (Cookie cookie :
                cookies) {
            if (cookie.getName().equals(PropertyName.ID.getEnValue())) {
                params.put(PropertyName.ID.getEnValue(), cookie.getValue());
                userTemp.setId(cookie.getValue());
                if (++paramsCount >= 3) {
                    break;
                }
            } else if (cookie.getName().equals(PropertyName.PASSWORD.getEnValue())) {
                params.put(PropertyName.PASSWORD.getEnValue(), cookie.getValue());
                if (++paramsCount >= 3) {
                    break;
                }
            } else if (cookie.getName().equals(PropertyName.AUTH.getEnValue())) {
                params.put(PropertyName.AUTH.getEnValue(), cookie.getValue());
                if (cookie.getValue().equals(Authentication.STUDENT.name())) {
                    url = Constant.STUDENT_LOGIN_URL;
                    userTemp.setAuthentication(Authentication.STUDENT);
                }
                else if (cookie.getValue().equals(Authentication.DORMITORY_ADMINISTRATOR.name())) {
                    url = Constant.DORADMIN_LOGIN_URL;
                    userTemp.setAuthentication(Authentication.DORMITORY_ADMINISTRATOR);
                }
                else if (cookie.getValue().equals(Authentication.ADMINISTRATOR.name())) {
                    url = Constant.ADMIN_LOGIN_URL;
                    userTemp.setAuthentication(Authentication.ADMINISTRATOR);
                }
                if (++paramsCount >= 3) {
                    break;
                }
            }
        }
        if (paramsCount < 3){
            setContentView(R.layout.activity_welcome_login);
            isFirst = true;
            bindLoginView();
            return;
        }
        doLogin(url, params);
    }
}
