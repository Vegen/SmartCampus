package com.itculturalfestival.smartcampus.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itculturalfestival.smartcampus.Constant;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.entity.db.Lost;
import com.itculturalfestival.smartcampus.utils.GlideUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vegen on 2018/3/26.
 */

public class LostAndFoundAdapter extends BaseQuickAdapter<Lost, LostAndFoundAdapter.MyViewHolder> {

    public LostAndFoundAdapter() {
        super(R.layout.app_item_lost_found);
    }

    @Override
    protected void convert(MyViewHolder helper, Lost item) {
        if (item.getSolve()){
            helper.ivSolve.setVisibility(View.VISIBLE);
        }else {
            helper.ivSolve.setVisibility(View.GONE);
        }

        if (item.getType().equals(Constant.LOST)){
            helper.tvTypeLost.setVisibility(View.VISIBLE);
            helper.tvTypeFound.setVisibility(View.GONE);
        }else {
            helper.tvTypeLost.setVisibility(View.GONE);
            helper.tvTypeFound.setVisibility(View.VISIBLE);
        }

        // TODO 根据用户 id 检测是否是用户自己发的，是则显示已解决按钮
        if (item.getSmartUser() != null && item.getSmartUser().getObjectId().equals("")){
            helper.tvSolve.setVisibility(View.VISIBLE);
            helper.tvSolve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 点击则解决，更新改 item 信息
                    helper.ivSolve.setVisibility(View.VISIBLE);
                }
            });
        }else {
            helper.tvSolve.setVisibility(View.GONE);
        }

        helper.tvTitle.setText(item.getTitle());
        helper.tvDescription.setText(item.getDescription());
        helper.tvContact.setText(item.getContact());
        helper.tvBeTime.setText(item.getBeTime());
        helper.tvPublishTime.setText(item.getCreatedAt());
        GlideUtils.load(mContext, item.getImg(), helper.ivImg);
    }

    class MyViewHolder extends BaseViewHolder {

        @Bind(R.id.iv_img)
        ImageView ivImg;
        @Bind(R.id.tv_type_found)
        TextView tvTypeFound;
        @Bind(R.id.tv_solve)
        TextView tvSolve;
        @Bind(R.id.tv_type_lost)
        TextView tvTypeLost;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_description)
        TextView tvDescription;
        @Bind(R.id.tv_beTime)
        TextView tvBeTime;
        @Bind(R.id.tv_publish_time)
        TextView tvPublishTime;
        @Bind(R.id.iv_solve)
        ImageView ivSolve;
        @Bind(R.id.tv_contact)
        TextView tvContact;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
