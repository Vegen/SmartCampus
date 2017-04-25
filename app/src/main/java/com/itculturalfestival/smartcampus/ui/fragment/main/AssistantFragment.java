package com.itculturalfestival.smartcampus.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.bean.SimpleGridViewAdapter;
import com.itculturalfestival.smartcampus.ui.activity.information.InformationActivity;
import com.itculturalfestival.smartcampus.ui.activity.record.RecordActivity;
import com.itculturalfestival.smartcampus.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @creation_time: 2017/4/22
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 助手
 */

public class AssistantFragment extends BaseFragment {
    private View view;
    private GridView gv_assistant;
    private List<SimpleGridViewAdapterBean> functions;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assistant, null);
        setView(view);
        gv_assistant = getView(R.id.gv_assistant);

        int[] icons = new int[]{R.mipmap.ic_record_48, R.mipmap.ic_library, R.mipmap.ic_fix, R.mipmap.ic_syllabus};
        String[] texts = new String[]{"记录", "图书馆", "宿舍报修", "正方系统"};

        functions = new ArrayList<>();
        SimpleGridViewAdapterBean fun;
        for (int i = 0; i < icons.length; i ++){
            fun = new SimpleGridViewAdapterBean();
            fun.icon = icons[i];
            fun.text = texts[i];
            functions.add(fun);
        }

        SimpleGridViewAdapter adapter = new SimpleGridViewAdapter(getContext(), R.layout.view_grid_assistan, functions);
        gv_assistant.setAdapter(adapter);
        gv_assistant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    startActivity(new Intent(getContext(), RecordActivity.class));
                }
                if (i == 1){
                    InformationActivity.setIntent(getContext(), "http://weixin.zhaobenshu.com/WeiXin/lingnan/page/home/zbsHome.php" ,"图书馆");
                }
                if (i == 2){
                    InformationActivity.setIntent(getContext(), "http://hi.lnsfxyzs.cn/bx/", "宿舍报修");
                }
                if (i == 3){
                    InformationActivity.setIntent(getContext(), "http://202.192.143.234/", "正方系统");
                }

            }
        });
        return view;
    }


    public class SimpleGridViewAdapterBean{
        public int icon;    //图标
        public String text; //文字
    }
}
