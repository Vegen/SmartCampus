package com.vegen.smartcampus.baseframework.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import io.reactivex.disposables.Disposable;

/**
 * Created by vegen on 2018/3/2.
 */

public class LoadingProgressDialog extends ProgressDialog {

    private Context context;

    Disposable disposable;

    public LoadingProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public LoadingProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCanceledOnTouchOutside(false);
        this.setMessage("正在加载...");
    }

    public void show(final String message) {
        super.show();
        final LoadingProgressDialog tmp = this;
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tmp.setMessage(message);
            }
        });
    }

    public void show(final String message, boolean canceledOnTouchOutside) {
        super.show();
        final LoadingProgressDialog tmp = this;
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tmp.setMessage(message);
            }
        });
        setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    public void bindSubscription(Disposable subscription) {
        this.disposable = subscription;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        try {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        } catch (Exception ignored) {
        }

    }

}