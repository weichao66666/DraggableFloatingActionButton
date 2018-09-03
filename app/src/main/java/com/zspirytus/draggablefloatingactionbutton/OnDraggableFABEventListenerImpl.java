package com.zspirytus.draggablefloatingactionbutton;

import android.util.Log;

import com.zspirytus.mylibrary.DraggableFloatingActionButton;
import com.zspirytus.mylibrary.OnDraggableFABEventListener;

/**
 * Created by ZSpirytus on 2018/9/2.
 */

public class OnDraggableFABEventListenerImpl implements OnDraggableFABEventListener{

    private MainActivity mActivity;
    private DraggableFloatingActionButton mDFab;

    public OnDraggableFABEventListenerImpl(MainActivity activity) {
        mActivity = activity;
        mDFab = activity.findViewById(R.id.fab);
    }

    @Override
    public void onClick() {
        if(mActivity.isPlaying()) {
            mDFab.setClickSrc(R.drawable.ic_play_arrow_white_48dp);
            mActivity.setPlayState(false);
        } else {
            mDFab.setClickSrc(R.drawable.ic_pause_white_48dp);
            mActivity.setPlayState(true);
        }
        Log.e(this.getClass().getSimpleName(), "click");
    }

    @Override
    public void onDraggedLeft() {
        Log.e(this.getClass().getSimpleName(), "leftDrag");
    }

    @Override
    public void onDraggedRight() {
        Log.e(this.getClass().getSimpleName(), "rightDrag");
    }

    @Override
    public void onLongClick() {
        Log.e(this.getClass().getSimpleName(), "longClick");
    }
}
