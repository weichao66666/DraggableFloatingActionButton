package com.zspirytus.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ZSpirytus on 2018/8/17.
 */

public class DraggableFloatingActionButton extends FloatingActionButton implements View.OnTouchListener {

    private static final float CLICK_DRAG_TOLERANCE = 0.382f * 0.382f * 100;
    private static final int RESET_ANIMATOR_DURATION = 382;
    private static final int RESPONSE_ACTION_MOVE_DELAY = 0;
    private static final float DEFAULT_DAMPING = 0.618f;
    private static final float DEFAULT_BORDER = 200f;

    private float damping;
    private float border;

    private static float initRawX;
    private float dX;
    private float deltaX;

    private OnDraggableFABEventListener onDraggableFABEventListener;

    public DraggableFloatingActionButton(Context context) {
        this(context, null);
    }

    public DraggableFloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DraggableFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadAttrs(context, attrs);
        init();
    }

    public void setOnDraggableFABEventListener(OnDraggableFABEventListener onDraggableFABEventListener) {
        this.onDraggableFABEventListener = onDraggableFABEventListener;
    }

    private void loadAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.dFab);
            damping = array.getFloat(R.styleable.dFab_damping, DEFAULT_DAMPING);
            border = array.getDimension(R.styleable.dFab_border, DEFAULT_BORDER);
            array.recycle();
        }
    }

    private void init() {
        setOnTouchListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        // get mFab initial location X
        initRawX = getX();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                dX = initRawX - motionEvent.getRawX();
                return true;
            case MotionEvent.ACTION_MOVE:
                deltaX = (motionEvent.getRawX() - initRawX + dX) * damping;
                if (deltaX < -border) {
                    deltaX = -border;
                } else if (deltaX < -CLICK_DRAG_TOLERANCE) {

                } else if (deltaX > CLICK_DRAG_TOLERANCE && deltaX <= border) {

                } else if (deltaX > border) {
                    deltaX = border;
                }
                if (Math.abs(deltaX) >= CLICK_DRAG_TOLERANCE) {
                    view.animate()
                            .x(initRawX + deltaX)
                            .setDuration(RESPONSE_ACTION_MOVE_DELAY)
                            .start();
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (onDraggableFABEventListener != null) {
                    if (Math.abs(deltaX) < CLICK_DRAG_TOLERANCE) {
                        onDraggableFABEventListener.onClick();
                    } else {
                        if (deltaX == border) {
                            onDraggableFABEventListener.onDraggedRight();
                        } else if (deltaX == -border) {
                            onDraggableFABEventListener.onDraggedLeft();
                        }
                    }
                }
                view.animate()
                        .x(initRawX)
                        .setDuration(RESET_ANIMATOR_DURATION)
                        .start();
                return true;
        }
        return super.onTouchEvent(motionEvent);
    }
}