package com.itculturalfestival.smartcampus.ui.activity.team;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.ui.activity.information.MainActivity;
import com.itculturalfestival.smartcampus.ui.base.BaseActivity;

/**
 * @creation_time: 2017/4/10
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe:
 */

public class CreateTeamActivity2 extends BaseActivity {

    private TextView tv_end_step;
    private EditText name;
    private EditText grade;

    @Override
    protected void onCreate() {
        initLayout(R.layout.activity_create_team_end_step);
        name = getView(R.id.name);
        grade = getView(R.id.grade);
        tv_end_step = getView(R.id.tv_end_step);
        tv_end_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(name.getText().toString().trim())){
                    showToast("真实姓名不能为空");
                }else if (TextUtils.isEmpty(grade.getText().toString().trim())){
                    showToast("职务不能为空");
                }else {
                    showToast("创建团队成功");
                    startActivity(new Intent(getContext(), MainActivity.class));
                }
            }
        });
    }
}
