package com.invaders.invadersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView title = (TextView) findViewById(R.id.title);
        Button play = (Button) findViewById(R.id.play);
        Button itemstore = (Button) findViewById(R.id.itemstore);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                title.setTextColor(Color.parseColor(blinkingColorString("GREEN")));
                play.setTextColor(Color.parseColor(blinkingColorString("WHITE")));
                itemstore.setTextColor(Color.parseColor(blinkingColorString("WHITE")));
            }
        };

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectDifficulty.class);
                startActivity(intent);
            }
        });


        itemstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ItemStore.class);
                startActivity(intent);
            }
        });

        class NewRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace() ;
                    }
                    handler.sendEmptyMessage(0) ;
                }
            }
        }
        NewRunnable nr = new NewRunnable();
        Thread t = new Thread(nr);
        t.start();
    }

    @Override
    protected void onDestroy() {
        // 액티비티가 소멸될 때 핸들러 제거
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    private String blinkingColorString(String color) {
        if (color == "GREEN") {
            color = "#00"+Integer.toHexString((int) (Math.random() * (255 - 155) + 155)).toLowerCase()+"00";
        }
        if (color == "WHITE") {
            String RGB = Integer.toHexString((int) (Math.random() * (255 - 155) + 155)).toLowerCase();
            color = "#"+RGB+RGB+RGB;
        }
        if (color == "GRAY") {
            String RGB = Integer.toHexString((int) (Math.random() * (160 - 100) + 100)).toLowerCase();
            color = "#"+RGB+RGB+RGB;
        }
        return color;
    }
}