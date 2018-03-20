package com.itculturalfestival.smartcampus.ui.custom;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itculturalfestival.smartcampus.R;

/**
 * @creation_time: 2017/4/10
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 头
 */

//页面中,顶部标题栏(返回,标题,功能键(搜索))
public class Header extends LinearLayout {

    Context con;

    ImageView back ;
    ImageView fun ;
    ImageView fun2;
    TextView title;
    TextView fun_text;

    public Header(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.con=context;

        View v = LayoutInflater.from(context).inflate(R.layout.view_header, null);

        int resourceid = attrs.getAttributeResourceValue(null, "themeColor", 0);
        if (resourceid != 0) {
            RelativeLayout root = (RelativeLayout) v.findViewById(R.id.root);
            root.setBackgroundColor(con.getResources().getColor(resourceid));
        }

        boolean hidFun=attrs.getAttributeBooleanValue(null, "hideFunBtn", false);

        int  titleId=attrs.getAttributeResourceValue(null, "title", 0);
        String titleTxt=con.getResources().getString(titleId);

        back =(ImageView)v.findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) con).finish();
            }
        });

        fun =(ImageView)v.findViewById(R.id.fun);
        fun2 = (ImageView) v.findViewById(R.id.fun2);

        if(hidFun){
            fun.setVisibility(View.INVISIBLE);
        }

        title=(TextView)v.findViewById(R.id.title);
        title.setText(titleTxt);

        //设置文字按钮
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Header);
        fun_text = (TextView) v.findViewById(R.id.fun_text);
        String funText = a.getString(R.styleable.Header_funText);
        int funTextColor = a.getColor(R.styleable.Header_funTextColor, 0xffffffff);
        float funTextSize = a.getDimension(R.styleable.Header_funTextSize, 18);
        fun_text.setText(funText);
        fun_text.setTextColor(funTextColor);
        fun_text.setTextSize(funTextSize);

        //设置图片按钮
        Drawable drawable = a.getDrawable(R.styleable.Header_funBtnImg);
        if (drawable != null){
            fun.setImageDrawable(drawable);
        }

        a.recycle();


        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(v,layoutParams);
    }

    public void setTitle(String titleStr){
        title.setText(titleStr);
    }

    public void setFunClickListener(OnClickListener listener){
        fun.setOnClickListener(listener);
        fun_text.setOnClickListener(listener);
    }

    public void setFun2ClickListener(OnClickListener listener){
        fun2.setOnClickListener(listener);
    }

    public void setFunImage(int drawableId){
        setFunImage(drawableId, 0);
    }

    public void setFunImage(int drawableId, int padding){
        Drawable drawable=con.getResources().getDrawable(drawableId);//R.drawable.ok
        fun.setImageDrawable(drawable);
        if (padding > 0){
            fun.setPadding(padding, padding, padding, padding);
        }
    }

    public void setFun2Image(int drawableId){
        setFun2Image(drawableId, 0);
    }

    public void setFun2Image(int drawableId, int padding){
        Drawable drawable = con.getResources().getDrawable(drawableId);
        fun2.setImageDrawable(drawable);
        fun2.setVisibility(VISIBLE);
        if (padding > 0){
            fun2.setPadding(padding, padding, padding, padding);
        }
    }

    public void setFunHide(boolean status){
        if (status){
            fun.setVisibility(GONE);
        }else {
            fun.setVisibility(VISIBLE);
        }
    }

    public void setFun2Hide(boolean status){
        if (status){
            fun2.setVisibility(GONE);
        }else {
            fun2.setVisibility(VISIBLE);
        }
    }

    /**
     * 设置返回按钮的点击事件
     */
    public void setOnBackClickListener(OnClickListener l){
        back.setOnClickListener(l);
    }

    public void setFunText(String s){
        fun_text.setText(s);
    }


}
