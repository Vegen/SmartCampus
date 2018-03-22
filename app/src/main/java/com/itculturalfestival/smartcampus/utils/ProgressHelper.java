package com.itculturalfestival.smartcampus.utils;

import android.animation.ObjectAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

/**
 * Created by vegen on 2018/3/22.
 */

public class ProgressHelper {
    public static ObjectAnimator setProgress(ProgressBar progressBar, int progress) {
        ObjectAnimator animation = ObjectAnimator.ofInt(
                progressBar, "progress", progressBar.getProgress(), progress);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        return animation;
    }

    public static void hide(ProgressBar progressBar) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(progressBar, "alpha", 1f, 0f);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    public static void simulateLoading(final ProgressBar progressBar, final int value) {

    }
}
