package com.invaders.invadersapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;
public class selectlevel extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectlevel);

        // 레벨 1 버튼
        Button btnLevel1 = findViewById(R.id.btnLevel1);
        btnLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 레벨 1이 선택되었을 때의 동작

            }
        });

        Button btnLevel2 = findViewById(R.id.btnLevel2);
        btnLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 레벨 2이 선택되었을 때의 동작

            }
        });

        Button btnLevel3 = findViewById(R.id.btnLevel3);
        btnLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 레벨 3이 선택되었을 때의 동작

            }
        });

        Button btnLevel4 = findViewById(R.id.btnLevel4);
        btnLevel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 레벨 4가 선택되었을 때의 동작

            }
        });

        Button btnLevel5 = findViewById(R.id.btnLevel5);
        btnLevel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 레벨 5가 선택되었을 때의 동작

            }
        });

        Button btnLevel6 = findViewById(R.id.btnLevel6);
        btnLevel6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 레벨 6이 선택되었을 때의 동작

            }
        });

        Button btnLevel7 = findViewById(R.id.btnLevel7);
        btnLevel7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 레벨 7이 선택되었을 때의 동작

            }
        });
    }
}
