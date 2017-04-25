package com.itculturalfestival.smartcampus.ui.activity.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.ui.base.BaseActivity;
import com.itculturalfestival.smartcampus.ui.view.CircleImageView;
import com.itculturalfestival.smartcampus.ui.widget.MyDialog;
import com.itculturalfestival.smartcampus.ui.view.RecyclerDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @creation_time: 2017/4/11
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 我的资料
 */

public class MyDataActivity extends BaseActivity {

    private RecyclerView rv_school;
    private CircleImageView civ_head;
    private TextView tv_name;
    private TextView tv_sex;
    private List<SchoolDataBean> dataList;
    private LinearLayout ll_name;

    @Override
    protected void onCreate() {
        initLayout(R.layout.activity_my_data);
        initView();
    }

    private void initView(){
        civ_head = getView(R.id.civ_head);
        tv_name = getView(R.id.tv_name);
        tv_sex = getView(R.id.tv_sex);
        ll_name = getView(R.id.ll_name);
        tv_name = getView(R.id.tv_name);
        ll_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MyDialog myDialog = new MyDialog(getContext(), R.layout.view_edit);
                myDialog.show();
                myDialog.getView(R.id.commit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(myDialog.getView(R.id.commit).toString())){
                            showToast("不能为空");
                        }else {
                            tv_name.setText(myDialog.getEditText(R.id.edit));
                            myDialog.dismiss();
                        }
                    }
                });
            }
        });
        Glide.with(getContext()).load("http://1b6093f923.51mypc.cn/res/2017/02/14872303862009c0e1b55.jpg").into(civ_head);
        tv_name.setText("huweijian");
        tv_sex.setText("男");
        rv_school = getView(R.id.rv_school);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_school.setLayoutManager(layoutManager);
        dataList = new ArrayList<>();
        SchoolDataBean schoolData = null;
        schoolData = new SchoolDataBean("学校", "岭南师范学院", 0);
        dataList.add(schoolData);
        schoolData = new SchoolDataBean("院系", "信息工程学院", 0);
        dataList.add(schoolData);
        schoolData = new SchoolDataBean("专业", "网络工程", 0);
        dataList.add(schoolData);
        schoolData = new SchoolDataBean("学历", "本科", 0);
        dataList.add(schoolData);
        schoolData = new SchoolDataBean("入学年份", "2014", 0);
        dataList.add(schoolData);
        schoolData = new SchoolDataBean("学号", "2014344136", 0);
        dataList.add(schoolData);
        CommonAdapter commonAdapter = new CommonAdapter<SchoolDataBean>(this, R.layout.view_item_school_data, dataList) {
            @Override
            protected void convert(ViewHolder holder, SchoolDataBean schoolDataBean, int position) {
                holder.setText(R.id.tv_type, schoolDataBean.type);
                holder.setText(R.id.tv_data, schoolDataBean.data);
            }
        };
        rv_school.setAdapter(commonAdapter);
        rv_school.addItemDecoration(new RecyclerDecoration(this, RecyclerDecoration.VERTICAL_LIST));
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    public class SchoolDataBean {
        private String type;
        private String data;
        private int type_no;
        public SchoolDataBean(){}
        public SchoolDataBean(String type, String data ,int type_no){
            this.type = type;
            this.data = data;
            this.type_no = type_no;
        }
    }
}
