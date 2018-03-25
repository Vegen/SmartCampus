package com.itculturalfestival.smartcampus.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.itculturalfestival.smartcampus.Constant;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.entity.Employment;
import com.itculturalfestival.smartcampus.network.Url;
import com.itculturalfestival.smartcampus.ui.main.home.topfun.TopFunArticleDetailActivity;

import java.util.List;

/**
 * Created by Huguang on 2018/3/25.
 */

public class EmploymentListAdapter extends BaseQuickAdapter<Employment, BaseViewHolder> {

    public EmploymentListAdapter() {
        super(R.layout.app_item_employment_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, Employment item) {
        helper.setText(R.id.tv_title, item.getTitle());
        if (item.getContent() == null || item.getContent().isEmpty()) {
            helper.setVisible(R.id.tv_content, false);
        }else {
            helper.setVisible(R.id.tv_content, true);
            helper.setText(R.id.tv_content, item.getContent());
        }
        helper.setText(R.id.tv_tips, item.getTips());
        helper.setText(R.id.tv_time, item.getTime());
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopFunArticleDetailActivity.start(mContext, item.getTitle(), Url.JYSD_URL + item.getNextUrl(), Constant.MESSAGE_EMPLOYMENT);
            }
        });
    }
}
