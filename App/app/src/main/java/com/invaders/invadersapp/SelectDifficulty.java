package com.invaders.invadersapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SelectDifficulty extends AppCompatActivity {

    private Button easyButton, normalButton, hardButton, hardcoreButton, mainButton;
    private BlinkingRunnable br;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectdifficulty);

        Button easyButton = findViewById(R.id.button_easy);
        Button normalButton = findViewById(R.id.button_normal);
        Button hardButton = findViewById(R.id.button_hard);
        Button hardcoreButton = findViewById(R.id.button_hardcore);
        Button mainButton = findViewById(R.id.button_main);

        TextView[] textViews = new TextView[]{easyButton, normalButton, hardButton, hardcoreButton, mainButton};
        String[] colors = new String[]{"WHITE", "WHITE", "WHITE", "WHITE", "WHITE"};

        br = new BlinkingRunnable(textViews, colors);
        Thread t = new Thread(br);
        t.start();

        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다음 화면으로 이동
            }
        });

        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다음 화면으로 이동
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다음 화면으로 이동
            }
        });

        hardcoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다음 화면으로 이동
            }
        });

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectDifficulty.this, MainActivity.class);
                // MainActivity를 새로 시작하는 대신 기존에 존재하는 인스턴스로 돌아갑니다.
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }
}

