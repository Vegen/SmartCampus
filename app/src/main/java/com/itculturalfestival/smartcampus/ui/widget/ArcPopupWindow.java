package com.itculturalfestival.smartcampus.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.ui.activity.information.MainActivity;
import com.itculturalfestival.smartcampus.ui.activity.message.MessageActivity;
import com.itculturalfestival.smartcampus.ui.activity.mine.MineActivity;
import com.itculturalfestival.smartcampus.ui.activity.record.RecordActivity;
import com.itculturalfestival.smartcampus.ui.activity.team.TeamActivity;
import com.ogaclejapan.arclayout.ArcLayout;

/**
 * @creation_time: 2017/4/8
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 弧形弹窗
 */

public class ArcPopupWindow extends CustomPopupWindow implements View.OnClickListener {

    private final int bgColor = 0x30000000; //半透明背景色

    private View bg;
    private ArcLayout arc_menu;
    private LinearLayout ll_message;
    private ImageView iv_message;
    private TextView tv_message;
    private LinearLayout ll_team;
    private ImageView iv_team;
    private TextView tv_team;
    private LinearLayout ll_record;
    private ImageView iv_record;
    private TextView tv_record;
    private LinearLayout ll_mine;
    private ImageView iv_mine;
    private TextView tv_mine;
    private LinearLayout ll_information;
    private ImageView iv_information;
    private TextView tv_information;

    private View viewInside;

    private Context context;
    private Activity activity;
    private PopupWindow pop_bg;
    private MenuChooseListener m;


