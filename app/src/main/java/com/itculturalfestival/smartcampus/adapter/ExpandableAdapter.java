package com.itculturalfestival.smartcampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.bean.team.ExpandableListBean;
import com.itculturalfestival.smartcampus.ui.custom.CircleImageView;

import java.util.List;

/**
 * @creation_time: 2017/4/9
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe:
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {

    private List<ExpandableListBean> groupArray;
    private List<List<ExpandableListBean>> childArray;
    private Context mContext;

    public ExpandableAdapter(Context context, List<ExpandableListBean> groupArray, List<List<ExpandableListBean>> childArray){
        mContext = context;
        this.groupArray = groupArray;
        this.childArray = childArray;
    }

    @Override
    public int getGroupCount() {
        return groupArray.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childArray.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupArray.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childArray.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        GroupHolder holder = null;
        if(view == null){
            holder = new GroupHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.view_expandlist_group_team, null);
            holder.civ_head = (CircleImageView) view.findViewById(R.id.civ_head);
            holder.tv_group = (TextView)view.findViewById(R.id.tv_group);
            holder.iv_status = (ImageView)view.findViewById(R.id.iv_status);
            view.setTag(holder);
        }else{
            holder = (GroupHolder)view.getTag();
        }
//        Log.e("getGroupView", "groupPosition=" + groupPosition + "  isExpanded=" + isExpanded);
        //判断是否已经打开列表
        if(isExpanded){
            holder.iv_status.setBackgroundResource(R.mipmap.ic_up);
        }else{
            holder.iv_status.setBackgroundResource(R.mipmap.ic_drop);
        }

        if (groupArray.size() == 3) {
            if (groupPosition == 0) {
                Glide.with(mContext).load(R.drawable.ic_friend).into(holder.civ_head);
            }else if (groupPosition == 1){
                Glide.with(mContext).load(R.drawable.ic_group_team).into(holder.civ_head);
            }else {
                Glide.with(mContext).load(R.drawable.ic_my_team).into(holder.civ_head);
            }
        }
        if (groupArray.size() == 1){
            if (groupPosition == 0) {
                Glide.with(mContext).load(R.drawable.ic_my_team).into(holder.civ_head);
            }
        }
        holder.tv_group.setText(groupArray.get(groupPosition).getWhat());
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        ChildHolder holder = null;
        if(view == null){
            holder = new ChildHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.view_expandlist_item_team, null);
            holder.tv_item = (TextView)view.findViewById(R.id.tv_item);
            holder.civ_head = (CircleImageView) view.findViewById(R.id.civ_head);
            view.setTag(holder);
        }else{
            holder = (ChildHolder)view.getTag();
        }
        holder.tv_item.setText(childArray.get(groupPosition).get(childPosition).getWhat());
        Glide.with(mContext).load(childArray.get(groupPosition).get(childPosition).getHead()).into(holder.civ_head);

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder{
        public CircleImageView civ_head;
        public TextView tv_group;
        private ImageView iv_status;
    }

    class ChildHolder{
        public CircleImageView civ_head;
        public TextView tv_item;
    }
}
