package engine;

public class CountUpTimer {
    private long startTime;
    private long elapsedTime;
    private boolean running;
    private long gamePlayTime;
    private long roundStartTime;
    private long roundElapsedTime;

    public CountUpTimer() {
        startTime = System.currentTimeMillis();
        elapsedTime = 0;
        running = true;
        gamePlayTime = 0;
        roundStartTime = 0;
        roundElapsedTime = 0;
    }

    public void update() {
        if (running) {
            long currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - startTime;
            gamePlayTime += elapsedTime;
            roundElapsedTime = currentTime - roundStartTime;
            startTime = currentTime;
        }
    }

    public void start() {
        running = true;
        startTime = System.currentTimeMillis() - elapsedTime;
        roundStartTime = System.currentTimeMillis();
    }

    public void stop() {
        running = false;
    }

    public void startRound() {
        roundStartTime = System.currentTimeMillis();
    }

    public void endRound() {
        roundElapsedTime = System.currentTimeMillis() - roundStartTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public long getGamePlayTime() {
        return gamePlayTime;
    }

    public long getRoundElapsedTime() {
        return roundElapsedTime;
    }
}
