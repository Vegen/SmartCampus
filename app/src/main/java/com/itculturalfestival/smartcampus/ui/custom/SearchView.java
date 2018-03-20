package com.itculturalfestival.smartcampus.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itculturalfestival.smartcampus.R;
import com.itculturalfestival.smartcampus.utils.PhoneUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @creation_time: 2017/4/10
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 搜索框
 */

public class SearchView extends LinearLayout implements TextWatcher {

    public static final int SEARCHTYPE_AUTO = 0;     //延时自动执行搜索
    public static final int SEARCHTYPE_ENTER = 1;    //按回车键执行搜索

    private final int DEFAULT_TEXTSIZE = 15;        //默认字体大小15sp

    private EditText et_search;     //输入框
    private ImageView iv_del;       //删除图标
    private LinearLayout ll_root;   //背景
    private ImageView iv_search;   //搜索图标

    private int searchType = SEARCHTYPE_AUTO;   //搜索方式
    private SearchWay mSearch;                  //匹配方法
    private String searchText = "";             //改变后的文字
    private WaitThread waitThread;              //等待线程
    private int waitTime = 200;                 //延时搜索时间，默认200ms
    private int curTime;                        //当前延时时间
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //更新回调接口
            if (0 == msg.what && null != mSearch){
                waitThread = null;
                if (searchText.isEmpty()){
                    mSearch.update(mSearch.getData());
                }else {
                    //匹配结果回调
                    List searchList = new ArrayList();
                    List list = mSearch.getData();
                    if (list != null) {
                        for (Object o : list) {
                            if (mSearch.matchItem(o, searchText)) {
                                searchList.add(o);
                            }
                        }
                        mSearch.update(searchList);
                    }
                }
            }
        }
    };

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_search, null);

        bindView(view);
        obtainStyledAttr(context, attrs);
        bindListener(view);

        ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(view, params);
    }

    /**
     * 绑定控件
     */
    private void bindView(View view){
        et_search = (EditText) view.findViewById(R.id.et_search);
        iv_del = (ImageView) view.findViewById(R.id.iv_del);
        ll_root = (LinearLayout) view.findViewById(R.id.ll_root);
        iv_search = (ImageView) view.findViewById(R.id.iv_search);
    }

    /**
     * 绑定监听器
     */
    private void bindListener(View view){
        //删除所有文字
        iv_del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
            }
        });

        //点击搜索框，输入框获得焦点
        view.findViewById(R.id.ll_root).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.requestFocus();
                PhoneUtil.showSoftInput(getContext(), et_search);
            }
        });

        //文字改变监听
        et_search.addTextChangedListener(this);

        //输入焦点状态改变时改变搜索框样式
        et_search.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ll_root.setBackgroundResource(b ? R.drawable.bg_stroke_corners_blue : R.drawable.bg_corners_gray);
                if (iv_search != null){
                    iv_search.setImageResource(b ? R.mipmap.ic_search_focus : R.mipmap.ic_search_unfocus);
                }
            }
        });
    }

    /**
     * 获取自定义属性
     */
    private void obtainStyledAttr(Context context, AttributeSet attrs){
        TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.SearchView);
        String s;
        //文字大小
        float textSize = typed.getDimension(R.styleable.SearchView_sv_textSize, -1);
        if (textSize == -1){
            textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TEXTSIZE, getResources().getDisplayMetrics());
            et_search.setTextSize(textSize);
            et_search.getPaint().setTextSize(textSize);
        }else{
            et_search.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }

        //文字
        s = typed.getString(R.styleable.SearchView_sv_text);
        if (s != null){
            et_search.setText(s);
        }
        //提示文字
        s = typed.getString(R.styleable.SearchView_sv_hint);
        if (s != null){
            et_search.setHint(s);
        }
        //是否隐藏搜索图标
        boolean hideImg = typed.getBoolean(R.styleable.SearchView_sv_hideImg, false);
        if (hideImg) {
            iv_search.setVisibility(GONE);
            iv_search = null;
        }
        //回收资源
        typed.recycle();
    }

    /**
     * 匹配数据的方法
     */
    public void setSearchWay(SearchWay search){
        mSearch = search;
    }

    public String getText(){
        return et_search.getText().toString();
    }

    public SearchWay getSearchWay() {
        return mSearch;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (textChanged != null){
            textChanged.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (textChanged != null){
            textChanged.onTextChanged(s, start, before, count);
        }
    }

    private OnTextChanged textChanged;

    public void setOnTextChanged(OnTextChanged textChanged){
        this.textChanged = textChanged;
    }

    public interface OnTextChanged{
        void beforeTextChanged(CharSequence s, int start, int count, int after);
        void onTextChanged(CharSequence s, int start, int before, int count);
        void afterTextChanged(Editable s);
    }

    @Override
    public void afterTextChanged(Editable s) {

        if (textChanged != null){
            textChanged.afterTextChanged(s);
        }

        //删除图标
        if (s.toString().isEmpty()){
            iv_del.setVisibility(GONE);
        }else {
            iv_del.setVisibility(VISIBLE);
        }

        searchText = s.toString();

        //不是自动搜索
        if (searchType != SEARCHTYPE_AUTO) {
            if (searchText.isEmpty()){
                mHandler.sendEmptyMessage(0);
            }
            return;
        }

        if (mSearch != null) {
            if (null == waitThread) {
                waitThread = new WaitThread();
                waitThread.start();
            } else {
                //搜索框的文字发生变化就重置等待时间
                if (!searchText.equals(s.toString())) {
                    curTime = 0;
                }
            }
        }
    }

    /**
     * 设置搜索方式
     */
    public void setSearchType(int type){
        this.searchType = type;

        //按回车键搜索，设计监听器
        if (type == SEARCHTYPE_ENTER){
            et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    //判断是否是搜索键
                    if (i == EditorInfo.IME_ACTION_SEARCH){
                        mHandler.sendEmptyMessage(0);
                        //关闭软键盘
                        PhoneUtil.hideSoftInput(getContext(), et_search);
                    }

                    return false;
                }
            });
        }else {
            et_search.setOnEditorActionListener(null);
        }
    }

    /**
     * 设置搜索延时时间
     * @param waitTime 毫秒，精度为100ms
     */
    public void setWaitTime(int waitTime){
        this.waitTime = waitTime;
    }

    /**
     * 延时搜索的线程
     */
    private class WaitThread extends Thread{
        @Override
        public void run() {
            //等待延时
            for (curTime = 0; curTime < waitTime; curTime += 100){
                try {
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 用于匹配项
     */
    public static abstract class SearchWay<T>{
        /**
         * @return 数据源
         */
        public abstract List<T> getData();

        /**
         * @param s 搜索框的文字
         * @return item中是否包含有s
         */
        public abstract boolean matchItem(T item, String s);

        /**
         * 更新列表
         * @param resultList 匹配的数据，重新加载到列表
         */
        public abstract void update(List<T> resultList);
    }

    /**
     * 设置键盘的搜索活动
     * @param onEditorActionListener
     */
    public void setOnEditorActionListener(TextView.OnEditorActionListener onEditorActionListener){
        et_search.setOnEditorActionListener(onEditorActionListener);
    }
}
