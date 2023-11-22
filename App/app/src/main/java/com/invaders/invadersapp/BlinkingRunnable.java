package com.invaders.invadersapp;

import android.widget.Button;
import android.widget.TextView;

public class BlinkingRunnable extends MainActivity implements Runnable {
    TextView textView;
    Button play;
    Button itemstore;
    String color;
    public BlinkingRunnable(TextView tv, Button b1, Button b2, String c) {
        textView = tv;
        play = b1;
        itemstore = b2;
        color = c;
    }
    @Override
    public void run() {
        while (true) {
            textView.setTextColor(blinkingColor(color));
            play.setTextColor(blinkingColor("WHITE"));
            itemstore.setTextColor(blinkingColor("WHITE"));
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }

}
