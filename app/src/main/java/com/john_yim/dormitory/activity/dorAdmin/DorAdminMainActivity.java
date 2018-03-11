package com.john_yim.dormitory.activity.dorAdmin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.john_yim.dormitory.R;
import com.john_yim.dormitory.ViewAdapter;
import com.john_yim.dormitory.activity.InfoDetailActivity;
import com.john_yim.dormitory.activity.ListActivity;
import com.john_yim.dormitory.activity.WelcomeActivity;
import com.john_yim.dormitory.constant.Authentication;
import com.john_yim.dormitory.constant.Constant;
import com.john_yim.dormitory.constant.EntityType;
import com.john_yim.dormitory.constant.ResultType;
import com.john_yim.dormitory.entity.ResponseResult;
import com.john_yim.dormitory.entity.view.GridItem;
import com.john_yim.dormitory.util.HttpUtil;
import com.john_yim.dormitory.util.ResponseUtil;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

public class DorAdminMainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private GridView gridView;
    private BaseAdapter viewAdapter;
    private List<GridItem> gridItems;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doradmin);
        context = this;
        gridView = (GridView) findViewById(R.id.dorAdminGridHome);
        gridItems = new ArrayList<>(9);
        gridItems.add(new GridItem(R.mipmap.student_info, "查看学生"));
        gridItems.add(new GridItem(R.mipmap.dormitory_inof, "查宿舍"));
        gridItems.add(new GridItem(R.mipmap.notice_list, "查公告"));
        gridItems.add(new GridItem(R.mipmap.notice_list, "发布公告"));
        gridItems.add(new GridItem(R.mipmap.add_violation, "登记违规"));
        gridItems.add(new GridItem(R.mipmap.look_violation, "查看违规"));
        gridItems.add(new GridItem(R.mipmap.logout, "登出"));
        viewAdapter = new ViewAdapter<GridItem>(gridItems, R.layout.item_grid_home) {
            @Override
            public void bindView(ViewHolder holder, GridItem obj) {
                holder.setImageResource(R.id.itemIcon, obj.getId());
                holder.setText(R.id.itemText, obj.getName());
            }
        };
        gridView.setAdapter(viewAdapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (i) {
            case 0:
                intent.setClass(context, InfoDetailActivity.class);
                intent.putExtra("auth", Authentication.DORMITORY_ADMINISTRATOR);
                intent.putExtra("searchTarget", "student");
                startActivity(intent);
                break;
            case 1:
                intent.setClass(context, InfoDetailActivity.class);
                intent.putExtra("auth", Authentication.DORMITORY_ADMINISTRATOR);
                intent.putExtra("searchTarget", "dormitory");
                startActivity(intent);
                break;
            case 2:
                intent.setClass(context, ListActivity.class);
                bundle.putSerializable("auth", Authentication.DORMITORY_ADMINISTRATOR);
                bundle.putSerializable("entity", EntityType.NOTICE);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 3:
                intent.setClass(context, DorAdminNoticeActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent.setClass(context, DorAdminViolationActivity.class);
                startActivity(intent);
                break;
            case 5:
                intent.setClass(context, ListActivity.class);
                bundle.putSerializable("auth", Authentication.DORMITORY_ADMINISTRATOR);
                bundle.putSerializable("entity", EntityType.VIOLATION);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 6:
                PersistentCookieStore cookieStore = new PersistentCookieStore(context);
                HttpUtil.asyncGet(Constant.DORADMIN_LOGOUT_URL, null, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                        Toast.makeText(context, "登出失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
                        if (i == 200) {
                            ResponseResult<String> result = ResponseUtil.getResult(s,
                                    new TypeToken<ResponseResult<String>>(){}.getType());
                            Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                            if (result.getCode() == ResultType.SUCCESS.getCode()) {
                                intent.setClass(context, WelcomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                }, cookieStore);
        }
    }
}
