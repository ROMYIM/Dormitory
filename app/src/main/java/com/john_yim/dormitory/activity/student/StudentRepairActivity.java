package com.john_yim.dormitory.activity.student;

import android.content.Context;
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
import com.john_yim.dormitory.constant.Constant;
import com.john_yim.dormitory.constant.RepairStatus;
import com.john_yim.dormitory.constant.RepairType;
import com.john_yim.dormitory.constant.ResultType;
import com.john_yim.dormitory.entity.ResponseResult;
import com.john_yim.dormitory.util.HttpUtil;
import com.john_yim.dormitory.util.ResponseUtil;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

public class StudentRepairActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        View.OnClickListener{

    private RepairType type;
    private EditText repairContent;
    private RadioButton electricalBtn;
    private RadioButton waterBtn;
    private RadioButton windowBtn;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_student_repair);
        repairContent = (EditText) findViewById(R.id.repairContent);
        electricalBtn = (RadioButton) findViewById(R.id.repairElectricalType);
        waterBtn = (RadioButton) findViewById(R.id.repairWaterType);
        windowBtn = (RadioButton) findViewById(R.id.repairWindowType);
        RadioGroup repairTypeGroup = (RadioGroup) findViewById(R.id.repairType);
        Button repairBtn = (Button) findViewById(R.id.repairBtn);
        repairTypeGroup.setOnCheckedChangeListener(this);
        repairBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (type == null) {
            Toast.makeText(context, "请选择维修类型", Toast.LENGTH_SHORT).show();
            return;
        }
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        RequestParams params = new RequestParams();
        params.put("content", repairContent.getText());
        params.put("type", type.name());
        params.put("status", RepairStatus.BROKEN.name());
//        params.put("sendDate", DateUtil.dateToString("yyyy-MM-dd", new Date()));
        HttpUtil.asyncPost(Constant.STUDENT_DECLARE_REPAIR_URL, params, new ResponseHandler(), cookieStore);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.repairWaterType:
                type = RepairType.WATER_HEATER;
                waterBtn.setChecked(true);
                break;
            case R.id.repairElectricalType:
                type = RepairType.ELECTRICAL;
                electricalBtn.setChecked(true);
                break;
            case R.id.repairWindowType:
                type = RepairType.DOOR_WINDOW;
                windowBtn.setChecked(true);
                break;
        }
    }

    private class ResponseHandler extends TextHttpResponseHandler {

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            Toast.makeText(context, "维修申请失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {
            if (i == 200) {
                ResponseResult<String> result = ResponseUtil.getResult(s,
                        new TypeToken<ResponseResult<String>>(){}.getType());
                if (result.getCode() == ResultType.SUCCESS.getCode()) {
                    finish();
                } else {
                    Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
