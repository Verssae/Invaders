package engine;

import screen.GameScreen;

import java.util.Timer;
import java.util.TimerTask;

public class CountUpTimer {
    private Timer timer;
    private int seconds;
    private GameScreen gameScreen;
    private DrawManager drawManager;

    public CountUpTimer(GameScreen gameScreen, DrawManager drawManager) {
        this.gameScreen = gameScreen;
        this.drawManager = drawManager;
        timer = new Timer();
        seconds = 0;
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seconds++;

                drawManager.setTimerValue(seconds);
            }
        }, 1000, 1000);
    }

    public int getSeconds() {
        return seconds;
    }

    public void stop() {
        timer.cancel();
    }
}

