package com.itculturalfestival.smartcampus.ui.activity.record;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.entity.RecordDB;
import com.itculturalfestival.smartcampus.ui.base.BaseActivity;
import com.itculturalfestival.smartcampus.ui.view.Header;
import com.itculturalfestival.smartcampus.ui.widget.ArcPopupWindow;
import com.itculturalfestival.smartcampus.ui.view.RecyclerDecoration;
import com.itculturalfestival.smartcampus.util.DateUtil;
import com.itculturalfestival.smartcampus.util.LoginUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @creation_time: 2017/4/11
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 记录首页
 */

public class RecordActivity extends BaseActivity{

    private List<RecordDB> recordList;
    private RecyclerView rv_record;
    private CommonAdapter adapter;
    private int TYPE_ADD = 1, TYPE_DETAILS = 2;
    private Header header;

    @Override
    protected void onCreate() {
        initLayout(R.layout.activity_record);
        recordList = new ArrayList<>();
        initView();
    }

    private void initView(){
        rv_record = getView(R.id.rv_record);
        header = getView(R.id.header);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_record.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData(){
        header.setFunImage(R.mipmap.ic_add_record, 10);
        header.setFunClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),RecordDetailsActivity.class);
                intent.putExtra("type", TYPE_ADD);
                startActivity(intent);
            }
        });

        //判断是否已存在
        boolean isDBExist = !DataSupport.limit(1).find(RecordDB.class).isEmpty();
        if (isDBExist){
            recordList = DataSupport.where("userId = ?", String.valueOf(LoginUtil.getUserId(getContext()))).order("ctime desc").find(RecordDB.class);
        }else {
            recordList = new ArrayList<>();
        }

        adapter = new CommonAdapter<RecordDB>(this, R.layout.view_item_record, recordList) {
            @Override
            protected void convert(ViewHolder holder, RecordDB recordDB, int position) {
                holder.setText(R.id.tv_title, recordDB.getTitle());
                holder.setText(R.id.tv_time, DateUtil.textForNow("yyyy年MM月dd日 HH:mm", recordDB.getCtime()));
            }
        };
        rv_record.setAdapter(adapter);
        rv_record.addItemDecoration(new RecyclerDecoration(getContext(), RecyclerDecoration.VERTICAL_LIST));
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(getContext(),RecordDetailsActivity.class);
                intent.putExtra("id", recordList.get(position).getId());
                intent.putExtra("type", TYPE_DETAILS);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

}
