package com.itculturalfestival.smartcampus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.entity.InformationDB;
import com.itculturalfestival.smartcampus.util.DateUtil;
import com.itculturalfestival.smartcampus.util.L;

import java.util.Date;
import java.util.List;

/**
 * @creation_time: 2017/4/5
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 资讯RecyclerView适配器
 */

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<InformationDB> informationList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public RecyclerAdapter(Context context, List<InformationDB> informationList){
        this.context = context;
        this.informationList = informationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_information_item, parent, false);
            view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            return new ItemViewHolder(view);
        }else if (viewType == TYPE_FOOTER){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_item_foot, parent, false);
            view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            if (onItemClickListener != null) {
//                holder.getLayoutPosition();
//                ll_item_information
                InformationDB informationBean = informationList.get(position);
                ((ItemViewHolder) holder).tv_title.setText(informationBean.getTitle());
                ((ItemViewHolder) holder).tv_source.setText(informationBean.getSource());
                ((ItemViewHolder) holder).tv_time.setText(DateUtil.showTime(informationBean.getCtime()));
                Glide.with(context).load(informationBean.getIllustration()).into(((ItemViewHolder) holder).iv_illustration);
                ((ItemViewHolder) holder).ll_item_information.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(((ItemViewHolder) holder).ll_item_information, position);
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        //最后一个item设置为footerView
        if (position + 1 == getItemCount()){
            return  TYPE_FOOTER;
        }
        return  TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return informationList.size() ==  0 ? 0 : informationList.size() + 1;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    static class FooterViewHolder extends ViewHolder{

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class ItemViewHolder extends ViewHolder{
        TextView tv_title;
        TextView tv_source;
        TextView tv_time;
        ImageView iv_illustration;
        LinearLayout ll_item_information;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_source = (TextView) itemView.findViewById(R.id.tv_source);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_illustration = (ImageView) itemView.findViewById(R.id.iv_illustration);
            ll_item_information = (LinearLayout) itemView.findViewById(R.id.ll_item_information);
        }
    }

}
