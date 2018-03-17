package com.john_yim.dormitory.activity.adminstrator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.john_yim.dormitory.R;
import com.john_yim.dormitory.ViewAdapter;
import com.john_yim.dormitory.activity.InfoDetailActivity;
import com.john_yim.dormitory.constant.Authentication;
import com.john_yim.dormitory.constant.EntityType;
import com.john_yim.dormitory.entity.view.GridItem;

import java.util.ArrayList;
import java.util.List;

public class AdminMainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private BaseAdapter viewAdapter;
    private List<GridItem> gridItems;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        context = this;
        gridView = (GridView) findViewById(R.id.adminGridHome);
        gridItems = new ArrayList<>(9);
        gridItems.add(new GridItem(R.mipmap.student_info, "查看学生"));
        gridItems.add(new GridItem(R.mipmap.student_info, "查看宿管"));
        gridItems.add(new GridItem(R.mipmap.student_info, "学生入宿"));
        gridItems.add(new GridItem(R.mipmap.student_info, "宿管管理"));
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
        Bundle bundle = new Bundle();
        bundle.putSerializable("auth", Authentication.ADMINISTRATOR);
        Intent intent = new Intent();
        switch (i) {
            case 0:
                bundle.putString("function", "search");
                bundle.putSerializable("entity", EntityType.STUDENT);
                intent.setClass(context, InfoDetailActivity.class);
                break;
            case 1:
                bundle.putString("function", "search");
                bundle.putSerializable("entity", EntityType.DORMITORY_ADMIN);
                intent.setClass(context, InfoDetailActivity.class);
                break;
            case 2:
                bundle.putString("function", "edit");
                bundle.putSerializable("entity", EntityType.STUDENT);
                intent.setClass(context, InfoDetailActivity.class);
                break;
            case 3:
                bundle.putString("function", "edit");
                bundle.putSerializable("entity", EntityType.DORMITORY_ADMIN);
                intent.setClass(context, InfoDetailActivity.class);
                break;

        }
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
