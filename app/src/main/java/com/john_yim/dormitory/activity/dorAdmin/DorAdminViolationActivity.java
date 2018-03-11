package com.john_yim.dormitory.activity.dorAdmin;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.john_yim.dormitory.R;
import com.john_yim.dormitory.constant.Constant;
import com.john_yim.dormitory.constant.ResultType;
import com.john_yim.dormitory.constant.ViolationType;
import com.john_yim.dormitory.entity.ResponseResult;
import com.john_yim.dormitory.util.HttpUtil;
import com.john_yim.dormitory.util.ResponseUtil;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

public class DorAdminViolationActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener{
    private Context context;
    private ViolationType type;
    private EditText violationContent;
    private EditText studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doradmin_violation);
        context = this;
        Button sendBtn = (Button) findViewById(R.id.sendViolationBtn);
        Spinner violationType = (Spinner) findViewById(R.id.violationTypeSpinner);
        violationContent = (EditText) findViewById(R.id.violationContent);
        studentId = (EditText) findViewById(R.id.violationStudentId);
        violationType.setOnItemSelectedListener(this);
        sendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (type == null) {
            Toast.makeText(context, "请选择违规类型", Toast.LENGTH_SHORT).show();
            return;
        }
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        RequestParams params = new RequestParams();
        params.put("type", type.name());
        params.put("content",violationContent.getText().toString());
        HttpUtil.asyncPost(Constant.DORADMIN_ADD_VIOLATION_URL + studentId.getText().toString(),
                params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                        Toast.makeText(context, "添加违规失败", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0)
            return;
        switch (i - 1) {
            case 0:
                type = ViolationType.VANDALISM;
                break;
            case 1:
                type = ViolationType.LATE_BACK;
                break;
            case 2:
                type = ViolationType.ABUSE;
                break;
            case 3:
                type = ViolationType.CLIMB_OVER;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
