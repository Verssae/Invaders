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
    private BlinkingRunnable br;
    private TextView select_level;
    Button btnLevel1, btnLevel2, btnLevel3, btnLevel4,btnLevel5, btnLevel6, btnLevel7, btnmain;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectlevel);

        select_level = (TextView) findViewById(R.id.leveltitle);
        btnLevel1 = findViewById(R.id.btnLevel1);
        btnLevel2 = findViewById(R.id.btnLevel2);
        btnLevel3 = findViewById(R.id.btnLevel3);
        btnLevel4 = findViewById(R.id.btnLevel4);
        btnLevel5 = findViewById(R.id.btnLevel5);
        btnLevel6 = findViewById(R.id.btnLevel6);
        btnLevel7 = findViewById(R.id.btnLevel7);
        btnmain = findViewById(R.id.btnmain);

        //setting for Blinking function
        TextView[] textViews = new TextView[]{select_level, btnLevel1, btnLevel2, btnLevel3, btnLevel4, btnLevel5, btnLevel6, btnLevel7, btnmain};
        String[] colors = new String[]{"GREEN","GREY", "WHITE", "WHITE", "WHITE", "WHITE", "GREY","WHITE","GREY"};

        //Blinking starts.
        br = new BlinkingRunnable(textViews, colors);
        Thread t = new Thread(br);
        t.start();

        btnLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // action of level1 button

            }
        });

        btnLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // action of level2 button

            }
        });

        btnLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // action of level3 button

            }
        });

        btnLevel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // action of level4 button

            }
        });

        btnLevel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // action of level5 button

            }
        });

        btnLevel6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // action of level6 button

            }
        });

        btnLevel7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // action of level7 button

            }
        });

        btnmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // action of main button

            }
        });

    }
}