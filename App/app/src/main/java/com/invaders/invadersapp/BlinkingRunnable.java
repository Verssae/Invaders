package com.invaders.invadersapp;

import android.widget.TextView;


public class BlinkingRunnable extends MainActivity implements Runnable {

    private TextView[] textViews;
    public String[] colors;
    private String[] colorsInit;
    private int count = 0;
    private int changedidx;
    public BlinkingRunnable(TextView[] t, String[] c) {
        textViews = t;
        colors = c;
        colorsInit = new String[c.length];
        for (int i = 0; i < c.length; i++) {
            colorsInit[i] = c[i];
        }
    }
    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < textViews.length; i++) {
                textViews[i].setTextColor(blinkingColor(colors[i]));
            }
            if (count == 1) {
                colors[changedidx] = colorsInit[changedidx];
                count--;
            }
            else if (count > 1) {
                count --;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.getStackTrace();
            }
        }
    }
    public void changeColor(int idx, String color) {
        changedidx = idx;
        colors[changedidx] = color;
        count = 30;
    }
}
