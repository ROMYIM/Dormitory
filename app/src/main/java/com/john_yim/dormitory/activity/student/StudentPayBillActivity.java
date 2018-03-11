package com.john_yim.dormitory.activity.student;

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
import com.john_yim.dormitory.constant.BillType;
import com.john_yim.dormitory.constant.Constant;
import com.john_yim.dormitory.constant.ResultType;
import com.john_yim.dormitory.entity.ResponseResult;
import com.john_yim.dormitory.util.HttpUtil;
import com.john_yim.dormitory.util.ResponseUtil;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

public class StudentPayBillActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        View.OnClickListener{

    private BillType type;
    private Context context;
    private EditText billText;
    private RadioButton waterTypeBtn;
    private RadioButton electricTypeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_bill);
        context = StudentPayBillActivity.this;
        billText = (EditText) findViewById(R.id.billMoney);
        Button payBillBtn = (Button) findViewById(R.id.payBillBtn);
        RadioGroup billTypeGroup = (RadioGroup) findViewById(R.id.billType);
        billTypeGroup.setOnCheckedChangeListener(this);
        payBillBtn.setOnClickListener(this);
        waterTypeBtn = (RadioButton) findViewById(R.id.waterType);
        electricTypeBtn = (RadioButton) findViewById(R.id.electricType);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (i) {
            case R.id.waterType:
                type = BillType.WATER;
                waterTypeBtn.setChecked(true);
                break;
            case R.id.electricType:
                type = BillType.ELECTRIC;
                electricTypeBtn.setChecked(true);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (type == null) {
            Toast.makeText(context, "请选择缴费项目", Toast.LENGTH_SHORT).show();
            return;
        }
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        RequestParams params = new RequestParams();
        params.put("bills", billText.getText());
        HttpUtil.asyncPost(Constant.STUDENT_PAY_BILLS_URL + type.name(), params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(context, "交易失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (i == 200) {
                    try {
                        ResponseResult<String> result = ResponseUtil.getResult(s,
                                new TypeToken<ResponseResult<String>>(){}.getType());
                        if (result.getCode() == ResultType.SUCCESS.getCode()) {
                            Intent intent = new Intent(context, StudentMainActivity.class);
                            startActivity(intent);
                            return;
                        }
                    }  catch (Exception e) {
                        System.out.println(e.getMessage());
                        Toast.makeText(context, "交易失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, cookieStore);
    }
}