    /**
     * @param context
     */
    public ArcPopupWindow(Context context, Activity activity) {
        super(context, R.layout.pop_arc);
        this.context = context;
        this.activity = activity;
        viewInside = getContentView();
        getView(R.id.bg).setOnClickListener(this);      //背景
        ll_message = getView(R.id.ll_message);
        iv_message = getView(R.id.iv_message);
        tv_message = getView(R.id.tv_message);
        ll_team = getView(R.id.ll_team);
        iv_team = getView(R.id.iv_team);
        tv_team = getView(R.id.tv_team);
        ll_record = getView(R.id.ll_record);
        iv_record = getView(R.id.iv_record);
        tv_record = getView(R.id.tv_record);
        ll_mine = getView(R.id.ll_mine);
        iv_mine = getView(R.id.iv_mine);
        tv_mine = getView(R.id.tv_mine);
        ll_information = getView(R.id.ll_information);
        iv_information = getView(R.id.iv_information);
        tv_information = getView(R.id.tv_information);
        ll_message.setOnClickListener(this);
        ll_information.setOnClickListener(this);
        ll_mine.setOnClickListener(this);
        ll_record.setOnClickListener(this);
        ll_team.setOnClickListener(this);

        View bg = new View(context);
        bg.setBackgroundColor(bgColor);
        pop_bg = new PopupWindow(bg);
        pop_bg.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop_bg.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //渐变效果
        pop_bg.setAnimationStyle(R.style.BottomPopBgStyle);

        //设置宽高
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        //从底部弹出的动画效果
        this.setAnimationStyle(R.style.BottomPopStyle);
        nowLocation();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_information:
                selected(0, true);
                break;
            case R.id.ll_message:
                selected(1, true);
                break;
            case R.id.ll_team:
                selected(2, true);
                break;
            case R.id.ll_record:
                selected(3, true);
                break;
            case R.id.ll_mine:
                selected(4, true);
                break;
        }
        dismiss();
    }

    public void setOnMenuChooseListener(MenuChooseListener m){
        this.m = m;
    }

    public interface MenuChooseListener{
        void select(int which);
    }

    private void nowLocation(){
        if (context instanceof MainActivity){
            selected(0, false);
        }else if (context instanceof MessageActivity){
            selected(1, false);
        }else if (context instanceof TeamActivity){
            selected(2, false);
        }else if (context instanceof RecordActivity){
            selected(3, false);
        }else if (context instanceof MineActivity){
            selected(4, false);
        }
    }

    /**
     * 点击选择了菜单哪个模块
     * @param which
     */
    private void selected(int which, boolean isGo){
        if (m != null){
            m.select(which);
        }
        switch (which){
            case 0://资讯
                iv_message.setImageResource(R.drawable.ic_message_normal);
                tv_message.setTextColor(getRColor(R.color.text_color));
                iv_team.setImageResource(R.drawable.ic_team_normal);
                tv_team.setTextColor(getRColor(R.color.text_color));
                iv_record.setImageResource(R.drawable.ic_record_normal);
                tv_record.setTextColor(getRColor(R.color.text_color));
                iv_mine.setImageResource(R.drawable.ic_mine_normal);
                tv_mine.setTextColor(getRColor(R.color.text_color));
                iv_information.setImageResource(R.drawable.ic_information_select);
                tv_information.setTextColor(getRColor(R.color.colorTheme));
                if (isGo) {
                    if (!(context instanceof MainActivity)) {
                        context.startActivity(new Intent(context, MainActivity.class));
                        activity.finish();
                    }
                }
                break;
            case 1://消息
                iv_message.setImageResource(R.drawable.ic_message_select);
                tv_message.setTextColor(getRColor(R.color.colorTheme));
                iv_team.setImageResource(R.drawable.ic_team_normal);
                tv_team.setTextColor(getRColor(R.color.text_color));
                iv_record.setImageResource(R.drawable.ic_record_normal);
                tv_record.setTextColor(getRColor(R.color.text_color));
                iv_mine.setImageResource(R.drawable.ic_mine_normal);
                tv_mine.setTextColor(getRColor(R.color.text_color));
                iv_information.setImageResource(R.drawable.ic_information_normal);
                tv_information.setTextColor(getRColor(R.color.text_color));
                if (isGo) {
                    if (!(context instanceof MessageActivity))
                        context.startActivity(new Intent(context, MessageActivity.class));
                }
                break;
            case 2://团队
                iv_message.setImageResource(R.drawable.ic_message_normal);
                tv_message.setTextColor(getRColor(R.color.text_color));
                iv_team.setImageResource(R.drawable.ic_team_select);
                tv_team.setTextColor(getRColor(R.color.colorTheme));
                iv_record.setImageResource(R.drawable.ic_record_normal);
                tv_record.setTextColor(getRColor(R.color.text_color));
                iv_mine.setImageResource(R.drawable.ic_mine_normal);
                tv_mine.setTextColor(getRColor(R.color.text_color));
                iv_information.setImageResource(R.drawable.ic_information_normal);
                tv_information.setTextColor(getRColor(R.color.text_color));
                if (isGo) {
                    if (!(context instanceof TeamActivity))
                    context.startActivity(new Intent(context, TeamActivity.class));
                }
                break;
            case 3://记录
                iv_message.setImageResource(R.drawable.ic_message_normal);
                tv_message.setTextColor(getRColor(R.color.text_color));
                iv_team.setImageResource(R.drawable.ic_team_normal);
                tv_team.setTextColor(getRColor(R.color.text_color));
                iv_record.setImageResource(R.drawable.ic_record_select);
                tv_record.setTextColor(getRColor(R.color.colorTheme));
                iv_mine.setImageResource(R.drawable.ic_mine_normal);
                tv_mine.setTextColor(getRColor(R.color.text_color));
                iv_information.setImageResource(R.drawable.ic_information_normal);
                tv_information.setTextColor(getRColor(R.color.text_color));
                if (isGo) {
                    if (!(context instanceof RecordActivity))
                    context.startActivity(new Intent(context, RecordActivity.class));
                }
                break;
            case 4://我的
                iv_message.setImageResource(R.drawable.ic_message_normal);
                tv_message.setTextColor(getRColor(R.color.text_color));
                iv_team.setImageResource(R.drawable.ic_team_normal);
                tv_team.setTextColor(getRColor(R.color.text_color));
                iv_record.setImageResource(R.drawable.ic_record_normal);
                tv_record.setTextColor(getRColor(R.color.text_color));
                iv_mine.setImageResource(R.drawable.ic_mine_select);
                tv_mine.setTextColor(getRColor(R.color.colorTheme));
                iv_information.setImageResource(R.drawable.ic_information_normal);
                tv_information.setTextColor(getRColor(R.color.text_color));
                if (isGo) {
                    if (!(context instanceof MineActivity))
                    context.startActivity(new Intent(context, MineActivity.class));
                }
                break;
        }
    }

    public void show(){
        //添加半透明背景
        View view = new View(getContext());
        if (!pop_bg.isShowing()) pop_bg.showAtLocation(view , Gravity.TOP, 0, 0);
        showAtLocation(view, Gravity.BOTTOM|Gravity.RIGHT, ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth(),
                ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight());
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //移除半透明背景
        if (pop_bg.isShowing()) pop_bg.dismiss();
    }

    @Override
    public <T extends View> T getView(int viewId) {
        return (T) viewInside.findViewById(viewId);
    }
}
