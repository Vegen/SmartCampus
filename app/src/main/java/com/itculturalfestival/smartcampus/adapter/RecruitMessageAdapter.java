package com.itculturalfestival.smartcampus.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.entity.Recruit;
import com.itculturalfestival.smartcampus.utils.GlideUtils;

/**
 * Created by vegen on 2018/3/24.
 */

public class RecruitMessageAdapter extends BaseQuickAdapter<Recruit, BaseViewHolder> {

    public RecruitMessageAdapter() {
        super(R.layout.app_item_recruit_message);
    }

    @Override
    protected void convert(BaseViewHolder helper, Recruit item) {
        // 图片
        GlideUtils.load(mContext, item.getImgRes(), (ImageView) helper.getView(R.id.iv_img));
        // 标题
        helper.setText(R.id.tv_text, item.getTitle());
    }
}