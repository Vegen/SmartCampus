package com.itculturalfestival.smartcampus.ui.start;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.itculturalfestival.smartcampus.entity.db.SmartUser;
import com.vegen.smartcampus.baseframework.mvp.presenter.BasePresenterImpl;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by vegen on 2018/4/4.
 */

public class SignUpPresenter extends BasePresenterImpl<SignUpContract.View> implements SignUpContract.Presenter  {

    public SignUpPresenter(SignUpContract.View view) {
        super(view);
    }

    @Override
    public void signUp(String phone, String password) {
        // 注册账号
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(phone, password);
                    if (mView != null){
                        mView.signUpSuccess();
                    }
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    if (mView != null){
                        mView.signUpError(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void signUpServer(String phone, String password, String sex, String schoolName, Integer schoolId) {

//        SmartUser smartUser = new SmartUser();
//        smartUser.setPhone(phone);
//        smartUser.setPassword(password);
//        smartUser.setSex(sex);
//        smartUser.setSchoolName(schoolName);
//        smartUser.setSchoolId(schoolId);
//        smartUser.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null) {
//                    if (mView != null) mView.hideLoading(false);
//                } else {
//                    if (mView != null) {
//                        mView.showMessage(e.getMessage());
//                        mView.hideLoading(true);
//                    }
//                }
//            }
//        });
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(phone);
        bmobUser.setPassword(password);
        bmobUser.signUp(new SaveListener<SmartUser>() {
            @Override
            public void done(SmartUser s, BmobException e) {
                if(e == null){
                    SmartUser newUser = new SmartUser();
                    newUser.setSex(sex);
                    newUser.setSchoolId(schoolId);
                    newUser.setSchoolName(schoolName);
                    SmartUser bmobUser = BmobUser.getCurrentUser(SmartUser.class);
                    if (bmobUser != null) {
                        newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    if (mView != null) mView.hideLoading(false);
                                } else {
                                    if (mView != null) {
                                        mView.showMessage(e.getMessage());
                                        mView.hideLoading(true);
                                    }
                                }
                            }
                        });
                    }else {
                        if (mView != null) {
                            mView.showMessage(e.getMessage());
                            mView.hideLoading(true);
                        }
                    }
                }else{
                    if (mView != null) {
                        mView.showMessage(e.getMessage());
                        mView.hideLoading(true);
                    }
                }
            }
        });
    }
}
