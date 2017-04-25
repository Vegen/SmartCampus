package com.itculturalfestival.smartcampus.ui.activity.record;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.entity.RecordDB;
import com.itculturalfestival.smartcampus.ui.base.BaseActivity;
import com.itculturalfestival.smartcampus.util.DateUtil;
import com.itculturalfestival.smartcampus.util.LoginUtil;

import org.litepal.crud.DataSupport;

import java.util.Date;
import java.util.List;

/**
 * @creation_time: 2017/4/11
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 记录详情
 */

public class RecordDetailsActivity extends BaseActivity {

    private ImageView back;
    private TextView title;
    private ImageView iv_remove;
    private ImageView iv_update;
    private EditText et_title;
    private EditText et_content;
    private TextView submit;
    private LinearLayout ll_fun;
    private int type;
    private KeyListener titleKeylistener;      //备份原有可点击
    private KeyListener contentKeylistener;
    private int id;
    private List<RecordDB> recordList;
    private OnRecordChangeListener onRecordChangeListener;

    @Override
    protected void onCreate() {
        initLayout(R.layout.activity_record_details);
        initView();
        initData();
    }

    private void initView(){
        back = getView(R.id.back);
        title = getView(R.id.title);
        iv_remove = getView(R.id.iv_remove);
        iv_update = getView(R.id.iv_update);
        et_title = getView(R.id.et_title);
        et_content = getView(R.id.et_content);
        submit = getView(R.id.submit);
        ll_fun = getView(R.id.ll_fun);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleKeylistener = et_title.getKeyListener();
        contentKeylistener = et_content.getKeyListener();

        Intent intent = getIntent();
        type = intent.getIntExtra("type", 1);   //默认是1添加
        if (type == 1){     //添加
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (et_title.getText().toString().isEmpty()){
                        showToast("请输入标题");
                    }else {
                        RecordDB record = new RecordDB(LoginUtil.getUserId(getContext()), et_title.getText().toString(), et_content.getText().toString(), new Date());
                        record.saveThrows();
                        if (onRecordChangeListener!=null) {
                            onRecordChangeListener.change();
                        }
                        finish();
                    }

                }
            });
        }else {         //查看
            id = intent.getIntExtra("id", -1);
            recordList = DataSupport.where("userId = ? and id = ?", String.valueOf(LoginUtil.getUserId(getContext())), String.valueOf(id)).find(RecordDB.class);

            et_title.setText(recordList.get(0).getTitle());
            et_content.setText(recordList.get(0).getContent());

            ll_fun.setVisibility(View.VISIBLE);
            et_title.setKeyListener(null);
            et_content.setKeyListener(null);
            submit.setVisibility(View.GONE);

            iv_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showToast("你现在可以修改记录");
                    submit.setVisibility(View.VISIBLE);
                    et_title.setKeyListener(titleKeylistener);
                    et_content.setKeyListener(contentKeylistener);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (et_title.getText().toString().isEmpty()){
                                showToast("请输入标题");
                            }else {
//                                recordList.get(0).setTitle(et_title.getText().toString());
//                                recordList.get(0).setContent(et_content.getText().toString());
//                                recordList.get(0).saveThrows();
                                ContentValues values = new ContentValues();
                                values.put("title", et_title.getText().toString());
                                values.put("content", et_content.getText().toString());
                                DataSupport.updateAll(RecordDB.class, values, "id = ? and userId = ?", String.valueOf(id), String.valueOf(LoginUtil.getUserId(getContext())));
                                if (onRecordChangeListener!=null) {
                                    onRecordChangeListener.change();
                                }
                                finish();
                            }
                        }
                    });
                }
            });

            iv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("确定删除此条记录？");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            DataSupport.deleteAll(RecordDB.class, "id = ? and userId = ?", String.valueOf(id), String.valueOf(LoginUtil.getUserId(getContext())));
                            if (onRecordChangeListener!=null) {
                                onRecordChangeListener.change();
                            }
                            finish();
                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.create().show();
                }
            });
        }
    }
    private void initData(){

    }

    public void setOnRecordChageListener(OnRecordChangeListener onRecordChangeListener){
        this.onRecordChangeListener = onRecordChangeListener;
    }

    public interface OnRecordChangeListener{
        void change();
    }
}
