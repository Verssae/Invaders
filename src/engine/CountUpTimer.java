package engine;

public class CountUpTimer {
    private long startTime;
    private long elapsedTime;
    private boolean running;
    private long GameplayTime;
    private boolean pause;

    public CountUpTimer() {
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
        running = true;
        GameplayTime = 0;
        pause = false;
    }

    public void update() {
        if (running && !pause) {
            long currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - startTime;
        }
    }

    public void start() {
        running = true;
        startTime = System.currentTimeMillis() - elapsedTime;
    }

    public void stop() {
        running = false;
    }

    public void pause() {
        pause = true;
    }

    public void resume() {
        pause = false;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public long getClearTime() {
        return GameplayTime;
    }

    public void recordClearTime() {
        GameplayTime = elapsedTime;
    }
}
