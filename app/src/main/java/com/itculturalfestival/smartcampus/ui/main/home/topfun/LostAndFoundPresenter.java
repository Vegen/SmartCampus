package com.itculturalfestival.smartcampus.ui.main.home.topfun;

import com.itculturalfestival.smartcampus.entity.db.Lost;
import com.itculturalfestival.smartcampus.network.SubscriptionHolder;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenterImpl;
import com.vegen.smartcampus.baseframework.network.HttpError;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import rx.Subscription;

/**
 * Created by vegen on 2018/3/26.
 */

public class LostAndFoundPresenter extends BasePresenterImpl<LostAndFoundContract.View> implements LostAndFoundContract.Presenter {

    public LostAndFoundPresenter(LostAndFoundContract.View view) {
        super(view);
    }

    @Override
    public void getLostList(int SKIP, int skip) {
        BmobQuery<Lost> query = new BmobQuery<Lost>();
//        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_THEN_NETWORK);   // 先从缓存获取数据，如果没有，再从网络获取。
        query.order("-createdAt");      // 按照时间降序
        query.setLimit(SKIP);           // 每次加载条数
        query.setSkip(skip);            // 偏移量
        Subscription subscription = query.findObjects(new FindListener<Lost>() {
            @Override
            public void done(List<Lost> list, BmobException e) {
                if (mView != null) {
                    if (e == null) {
                        // 请求成功
                        mView.showLostList(list);
                        mView.hideLoading(false);
                        if (list == null || list.isEmpty()){
                            mView.loadMoreEnd(true);
                        }
                    } else {
                        mView.showMessage(HttpError.getErrorMessage(e));
                        mView.hideLoading(true);
                        mView.loadMoreFail();
                    }
                }
            }
        });
        mHttpLinkers.add(new SubscriptionHolder(subscription));
    }
}
