package com.itculturalfestival.smartcampus.ui.main.home.topfun;

import com.itculturalfestival.smartcampus.entity.db.Lost;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenter;
import com.vegen.smartcampus.baseframework.mvp.view.BaseView;

import java.util.List;

/**
 * Created by vegen on 2018/3/26.
 */

public class LostAndFoundContract {
    public interface View extends BaseView {
        void showLostList(List<Lost> lostList);
    }

    public interface Presenter extends BasePresenter {
        void getLostList(int SKIP, int skip);
    }
}
