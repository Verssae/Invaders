package com.invaders.invadersapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SelectDifficulty extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectdifficulty);

        Button easyButton = findViewById(R.id.button_easy);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다음 화면으로 이동
            }
        });

        Button normalButton = findViewById(R.id.button_normal);
        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다음 화면으로 이동
            }
        });

        Button hardButton = findViewById(R.id.button_hard);
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다음 화면으로 이동
            }
        });

        Button hardcoreButton = findViewById(R.id.button_hardcore);
        hardcoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다음 화면으로 이동
            }
        });

        Button mainButton = findViewById(R.id.button_main);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectDifficulty.this, MainActivity.class);
                // MainActivity를 새로 시작하는 대신 기존에 존재하는 인스턴스로 돌아갑니다.
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // 중간, 어려움 난이도 버튼에 대한 코드도 추가...
    }
}

