package com.itculturalfestival.smartcampus.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.MenuRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.itculturalfestival.smartcampus.R;
import com.vegen.smartcampus.baseframework.utils.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vegen on on 2018/3/7.
 */
public class BottomTab extends LinearLayout {

    Context context;

    Tab[] tabs = new Tab[]{};

    int lastSelect = -1;

    ViewPager mViewPager;

    public BottomTab(Context context) {
        super(context, null, 0);
    }

    public BottomTab(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.context = context;
        setData(R.menu.app_bottombar_menu);
    }

    public BottomTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(HORIZONTAL);
        setData(R.menu.app_bottombar_menu);
        selectTab(0);
    }

    public void setData(@MenuRes int menuRes, boolean hideSpTab) {
        PopupMenu popupMenu = new PopupMenu(context, null);
        Menu menu = popupMenu.getMenu();
        MenuInflater menuInflater = new MenuInflater(context);
        menuInflater.inflate(menuRes, menu);

        int menuSize = menu.size() - (hideSpTab ? 1 : 0);
        tabs = new Tab[menuSize];

        int index = 0;
        for (int i = 0; i < menu.size(); i++) {
            if (i == 3 && hideSpTab) continue;

            MenuItem item = menu.getItem(i);
            Tab tab = new Tab(item.getItemId(), item.getIcon(), item.getTitle().toString());
            tab.position = index++;
            tabs[tab.position] = tab;
        }

        removeAllViews();
        for (int i = 0; i < tabs.length; i++) {
            final Tab tab = tabs[i];

            View view = LayoutInflater.from(context).inflate(R.layout.app_item_tab, this, false);
            LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
            view.setLayoutParams(params);

            ViewHolder holder = new ViewHolder(view);
            holder.icon.setImageDrawable(tab.drawable);
            holder.title.setText(tab.title);
            holder.redPoint.setVisibility(tab.hasNew ? VISIBLE : INVISIBLE);
            view.setTag(holder);

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTabClickListener != null) {
                        onTabClickListener.onTabClick(v, tab.id, tab.position);
                    }
                    selectTab(tab.position);
                }
            });

            addView(view);
        }
    }

    public void setData(@MenuRes int menuRes) {
        setData(menuRes, false);
    }

    void notifyChange() {
        for (int i = 0; i < getChildCount(); i++) {
            Tab tab = tabs[i];
            ViewHolder holder = (ViewHolder) getChildAt(i).getTag();
            holder.icon.setImageDrawable(tab.drawable);
            holder.title.setText(tab.title);
            holder.redPoint.setVisibility(tab.hasNew ? VISIBLE : INVISIBLE);
        }
    }

    public void showRedPoint(int position) {
        changeRedPoint(position, true);
    }

    public void hideRedPoint(int position) {
        changeRedPoint(position, false);
    }

    public String getTabText(int position){
        if (isInvalidPosition(position)) {
            LogUtils.e("position is invalid");
            return null;
        }
        Tab tab = tabs[position];
        return tab.title;
    }

    public void changeRedPoint(int position, boolean isShow) {
        if (isInvalidPosition(position)) {
            LogUtils.e("position is invalid");
            return;
        }

        Tab tab = tabs[position];
        tab.hasNew = isShow;
        notifyChange();
    }

    public void selectTab(int position) {
        if (lastSelect == position) {
            return;
        }

        selectTab(position, true);
        selectTab(lastSelect, false);

        lastSelect = position;
    }

    void selectTab(int position, boolean isSelect) {
        if (isInvalidPosition(position)) {
            return;
        }

        ViewGroup viewGroups = (ViewGroup) getChildAt(position);
        for (int i = 0; i < viewGroups.getChildCount(); i++) {
            viewGroups.getChildAt(i).setSelected(isSelect);
        }
    }

    private boolean isInvalidPosition(int position) {
        return position < 0 || position >= tabs.length;
    }

    public void setupViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                selectTab(position);
            }
        });
    }

    public interface OnTabClickListener {
        void onTabClick(View view, int itemId, int position);
    }

    OnTabClickListener onTabClickListener;

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
    }

    class Tab {

        public int id;
        public String title;
        public Drawable drawable;

        public boolean hasNew;
        public int position;

        public Tab(int id, Drawable drawable, String title) {
            this.id = id;
            this.drawable = drawable;
            this.title = title;
        }
    }

    class ViewHolder {

        @Bind(R.id.icon)
        public ImageView icon;

        @Bind(R.id.title)
        public TextView title;

        @Bind(R.id.img_red_point)
        public View redPoint;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

    }

}
