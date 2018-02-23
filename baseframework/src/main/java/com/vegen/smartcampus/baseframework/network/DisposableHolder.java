package com.vegen.smartcampus.baseframework.network;

import io.reactivex.disposables.Disposable;

/**
 * Created by vegen on 2017/8/25.
 */

public class DisposableHolder implements HttpLinker {

    Disposable mDisposable;

    public DisposableHolder(Disposable disposable) {
        mDisposable = disposable;
    }

    @Override
    public void release() {
        if (mDisposable != null) mDisposable.dispose();
    }
}
