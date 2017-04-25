package com.itculturalfestival.smartcampus.ui.activity.team;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.ui.base.BaseActivity;

/**
 * @creation_time: 2017/4/10
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 创建团队
 */

public class CreateTeamActivity extends BaseActivity {

    private TextView tv_next_step;
    private EditText et_team_name;
    private EditText et_team_introduce;
    private LinearLayout ll_logo;
    private ImageView iv_logo;
    private static final int REQUEST_CODE_PICK_IMAGE = 1023;

    @Override
    protected void onCreate() {
        initLayout(R.layout.activity_create_team_first_step);
        tv_next_step = getView(R.id.tv_next_step);
        et_team_name = getView(R.id.et_team_name);
        et_team_introduce = getView(R.id.et_team_introduce);
        ll_logo = getView(R.id.ll_logo);
        iv_logo = getView(R.id.iv_logo);
        ll_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 打开系统相册
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");// 相片类型
                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
            }
        });
        tv_next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_team_name.getText().toString().trim())){
                    showToast("请填写团队名称");
                }else if (TextUtils.isEmpty(et_team_introduce.getText().toString().trim())){
                    showToast("请填写团队介绍");
                }else if (iv_logo.getDrawable() == null){
                    showToast("请选择logo");
                }else {
                    startActivity(new Intent(getContext(), CreateTeamActivity2.class));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            Uri uri = data.getData();
            Glide.with(getContext()).load(getRealFilePath(uri)).into(iv_logo);
        }
    }

    /**
     * 根据Uri获取图片文件的绝对路径
     */
    public String getRealFilePath(final Uri uri) {
        if (null == uri) {
            return null;
        }

        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = getContentResolver().query(uri,
                    new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
