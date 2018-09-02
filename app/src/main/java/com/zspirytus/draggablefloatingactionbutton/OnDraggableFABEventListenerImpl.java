package com.zspirytus.draggablefloatingactionbutton;

import android.support.design.widget.FloatingActionButton;

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
    }

    @Override
    public void onDraggedLeft() {

    }

    @Override
    public void onDraggedRight() {

    }

}
