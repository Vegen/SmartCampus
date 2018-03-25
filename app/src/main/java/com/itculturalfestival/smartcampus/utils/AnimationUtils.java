package com.itculturalfestival.smartcampus.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

/**
 * Created by root on 16-5-5.
 */
public class AnimationUtils {

    public static void showFromBottom(View view) {
        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("translationY", 0F);
        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("alpha", 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, p1, p2).setDuration(500).start();
    }

    public static void hideToBottom(View view) {
        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("translationY", view.getHeight());
        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("alpha", 0f);
        ObjectAnimator.ofPropertyValuesHolder(view, p1, p2).setDuration(500).start();
    }

    public static void show(View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0);

        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("alpha", 1f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, p2);
        objectAnimator.setDuration(200).start();
    }

    public static void hide(View view) {
        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("alpha", 0f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, p2);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.setDuration(200).start();
    }

    public static void rotation(View view, int rotation) {
        if (view == null) {
            return;
        }

        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("rotation", rotation);
        ObjectAnimator.ofPropertyValuesHolder(view, p1).setDuration(250).start();
    }

    public static void breath(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha" , 1, 0, 1);
        animator.setDuration(2000);
        animator.setRepeatCount(Integer.MAX_VALUE);
        animator.start();

    }

}
