package com.john_yim.dormitory.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.john_yim.dormitory.R;
import com.john_yim.dormitory.ViewAdapter;
import com.john_yim.dormitory.entity.view.ForumListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {

    private List<ForumListItem> forumListItems;
    private BaseAdapter viewAdapter;
    private ListView listView;
    private Context context;

    public GroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_group, container, false);
        listView = rootView.findViewById(R.id.forumList);
        context = getContext();
        forumListItems = new ArrayList<>();
        forumListItems.add(new ForumListItem("owner1", "2014-01-01", "title1", "回复：0", "赞：0"));
        forumListItems.add(new ForumListItem("owner2", "2014-01-01", "title2", "回复：2", "赞：2"));
        forumListItems.add(new ForumListItem("owner3", "2014-01-01", "title3", "回复：3", "赞：3"));
        forumListItems.add(new ForumListItem("owner4", "2014-01-01", "title4", "回复：4", "赞：4"));
        forumListItems.add(new ForumListItem("owner5", "2014-01-01", "title5", "回复：0", "赞：0"));
        forumListItems.add(new ForumListItem("owner6", "2014-01-01", "title6", "回复：0", "赞：0"));
        forumListItems.add(new ForumListItem("owner7", "2014-01-01", "title7", "回复：0", "赞：0"));
        forumListItems.add(new ForumListItem("owner8", "2014-01-01", "title8", "回复：0", "赞：0"));
        viewAdapter = new ViewAdapter<ForumListItem>(forumListItems, R.layout.item_list_group) {
            @Override
            public void bindView(ViewHolder holder, ForumListItem obj) {
                holder.setText(R.id.postOwner, obj.getOwnerName());
                holder.setText(R.id.postSendDate, obj.getSendDate());
                holder.setText(R.id.forumTitle, obj.getTitle());
                holder.setText(R.id.replyCount, obj.getReplyCount());
                holder.setText(R.id.goodCount, obj.getGoodCount());
            }
        };
        listView.setAdapter(viewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "你点击了~" + i + "~项", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

}
