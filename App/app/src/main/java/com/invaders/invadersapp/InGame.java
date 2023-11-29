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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingame);

        ImageView left_icon = (ImageView) findViewById(R.id.left_icon);
        ImageView right_icon = (ImageView) findViewById(R.id.right_icon);

        left_icon.setImageResource(R.drawable.left_button);
        right_icon.setImageResource(R.drawable.right_button);

        left_icon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    left_icon.setImageResource(R.drawable.left_touched);
                    handlerLeft.post(runnableLeft);
                    Log.i("Position", shipPosition+"");
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
                    Log.i("Position", shipPosition+"");
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
            if (shipPosition-0.02 > 0) shipPosition -= 0.02;
            else shipPosition = 0;
            Log.i("Position", shipPosition+"");
            // 쉽 무빙
            // Call the runnable again
            handlerLeft.postDelayed(this, 100);
        }
    };
    private Handler handlerRight = new Handler(Looper.getMainLooper());
    private Runnable runnableRight = new Runnable() {
        @Override
        public void run() {
            if (shipPosition+0.02 < 1) shipPosition += 0.02;
            else shipPosition = 1;
            Log.i("Position", shipPosition+"");
            // Call the runnable again
            handlerRight.postDelayed(this, 100);
        }
    };
}
