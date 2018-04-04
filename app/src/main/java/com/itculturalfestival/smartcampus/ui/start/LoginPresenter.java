package com.itculturalfestival.smartcampus.ui.start;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.itculturalfestival.smartcampus.entity.db.SmartUser;
import com.itculturalfestival.smartcampus.network.SubscriptionHolder;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenterImpl;
import com.vegen.smartcampus.baseframework.network.HttpError;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscription;

/**
 * Created by vegen on 2018/4/4.
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {
        EMClient.getInstance().login(phone, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                // 加载所有会话到内存
                EMClient.getInstance().chatManager().loadAllConversations();
                // 加载所有群组到内存，如果使用了群组的话
                EMClient.getInstance().groupManager().loadAllGroups();
                getUserDetail(phone, password);
            }

            @Override
            public void onError(final int i, final String s) {
                if (mView != null){
                    mView.loginError(i, s);
                }
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     * 从后端拿用户资料
     */
    private void getUserDetail(String phone, String password){
        SmartUser smartUser = new SmartUser();
        smartUser.setPhone(phone);
        smartUser.setPassword(password);
//        smartUser.login(new SaveListener<SmartUser>() {
//            @Override
//            public void done(SmartUser smartUser1, BmobException e) {
//                if (mView != null) {
//                    if (e == null) {
//                        // 请求成功
//                        if (smartUser1 == null){
//                            mView.showMessage("该用户不存在");
//                        }else {
//                            mView.loginSuccess(smartUser1);
//                        }
//                        mView.hideLoading(false);
//                    } else {
//                        mView.showMessage(HttpError.getErrorMessage(e));
//                        mView.hideLoading(true);
//                    }
//                }
//            }
//        });

//        BmobQuery<SmartUser> query = new BmobQuery<SmartUser>();
//        query.addWhereEqualTo("phone", phone);
//        query.addWhereEqualTo("password", password);
//        Subscription subscription = query.findObjects(new FindListener<SmartUser>() {
//            @Override
//            public void done(List<SmartUser> list, BmobException e) {
//                if (mView != null) {
//                    if (e == null) {
//                        // 请求成功
//                        if (list == null || list.isEmpty()){
//                            mView.showMessage("该用户不存在");
//                        }else {
//                            mView.loginSuccess(list.get(0));
//                        }
//                        mView.hideLoading(false);
//                    } else {
//                        mView.showMessage(HttpError.getErrorMessage(e));
//                        mView.hideLoading(true);
//                    }
//                }
//            }
//        });
//        mHttpLinkers.add(new SubscriptionHolder(subscription));
    }
}
