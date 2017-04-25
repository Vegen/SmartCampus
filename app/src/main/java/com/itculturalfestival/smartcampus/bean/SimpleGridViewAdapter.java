package com.itculturalfestival.smartcampus.bean;

import android.content.Context;

import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.ui.fragment.main.AssistantFragment;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * @creation_time: 2017/4/23
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe:
 */

public class SimpleGridViewAdapter extends CommonAdapter<AssistantFragment.SimpleGridViewAdapterBean> {


    public SimpleGridViewAdapter(Context context, int layoutId, List<AssistantFragment.SimpleGridViewAdapterBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, AssistantFragment.SimpleGridViewAdapterBean simpleGridViewAdapterBean, int position) {
        holder.setImageResource(R.id.image, simpleGridViewAdapterBean.icon);
        holder.setText(R.id.text, simpleGridViewAdapterBean.text);
    }

}
