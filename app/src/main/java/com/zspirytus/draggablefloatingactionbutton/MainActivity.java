package com.zspirytus.draggablefloatingactionbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.zspirytus.mylibrary.DraggableFloatingActionButton;
import com.zspirytus.mylibrary.OnDraggableFABEventListener;

public class MainActivity extends AppCompatActivity {

    private DraggableFloatingActionButton mFab;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
        initView();
        setListener();
    }

    private void loadData(){
        mToast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
    }

    private void initView(){
        mFab = (DraggableFloatingActionButton) findViewById(R.id.fab);
    }

    private void setListener(){
        mFab.setOnDraggableFABEventListener(new OnDraggableFABEventListener() {
            @Override
            public void onClick() {
                mToast.setText("click");
                mToast.show();
            }

            @Override
            public void onDraggedLeft() {
                mToast.setText("drag left");
                mToast.show();
            }

            @Override
            public void onDraggedRight() {
                mToast.setText("drag right");
                mToast.show();
            }
        });
    }
}
