package com.john_yim.dormitory.activity.dorAdmin;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.john_yim.dormitory.R;
import com.john_yim.dormitory.constant.Constant;
import com.john_yim.dormitory.constant.ResultType;
import com.john_yim.dormitory.entity.ResponseResult;
import com.john_yim.dormitory.util.HttpUtil;
import com.john_yim.dormitory.util.ResponseUtil;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

public class DorAdminNoticeActivity extends AppCompatActivity implements View.OnClickListener{

    private Context context;
    EditText noticeContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doradmin_notice);
        context = this;
        Button sendBtn = (Button) findViewById(R.id.noticeSendBtn);
        noticeContent = (EditText) findViewById(R.id.noticeContent);
        sendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        RequestParams params = new RequestParams("content", noticeContent.getText().toString());
        HttpUtil.asyncPost(Constant.DORADMIN_ADD_NOTICE_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(context, "添加公告失败", Toast.LENGTH_SHORT).show();
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
        }, cookieStore);
    }
}
