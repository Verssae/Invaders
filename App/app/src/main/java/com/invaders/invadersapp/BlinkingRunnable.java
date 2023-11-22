package com.invaders.invadersapp;

import android.widget.TextView;


public class BlinkingRunnable extends MainActivity implements Runnable {
    /** TextView array to be matched colors. */
    private TextView[] textViews;
    /** String array of colors for text effect matching textviews. */
    public String[] colors;
    /** String array of colors for initializing colors. */
    private String[] colorsInit;
    /** Time counter by 0.01 sec. */
    private int count = 0;
    /** Index for color changed textview. */
    private int changedidx;

    /**
     * Initialize BlinkingRunnable.
     *
     * @param t TextView array to be applied effect.
     *
     * @param c String array of colors that textviews need to be applied.
     */
    public BlinkingRunnable(TextView[] t, String[] c) {
        textViews = t;
        colors = c;
        colorsInit = new String[c.length];
        for (int i = 0; i < c.length; i++) {
            colorsInit[i] = c[i];
        }
    }

    /**
     * Start runnable.
     */
    @Override
    public void run() {
        while (true) {
            // Apply effect that make textview blinking matched colors.
            for (int i = 0; i < textViews.length; i++) {
                textViews[i].setTextColor(blinkingColor(colors[i]));
            }
            // Initialize to the original color with 0.01 seconds left until the color is restored.
            if (count == 1) {
                colors[changedidx] = colorsInit[changedidx];
                count--;
            }
            else if (count > 1) {
                count --;
            }
            // Term of blinking(10 millisecond)
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.getStackTrace();
            }
        }
    }

    /**
     * Change selected textview's color for 0.3 second.
     *
     * @param idx Index of selected textview.
     *
     * @param color New color of selected textview.
     */
    public void changeColor(int idx, String color) {
        changedidx = idx;
        colors[changedidx] = color;
        count = 30; // Color changes for 0.3 second.
    }
}
