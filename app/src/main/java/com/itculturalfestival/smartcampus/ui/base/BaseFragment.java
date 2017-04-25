package com.itculturalfestival.smartcampus.ui.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * @creation_time: 2017/3/30
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 封装BaseFragment
 */

public abstract class BaseFragment extends Fragment {

    private View view;
//    private LayoutInflater inflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

//    public View inflateView(int layoutId){
//        view = inflater.inflate(layoutId, null);
//        return view;
//    }
    protected void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    public int getRColor(int id){
        return ContextCompat.getColor(getContext(), id);
    }

    public Drawable getRDrawable(int id){
        return ContextCompat.getDrawable(getContext(), id);
    }

    public String getRString(int id){
        return getResources().getString(id);
    }

    public void setView(View view){
        this.view = view;
    }

    /**
     * 必须先使用inflateView()方法，否则报null
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        return (T) view.findViewById(viewId);
    }
}
