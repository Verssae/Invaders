package engine;

public class CountUpTimer {
    private long startTime;
    private long elapsedTime;
    private boolean running;
    private long GameplayTime;
    private boolean pause;

    public CountUpTimer() {
        startTime = System.currentTimeMillis() + 6000;
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


    public long getElapsedTime() {
        long currentTime = System.currentTimeMillis();
        if (running && !pause) {
            elapsedTime = currentTime - startTime;
        }

        return Math.max(0, elapsedTime);
    }

    public long getClearTime() {
        return Math.max(0, GameplayTime);
    }

    public void recordClearTime() {
        GameplayTime = elapsedTime;
    }
}
