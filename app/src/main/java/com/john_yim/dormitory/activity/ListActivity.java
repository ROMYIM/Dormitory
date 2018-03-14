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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.john_yim.dormitory.R;
import com.john_yim.dormitory.ViewAdapter;
import com.john_yim.dormitory.constant.Authentication;
import com.john_yim.dormitory.constant.BillType;
import com.john_yim.dormitory.constant.Constant;
import com.john_yim.dormitory.constant.EntityType;
import com.john_yim.dormitory.constant.ResultType;
import com.john_yim.dormitory.entity.Bill;
import com.john_yim.dormitory.entity.DorAdmin;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        View.OnClickListener {

    private EditText searchContent;
    private ListView listView;
    private BaseAdapter viewAdapter;
    private Context context;
    private EntityType type;
    private List<Notice> noticeList;
    private List<Repair> repairList;
    private List<Bill> billList;
    private List<Violation> violationList;
    private HashMap<String, String> paramMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ListActivity.this;
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        listView = (ListView) findViewById(R.id.list);
        listView.setVisibility(View.INVISIBLE);
        type = (EntityType) bundle.getSerializable("entity");
        Authentication authentication = (Authentication) bundle.getSerializable("auth");
        TextView title = (TextView) findViewById(R.id.activityTitle);
        Button searchBtn = (Button) findViewById(R.id.searchBtn);
        searchContent = (EditText) findViewById(R.id.searchStudentId);
        TitleBarUtil.setActivityTitleBar(authentication, title, searchContent, searchBtn);
        title.setText("查询结果");
        switch (type) {
            case BILL:
                paramMap = new HashMap<>();
                BillType billType = (BillType) intent.getSerializableExtra("type");
                paramMap.put("type", billType.name());
                BillResponseHandler billInfoResponseHandler = new BillResponseHandler();
                getInformation(Constant.STUDENT_LOOK_BILLS_URL, billInfoResponseHandler, paramMap);
                break;
            case NOTICE:
                NoticeResponseHandler noticeInfoResponseHandler = new NoticeResponseHandler();
                authentication = (Authentication) bundle.getSerializable("auth");
                title.setVisibility(View.VISIBLE);
                searchContent.setVisibility(View.INVISIBLE);
                searchBtn.setVisibility(View.INVISIBLE);
                if (authentication == Authentication.STUDENT) {
                    getInformation(Constant.STUDENT_LOOK_NOTICES_URL, noticeInfoResponseHandler, null);
                } else if (authentication == Authentication.DORMITORY_ADMINISTRATOR) {
                    getInformation(Constant.DORADMIN_LOOK_NOTICES_URL + "SEND", noticeInfoResponseHandler, null);
                }
                break;
            case REPAIR:
                RepairResponseHandler repairInfoResponseHandler = new RepairResponseHandler();
                getInformation(Constant.STUDENT_LOOK_REPAIRS_URL, repairInfoResponseHandler, null);
                break;
            case VIOLATION:
                if (authentication == Authentication.STUDENT) {
                    ViolationResponseHandler violationInfoResponseHandler = new ViolationResponseHandler();
                    getInformation(Constant.STUDENT_LOOK_VIOLATIONS_URL, violationInfoResponseHandler, null);
                } else if (authentication == Authentication.DORMITORY_ADMINISTRATOR) {
                    paramMap = new HashMap<>();
                    searchBtn.setOnClickListener(this);
                }
                break;
            case STUDENT:
                if (authentication == Authentication.ADMINISTRATOR) {
                    getInformation(Constant.ADMIN_LOOK_STUDENTS_URL, new StudentResponseHandler(), null);
                }
                break;
            case DORMITORY_ADMIN:
                if (authentication == Authentication.ADMINISTRATOR) {
                    getInformation(Constant.ADMIN_LOOK_DORADMINS_URL, new DorAdminResponseHandler(), null);
                }
        }
        listView.setOnItemClickListener(this);
    }

    private void getInformation(String url, TextHttpResponseHandler responseHandler, Map<String, String> otherParams) {
        RequestParams params = new RequestParams();
        if (otherParams != null && otherParams.size() > 0) {
           switch (url) {
               case Constant.STUDENT_LOOK_BILLS_URL:
                   params.put("type", otherParams.get("type"));
                   break;
               case Constant.DORADMIN_LOOK_VIOLATIONS_URL:
                   params.put("studentId", otherParams.get("studentId"));
                   break;
           }
        }
        listView.setVisibility(View.VISIBLE);
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        HttpUtil.asyncGet(url, params, responseHandler, cookieStore);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(context, InfoDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", type);
        bundle.putSerializable("auth", Authentication.STUDENT);
        switch (type) {
            case NOTICE:
                bundle.putSerializable(EntityType.NOTICE.name(), noticeList.get(i));
                break;
            case REPAIR:
                bundle.putSerializable(EntityType.REPAIR.name(), repairList.get(i));
                break;
            case VIOLATION:
                bundle.putSerializable(EntityType.VIOLATION.name(), violationList.get(i));
                break;
            default:
                return;
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchBtn:
                if (type == EntityType.VIOLATION) {
                    paramMap.put("studentId", searchContent.getText().toString());
                    ViolationResponseHandler violationInfoResponseHandler = new ViolationResponseHandler();
                    getInformation(Constant.DORADMIN_LOOK_VIOLATIONS_URL, violationInfoResponseHandler, paramMap);
                } else if (type == EntityType.STUDENT) {

                } else if (type == EntityType.DORMITORY_ADMIN) {

                }
                break;
        }
    }

    private class NoticeResponseHandler extends TextHttpResponseHandler {

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            Toast.makeText(context, "查询失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {
            if (i == 200) {
                try {
                    ResponseResult<List<Notice>> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<List<Notice>>>(){}.getType());
                    noticeList = result.getResult();
                    if (noticeList != null && noticeList.size() > 0) {
                        viewAdapter = new ViewAdapter<Notice>(noticeList, R.layout.item_list_notice) {
                            @Override
                            public void bindView(ViewHolder holder, Notice obj) {
                                holder.setText(R.id.noticeId, String.valueOf(obj.getId()));
                                holder.setText(R.id.noticeSendDate, DateUtil.dateToString("yyyy-MM-dd", obj.getSendDate()));
                                holder.setText(R.id.noticeText, obj.getContent());
                            }
                        };
                        listView.setAdapter(viewAdapter);
                        viewAdapter.notifyDataSetChanged();
                    }
                } catch (JsonParseException e) {
                    ResponseResult<String> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<String>>(){}.getType());
                    if (result.getCode() == ResultType.ERR_LOGIN.getCode()) {
                        Intent intent = new Intent(context, WelcomeActivity.class);
                        startActivity(intent);
                    }
                    Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class RepairResponseHandler extends TextHttpResponseHandler {

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            Toast.makeText(context, "查询失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {
            if (i == 200) {
                try {
                    ResponseResult<List<Repair>> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<List<Repair>>>(){}.getType());
                    repairList = result.getResult();
                    if (repairList != null && repairList.size() > 0) {
                        if (viewAdapter == null) {
                            viewAdapter = new ViewAdapter<Repair>(repairList, R.layout.item_list_repair) {
                                @Override
                                public void bindView(ViewHolder holder, Repair obj) {
                                    holder.setText(R.id.repairId, String.valueOf(obj.getId()));
                                    holder.setText(R.id.repairType, obj.getType().getValue());
                                    holder.setText(R.id.repairState, obj.getStatus().getValue());
                                    holder.setText(R.id.repairText, obj.getContent());
                                    holder.setText(R.id.repairSendDate, DateUtil.dateToString("yyyy-MM-dd",obj.getSendDate()));
                                }
                            };
                            listView.setAdapter(viewAdapter);
                        }
                        viewAdapter.notifyDataSetChanged();
                    }
                } catch (JsonParseException e) {
                    ResponseResult<String> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<String>>(){}.getType());
                    if (result.getCode() == ResultType.ERR_LOGIN.getCode()) {
                        Intent intent = new Intent(context, WelcomeActivity.class);
                        startActivity(intent);
                    }
                    Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class ViolationResponseHandler extends TextHttpResponseHandler {

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            Toast.makeText(context, "查询失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {
            if (i == 200) {
                try {
                    ResponseResult<List<Violation>> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<List<Violation>>>(){}.getType());
                    violationList = result.getResult();
                    if (violationList != null && violationList.size() > 0) {
                        if (viewAdapter == null) {
                            viewAdapter = new ViewAdapter<Violation>(violationList, R.layout.item_list_violation) {
                                @Override
                                public void bindView(ViewHolder holder, Violation obj) {
                                    holder.setText(R.id.violationId, String.valueOf(obj.getId()));
                                    holder.setText(R.id.violationSendDate, DateUtil.dateToString("yyyy-MM-dd", obj.getSendDate()));
                                    holder.setText(R.id.violationType, obj.getType().getDescription());
                                    holder.setText(R.id.violationText, obj.getContent());
                                }
                            };
                            listView.setAdapter(viewAdapter);
                        }
                        viewAdapter.notifyDataSetChanged();
                    }
                } catch (JsonParseException e) {
                    ResponseResult<String> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<String>>(){}.getType());
                    if (result.getCode() == ResultType.ERR_LOGIN.getCode()) {
                        Intent intent = new Intent(context, WelcomeActivity.class);
                        startActivity(intent);
                    }
                    Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class BillResponseHandler extends TextHttpResponseHandler {

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            Toast.makeText(context, "查询失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {
            if (i == 200) {
                try {
                    ResponseResult<List<Bill>> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<List<Bill>>>(){}.getType());
                    billList = result.getResult();
                    if (billList != null && billList.size() > 0) {
                        viewAdapter = new ViewAdapter<Bill>(billList, R.layout.item_list_bill) {
                            @Override
                            public void bindView(ViewHolder holder, Bill obj) {
                                holder.setText(R.id.billSendDate, DateUtil.dateToString("yyyy-MM-dd", obj.getPayDate()));
                                holder.setText(R.id.billType, obj.getType().name());
                                holder.setText(R.id.billMoney, String.valueOf(obj.getPayMoney()));
                            }
                        };
                        listView.setAdapter(viewAdapter);
                        viewAdapter.notifyDataSetChanged();
                    }
                } catch (JsonParseException e) {
                    ResponseResult<String> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<String>>(){}.getType());
                    if (result.getCode() == ResultType.ERR_LOGIN.getCode()) {
                        Intent intent = new Intent(context, WelcomeActivity.class);
                        startActivity(intent);
                    }
                    Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class StudentResponseHandler extends TextHttpResponseHandler {

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            Toast.makeText(context, "获取学生列表失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {
            if (i == 200) {
                try {
                    ResponseResult<List<Student>> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<List<Student>>>(){}.getType());
                    viewAdapter = new ViewAdapter<Student>(result.getResult(), R.layout.item_list_student) {
                        @Override
                        public void bindView(ViewHolder holder, Student obj) {
                            holder.setText(R.id.studentPlane, obj.getId());
                        }
                    };
                    listView.setAdapter(viewAdapter);
                } catch (JsonParseException e) {
                    ResponseResult<String> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<String>>(){}.getType());
                    Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class DorAdminResponseHandler extends TextHttpResponseHandler {

        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            Toast.makeText(context, "查询失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {
            if (i == 200) {
                try {
                    ResponseResult<List<DorAdmin>> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<List<DorAdmin>>>(){}.getType());
                    viewAdapter = new ViewAdapter<DorAdmin>(result.getResult(), R.layout.item_list_doradmin) {
                        @Override
                        public void bindView(ViewHolder holder, DorAdmin obj) {
                            holder.setText(R.id.dorAdminPlane, obj.getId());
                        }
                    };
                    listView.setAdapter(viewAdapter);
                } catch (JsonParseException e) {
                    ResponseResult<String> result = ResponseUtil.getResult(s,
                            new TypeToken<ResponseResult<String>>(){}.getType());
                    Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
