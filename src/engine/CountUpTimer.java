package engine;

import screen.GameScreen;

import java.util.Timer;
import java.util.TimerTask;

public class CountUpTimer {
    private Timer timer;
    private int seconds;
    private GameScreen gameScreen;

    public CountUpTimer(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        timer = new Timer();
        seconds = 0;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seconds++;
                gameScreen.drawManager.gameOver(gameScreen, gameScreen.levelFinished);
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
