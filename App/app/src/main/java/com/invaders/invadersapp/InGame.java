package com.invaders.invadersapp;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InGame extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingame);

        ImageView left_icon = (ImageView) findViewById(R.id.left_icon);
        ImageView right_icon = (ImageView) findViewById(R.id.right_icon);

    }
}
