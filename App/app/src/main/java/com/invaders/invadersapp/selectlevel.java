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
    public int level;



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
                br.changeColor(1, "GREEN");
                Intent intent = new Intent(getApplicationContext(), InGame.class);
                level = 1;
                startActivity(intent);
            }
        });

        btnLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // action of level2 button
                br.changeColor(2, "GREEN");
                Intent intent = new Intent(getApplicationContext(), InGame.class);
                level = 2;
                startActivity(intent);
            }
        });

        btnLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // action of level3 button
                br.changeColor(3, "GREEN");
                Intent intent = new Intent(getApplicationContext(), InGame.class);
                level = 3;
                startActivity(intent);
            }
        });

        btnLevel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // action of level4 button
                br.changeColor(4, "GREEN");
                Intent intent = new Intent(getApplicationContext(), InGame.class);
                level = 4;
                startActivity(intent);
            }
        });

        btnLevel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // action of level5 button
                br.changeColor(5, "GREEN");
                Intent intent = new Intent(getApplicationContext(), InGame.class);
                level = 5;
                startActivity(intent);
            }
        });

        btnLevel6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // action of level6 button
                br.changeColor(6, "GREEN");
                Intent intent = new Intent(getApplicationContext(), InGame.class);
                level = 6;
                startActivity(intent);
            }
        });

        btnLevel7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // action of level7 button
                br.changeColor(7, "GREEN");
                Intent intent = new Intent(getApplicationContext(), InGame.class);
                level = 7;
                startActivity(intent);
            }
        });

        //go back to main when click 'mainbutton'
        btnmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                br.changeColor(8, "GREEN");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}