package com.itculturalfestival.smartcampus.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.itculturalfestival.smartcampus.utils.ActivityCollectorUtil;

/**
 * @creation_time: 2017/4/3
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 封装FragmentActivity
 */

public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectorUtil.addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(this);
    }

    public void initLayout(int layoutResID) {
        setContentView(layoutResID);
    }

    public void startActivity(Context content,Class<?> cls){
        Intent intent=new Intent(content,cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void startActivityForResult(Intent intent, int requestCode){
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        super.startActivityForResult(intent, requestCode);
    }

    /**
     * 针对6.0动态请求权限问题
     * 判断是否允许此权限
     *
     * @param permissions
     * @return
     */
    protected boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 动态请求权限
     *
     * @param code
     * @param permissions
     */
    protected void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public Context getContext(){
        return this;
    }

    public Activity getActivity(){
        return this;
    }

    public int getRColor(int id){
        return ContextCompat.getColor(this, id);
    }

    public Drawable getRDrawable(int id){
        return ContextCompat.getDrawable(this, id);
    }

    public String getRString(int id){
        return getResources().getString(id);
    }

    public <T extends View> T getView(int viewId){
        return (T) findViewById(viewId);
    }

    public void setClickListener(int viewId, View.OnClickListener l){
        View view = getView(viewId);
        if (view != null) view.setOnClickListener(l);
    }

    /**
     * 切换Fragment
     */
    protected void switchFragment(int id, Fragment from, Fragment to){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (from != to){
            if (from != null) {
                transaction.hide(from);
            }
            if (!to.isAdded()){
                transaction.add(id, to);
            }else {
                transaction.show(to);
            }
        }
        transaction.commit();
    }
}
