package com.automoveviewlist;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/7/21.
 */

public class MoveView extends RelativeLayout {
    private View mContentView;
    private View mDragView;
    private int childCount;
    private long durationMillis = 1000;
    private long delayMillis = 500;

    public MoveView(Context context) {
        super(context);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        childCount = getChildCount();
        initLayout();
        initEventLisener();
    }

    private void initLayout() {
        mContentView = getChildAt(0);
        mDragView = getChildAt(1);
    }

    private void initEventLisener() {
        mDragView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return false;
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float preX = event.getX();
                float preY = event.getY();
                if (mDragView != null) {
                    slideview(mDragView,0,preY);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void slideview(final View view , final float p1, final float p2) {
        TranslateAnimation animation = new TranslateAnimation(p1, p2, 0, 0);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(durationMillis);
        animation.setStartOffset(delayMillis);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int left = view.getLeft() + (int) (p2 - p1);
                int top = view.getTop();
                int width = view.getWidth();
                int height = view.getHeight();
                view.clearAnimation();
                view.layout(left, top, left + width, top + height);
            }
        });
    }
}
