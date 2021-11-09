package game;

import display.GameDisplay;

public class GameLoop implements Runnable {

    private int score = 100;

    private Game game;

    private static boolean running;
    private final double updateRate = 1.0d/100.0d;

    private long nextStatTime;
    private static int fps;
    private int ups;

    public GameLoop(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        running = true;
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        while(running) {
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000d;
            accumulator += lastRenderTimeInSeconds;
            lastUpdate = currentTime;

            if(accumulator >= updateRate) {
                while(accumulator >= updateRate) {
                    update();
                    accumulator -= updateRate;
                }
                render();
            }
            printStats();
            if (score == 0) stop();
        }
    }


    private void printStats() {
        if(System.currentTimeMillis() > nextStatTime) {
            //System.out.println(String.format("FPS: %d, UPS: %d", fps, ups));
            GameDisplay.setFPS(String.format("FPS: %d", fps));
            fps = 0;
            ups = 0;
            score--;
            GameDisplay.setScoreLabel(score);
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    private void stop() {
        running = false;
        GameDisplay.showFailed();
    }


    private void update() {
        game.update();
        ups++;
    }

    private void render() {
        game.render();
        fps++;
    }

    public static void setRunning(boolean b){
        running = b;
    }
}
