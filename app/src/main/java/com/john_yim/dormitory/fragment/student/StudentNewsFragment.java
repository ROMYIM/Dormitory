package com.john_yim.dormitory.fragment.student;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.john_yim.dormitory.R;
import com.john_yim.dormitory.ViewAdapter;
import com.john_yim.dormitory.activity.InfoDetailActivity;
import com.john_yim.dormitory.activity.WelcomeActivity;
import com.john_yim.dormitory.constant.Authentication;
import com.john_yim.dormitory.constant.Constant;
import com.john_yim.dormitory.constant.EntityType;
import com.john_yim.dormitory.constant.ResultType;
import com.john_yim.dormitory.entity.Notice;
import com.john_yim.dormitory.entity.Repair;
import com.john_yim.dormitory.entity.ResponseResult;
import com.john_yim.dormitory.util.DateUtil;
import com.john_yim.dormitory.util.HttpUtil;
import com.john_yim.dormitory.util.ResponseUtil;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentNewsFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView noticeListView;
    private ListView repairListView;
    private List<Notice> noticeList;
    private List<Repair> repairList;
    private Context context;
    private BaseAdapter noticeAdapter;
    private BaseAdapter repairAdapter;
    private PersistentCookieStore cookieStore;

    public StudentNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_student_news, container, false);
        context = getContext();
        noticeListView = rootView.findViewById(R.id.noticeList);
        repairListView = rootView.findViewById(R.id.repairList);
        cookieStore = new PersistentCookieStore(context);
        NoticeResponseHandler noticeInfoResponseHandler = new NoticeResponseHandler();
        getInformation(Constant.STUDENT_LOOK_NOTICES_URL, noticeInfoResponseHandler);
        RepairResponseHandler repairInfoResponseHandler = new RepairResponseHandler();
        getInformation(Constant.STUDENT_LOOK_REPAIRS_URL, repairInfoResponseHandler);
        noticeListView.setOnItemClickListener(this);
        repairListView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(context, InfoDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("auth", Authentication.STUDENT);
        switch (adapterView.getId()) {
            case R.id.noticeList:
                bundle.putSerializable("entity", EntityType.NOTICE);
                bundle.putSerializable(EntityType.NOTICE.name(), (Notice) noticeAdapter.getItem(i));
                break;
            case R.id.repairList:
                intent.putExtra("entity", EntityType.REPAIR);
                bundle.putSerializable(EntityType.REPAIR.name(), (Repair) repairAdapter.getItem(i));
                break;
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getInformation(final String url, TextHttpResponseHandler responseHandler) {
        HttpUtil.asyncGet(url, null, responseHandler, cookieStore);
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
                        if (noticeAdapter == null) {
                            noticeAdapter = new ViewAdapter<Notice>(noticeList, R.layout.item_list_notice) {
                                @Override
                                public void bindView(ViewHolder holder, Notice obj) {
                                    holder.setText(R.id.noticeId, String.valueOf(obj.getId()));
                                    holder.setText(R.id.noticeSendDate, DateUtil.dateToString("yyyy-MM-dd", obj.getSendDate()));
                                    holder.setText(R.id.noticeText, obj.getContent());
                                }
                            };
                            noticeListView.setAdapter(noticeAdapter);
                        }
                        noticeAdapter.notifyDataSetChanged();
                    }
                } catch (JsonParseException e) {
                    System.out.println(e.getMessage());
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
                        if (repairAdapter == null) {
                            repairAdapter = new ViewAdapter<Repair>(repairList, R.layout.item_list_repair) {
                                @Override
                                public void bindView(ViewHolder holder, Repair obj) {
                                    holder.setText(R.id.repairId, String.valueOf(obj.getId()));
                                    holder.setText(R.id.repairType, obj.getType().getValue());
                                    holder.setText(R.id.repairState, obj.getStatus().getValue());
                                    holder.setText(R.id.repairText, obj.getContent());
                                    holder.setText(R.id.repairSendDate, DateUtil.dateToString("yyyy-MM-dd",obj.getSendDate()));
                                }
                            };
                            repairListView.setAdapter(repairAdapter);
                        }
                        repairAdapter.notifyDataSetChanged();
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
}
