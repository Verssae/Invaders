package com.invaders.invadersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView title;
    private Button play;
    private Button itemstore;
    private BlinkingRunnable br;
    public TextView[] textViews;
    private String[] colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = (TextView) findViewById(R.id.title);
        play = (Button) findViewById(R.id.play);
        itemstore = (Button) findViewById(R.id.itemstore);

        textViews = new TextView[]{title, play, itemstore};
        colors = new String[]{"GREEN", "WHITE", "WHITE"};

        br = new BlinkingRunnable(textViews, colors);
        Thread t = new Thread(br);
        t.start();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                br.changeColor(1, "GREEN");
                Intent intent = new Intent(getApplicationContext(), SelectDifficulty.class);
                startActivity(intent);
            }
        });

        itemstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                br.changeColor(2, "GREEN");
                Intent intent = new Intent(getApplicationContext(), ItemStore.class);
                startActivity(intent);
            }
        });
    }

    public int blinkingColor(String color) {
        if (color == "GREEN") {
            return Color.rgb(0, (int) (Math.random() * (255 - 155) + 155), 0);
        }
        if (color == "WHITE") {
            int RGB;
            RGB = (int) (Math.random() * (255 - 155) + 155);
            return Color.rgb(RGB, RGB, RGB);
        }
        if (color == "GRAY") {
            int RGB;
            RGB = (int) (Math.random() * (160 - 100) + 100);
            return Color.rgb(RGB, RGB, RGB);
        }
        return Color.WHITE;
    }
}