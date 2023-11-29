package com.invaders.invadersapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
    private double shipPosition = 0.5;
    private ImageView ship;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingame);

        ImageView left_icon = (ImageView) findViewById(R.id.left_icon);
        ImageView right_icon = (ImageView) findViewById(R.id.right_icon);
        ship = (ImageView) findViewById(R.id.ship);

        left_icon.setImageResource(R.drawable.left_button);
        right_icon.setImageResource(R.drawable.right_button);

        Log.i("Position", ship.getX()+"");

        left_icon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    left_icon.setImageResource(R.drawable.left_touched);
                    handlerLeft.post(runnableLeft);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
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
                    right_icon.setImageResource(R.drawable.right_touched);
                    handlerRight.post(runnableRight);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    right_icon.setImageResource(R.drawable.right_button);
                    handlerRight.removeCallbacks(runnableRight);
                }
                return true;
            }
        });
    }
    private Handler handlerLeft = new Handler(Looper.getMainLooper());
    private Runnable runnableLeft = new Runnable() {
        @Override
        public void run() {
            if (ship.getX()-8 > 0) ship.setX(ship.getX()-8);
            else ship.setX(0);
            Log.i("Position", ship.getX()+"");
            // 쉽 무빙
            // Call the runnable again
            handlerLeft.postDelayed(this, 17);
        }
    };
    private Handler handlerRight = new Handler(Looper.getMainLooper());
    private Runnable runnableRight = new Runnable() {
        @Override
        public void run() {
            if (ship.getX()+8 < 1008) ship.setX(ship.getX()+8);
            else ship.setX(1008);
            Log.i("Position", ship.getX()+"");
            // Call the runnable again
            handlerRight.postDelayed(this, 17);
        }
    };
}
