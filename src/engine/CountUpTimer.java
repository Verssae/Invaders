package engine;
public class CountUpTimer {
    private long startTime;
    private long elapsedTime;
    private boolean running;

    public CountUpTimer() {
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
        running = true;
    }

    public void update() {
        if (running) {
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
        return elapsedTime;
    }
}

