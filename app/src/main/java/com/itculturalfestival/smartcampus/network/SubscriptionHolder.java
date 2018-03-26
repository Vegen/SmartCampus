package com.itculturalfestival.smartcampus.network;

import com.vegen.smartcampus.baseframework.network.HttpLinker;
import rx.Subscription;
/**
 * Created by vegen on 2018/3/26.
 */

public class SubscriptionHolder implements HttpLinker {

    Subscription subscription;

    public SubscriptionHolder(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void release() {
        if (subscription != null && subscription.isUnsubscribed()) subscription.unsubscribe();
    }
}