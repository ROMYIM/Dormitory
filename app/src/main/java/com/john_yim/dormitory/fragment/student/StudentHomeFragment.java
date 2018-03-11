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
import android.widget.GridView;

import com.john_yim.dormitory.R;
import com.john_yim.dormitory.ViewAdapter;
import com.john_yim.dormitory.activity.ListActivity;
import com.john_yim.dormitory.activity.student.StudentPayBillActivity;
import com.john_yim.dormitory.activity.student.StudentRepairActivity;
import com.john_yim.dormitory.constant.Authentication;
import com.john_yim.dormitory.constant.BillType;
import com.john_yim.dormitory.constant.EntityType;
import com.john_yim.dormitory.entity.view.GridItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentHomeFragment extends Fragment {

    private GridView gridView;
    private BaseAdapter viewAdapter;
    private List<GridItem> gridItems;
    private Context context;

    public StudentHomeFragment() {
        // Required empty public constructor
        gridItems = new ArrayList<>(9);
        gridItems.add(new GridItem(R.mipmap.pay_eletricity, "缴电费"));
        gridItems.add(new GridItem(R.mipmap.pay_water, "缴水费"));
        gridItems.add(new GridItem(R.mipmap.repair, "报修"));
        gridItems.add(new GridItem(R.mipmap.eletricty_list, "查电费"));
        gridItems.add(new GridItem(R.mipmap.water_list, "查水费"));
        gridItems.add(new GridItem(R.mipmap.repair_list, "查报修"));
        gridItems.add(new GridItem(R.mipmap.notice_list, "查公告"));
        gridItems.add(new GridItem(R.mipmap.notice_list, "查违规"));
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_student_home, container, false);
        context = getContext();
        gridView = rootView.findViewById(R.id.gridHome);
        viewAdapter = new ViewAdapter<GridItem>(gridItems, R.layout.item_grid_home) {
            @Override
            public void bindView(ViewHolder holder, GridItem obj) {
                holder.setImageResource(R.id.itemIcon, obj.getId());
                holder.setText(R.id.itemText, obj.getName());
            }
        };
        gridView.setAdapter(viewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                switch (i) {
                    case 0:
                        intent.setClass(context, StudentPayBillActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.setClass(context, StudentPayBillActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent.setClass(context, StudentRepairActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent.setClass(context, ListActivity.class);
                        bundle.putSerializable("entity", EntityType.BILL);
                        bundle.putSerializable("type", BillType.ELECTRIC);
                        bundle.putSerializable("auth", Authentication.STUDENT);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 4:
                        intent.setClass(context, ListActivity.class);
                        bundle.putSerializable("entity", EntityType.BILL);
                        bundle.putSerializable("type", BillType.WATER);
                        bundle.putSerializable("auth", Authentication.STUDENT);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 5:
                        intent.setClass(context, ListActivity.class);
                        bundle.putSerializable("entity", EntityType.REPAIR);
                        bundle.putSerializable("auth", Authentication.STUDENT);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 6:
                        intent.setClass(context, ListActivity.class);
                        bundle.putSerializable("entity", EntityType.NOTICE);
                        bundle.putSerializable("auth", Authentication.STUDENT);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 7:
                        intent.setClass(context, ListActivity.class);
                        bundle.putSerializable("entity", EntityType.VIOLATION);
                        bundle.putSerializable("auth", Authentication.STUDENT);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                }
            }
        });
        return rootView;
    }

}
