package com.zspirytus.draggablefloatingactionbutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zspirytus.mylibrary.DraggableFloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private DraggableFloatingActionButton mFab;

    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListener();
    }

    private void initView(){
        mFab = (DraggableFloatingActionButton) findViewById(R.id.fab);
    }

    private void setListener(){
        mFab.setOnDraggableFABEventListener(new OnDraggableFABEventListenerImpl(this));
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlayState(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}
