package com.invaders.invadersapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InGame extends AppCompatActivity {
    /** ImageView of ship */
    private ImageView ship;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingame);

        ImageView left_icon = (ImageView) findViewById(R.id.left_icon);
        ImageView right_icon = (ImageView) findViewById(R.id.right_icon);
        ship = (ImageView) findViewById(R.id.ship);
        // set img on left and right buttons
        left_icon.setImageResource(R.drawable.left_button);
        right_icon.setImageResource(R.drawable.right_button);

        left_icon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // change left button green when it touched
                    left_icon.setImageResource(R.drawable.left_touched);
                    handlerLeft.post(runnableLeft);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // change left button white
                    left_icon.setImageResource(R.drawable.left_button);
                    handlerLeft.removeCallbacks(runnableLeft);
                }
                return true;
            }
        });

        right_icon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // change right button green when it touched
                    right_icon.setImageResource(R.drawable.right_touched);
                    handlerRight.post(runnableRight);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // change right button white
                    right_icon.setImageResource(R.drawable.right_button);
                    handlerRight.removeCallbacks(runnableRight);
                }
                return true;
            }
        });
    }
    /** Handler for left button to control runnableLeft */
    private Handler handlerLeft = new Handler(Looper.getMainLooper());
    /** Runnable to move ship left */
    private Runnable runnableLeft = new Runnable() {
        @Override
        public void run() {
            if (ship.getX()-8 > 0) ship.setX(ship.getX()-8);
            else ship.setX(0);
            handlerLeft.postDelayed(this, 17); // fps = 1000/17
        }
    };
    /** Handler for right button to control runnableRight */
    private Handler handlerRight = new Handler(Looper.getMainLooper());
    /** Runnable to move ship right */
    private Runnable runnableRight = new Runnable() {
        @Override
        public void run() {
            if (ship.getX()+8 < 1008) ship.setX(ship.getX()+8);
            else ship.setX(1008);
            Log.i("Position", ship.getX()+"");
            handlerRight.postDelayed(this, 17); // fps = 1000/17
        }
    };
}
