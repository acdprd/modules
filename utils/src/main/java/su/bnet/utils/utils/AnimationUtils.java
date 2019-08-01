package su.bnet.utils.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

import su.bnet.utils.viewlisteners.AnimationEndListener;


/**
 * методы для схлопывания\расхлопывания
 */

public class AnimationUtils {
    public static void expand(final View v, long duration) {
//        v.measure(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        expand(v, duration, null);
    }

    public static void expand(final View v) {
//        v.measure(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        int duration = ((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        expand(v, duration, null);
    }

    public static void expand(final View v, long duration, AnimationEndListener listener) {
        v.measure(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation expandAnimation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? FrameLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        expandAnimation.setDuration(duration);
        if (listener != null) {
            expandAnimation.setAnimationListener(listener);
        }
        v.startAnimation(expandAnimation);
    }

    //экспериментальная (вроде работает? но плохо на части где нет контента)
    public static void expandMatchParent(final View v, long duration) {
        expandMatchParent(v, duration, null);
    }

    public static void expandMatchParent(final View v) {
        final int targetHeight = v.getMeasuredHeight();
        int duration = ((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        expandMatchParent(v, duration, null);
    }

    public static void expandMatchParent(final View v, long duration, AnimationEndListener listener) {
        v.measure(FrameLayout.LayoutParams.MATCH_PARENT, 0);
        final int targetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation expandAnimation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? FrameLayout.LayoutParams.MATCH_PARENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        expandAnimation.setDuration(duration);
        if (listener != null) {
            expandAnimation.setAnimationListener(listener);
        }
        v.startAnimation(expandAnimation);
    }

    public static void collapse(final View v, long duration) {
        collapse(v, duration, null);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();
        int duration = ((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        collapse(v, duration, null);
    }

    public static void collapse(final View v, long duration, AnimationEndListener listener) {
        final int initialHeight = v.getMeasuredHeight();
        Animation collapseAnimation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        collapseAnimation.setDuration(duration);
        if (listener != null) {
            collapseAnimation.setAnimationListener(listener);
        }
        v.startAnimation(collapseAnimation);
    }




}
