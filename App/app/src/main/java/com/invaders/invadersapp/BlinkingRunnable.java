package com.invaders.invadersapp;

import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class BlinkingRunnable extends MainActivity implements Runnable {
    TextView[] textView;
    String[] color;
    public BlinkingRunnable(TextView[] t, String[] c) {
        textView = t;
        color = c;
    }
    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < textView.length; i++) {
                textView[i].setTextColor(blinkingColor(color[i]));
            }
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace() ;
            }
        }
    }

}
