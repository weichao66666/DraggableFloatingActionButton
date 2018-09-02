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

    private float mDamping;
    private float mBorder;
    private boolean isDraggable;
    private int mLeftDragResId;
    private int mRightDragResId;

    private static float mInitRawX;
    private static int mCurrentResId;
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
            mDamping = array.getFloat(R.styleable.dFab_damping, DEFAULT_DAMPING);
            mBorder = array.getDimension(R.styleable.dFab_border, DEFAULT_BORDER);
            isDraggable = array.getBoolean(R.styleable.dFab_draggable, true);
            mLeftDragResId = array.getResourceId(R.styleable.dFab_leftDragSrc, 0);
            mRightDragResId = array.getResourceId(R.styleable.dFab_rightDragSrc, 0);
            mCurrentResId = array.getResourceId(R.styleable.dFab_src, 0);
            array.recycle();
        }
    }

    private void init() {
        this.post(new Runnable() {
            @Override
            public void run() {
                mInitRawX = DraggableFloatingActionButton.this.getX();
            }
        });
        setImageResource(mCurrentResId);
        setOnTouchListener(this);
    }

    public void setDraggable(boolean isDraggable) {
        this.isDraggable = isDraggable;
    }

    // set image src by resId when dFab is clicked
    public void setClickSrc(int resId) {
        setImageResource(resId);
        mCurrentResId = resId;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                dX = mInitRawX - motionEvent.getRawX();
                return true;
            case MotionEvent.ACTION_MOVE:
                deltaX = (motionEvent.getRawX() - mInitRawX + dX) * mDamping;
                if (deltaX < -mBorder) {
                    deltaX = -mBorder;
                } else if (deltaX < -CLICK_DRAG_TOLERANCE) {
                    setImageResource(mLeftDragResId);
                } else if (deltaX > CLICK_DRAG_TOLERANCE && deltaX <= mBorder) {
                    setImageResource(mRightDragResId);
                } else if (deltaX > mBorder) {
                    deltaX = mBorder;
                }
                if (isDraggable && Math.abs(deltaX) >= CLICK_DRAG_TOLERANCE) {
                    view.animate()
                            .x(mInitRawX + deltaX)
                            .setDuration(RESPONSE_ACTION_MOVE_DELAY)
                            .start();
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (onDraggableFABEventListener != null) {
                    if (Math.abs(deltaX) < CLICK_DRAG_TOLERANCE) {
                        onDraggableFABEventListener.onClick();
                    } else if (isDraggable) {
                        if (deltaX == mBorder) {
                            onDraggableFABEventListener.onDraggedRight();
                        } else if (deltaX == -mBorder) {
                            onDraggableFABEventListener.onDraggedLeft();
                        }
                    }
                }
                view.animate()
                        .x(mInitRawX)
                        .setDuration(RESET_ANIMATOR_DURATION)
                        .start();
                setImageResource(mCurrentResId);
                deltaX = 0;
                return true;
        }
        return super.onTouchEvent(motionEvent);
    }
}