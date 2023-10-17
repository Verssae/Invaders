package engine;

public class CountUpTimer {
    private long startTime;
    private long elapsedTime;
    private boolean running;
    private long gamePlayTime;

    public CountUpTimer() {
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
        running = true;
        gamePlayTime = 0;
    }

    public void update() {
        if (running) {
            long currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - startTime;
            gamePlayTime += elapsedTime;
            startTime = currentTime;
        }
    }

    public void start() {
        running = true;
        startTime = System.currentTimeMillis() - elapsedTime;
    }

    public void stop() {

        running = false;
    }

    public long getElapsedTime() {

        return elapsedTime;
    }

    public long getGamePlayTime() {

        return gamePlayTime;
    }
}