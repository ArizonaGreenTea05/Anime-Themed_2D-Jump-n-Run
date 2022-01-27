package game;

import display.GameDisplay;

public class GameLoop implements Runnable {

    private int score = 100;

    private Game game;
    private GameDisplay gameDisplay;

    private static boolean running;
    private final double updateRate = 1.0/120.0;

    private long nextStatTime;
    private static int fps;
    private int ups;

    public GameLoop(Game game) {
        this.game = game;
        this.gameDisplay = game.getGameDisplay();
        game.setGameLoop(this);
    }

    @Override
    public void run() {
        running = true;
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        while(running){
                currentTime = System.currentTimeMillis();
                double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000d;
                accumulator += lastRenderTimeInSeconds;
                lastUpdate = currentTime;

                if (accumulator >= updateRate) {
                    while (accumulator >= updateRate) {
                        update();
                        accumulator -= updateRate;
                    }
                    render();
                }
                printStats();
                if (score == 0) {failed();}
        }
    }


    private void printStats() {
        if(System.currentTimeMillis() > nextStatTime) {
            System.out.printf("FPS: %d, UPS: %d%n", fps, ups);
            gameDisplay.setFPS("FPS: " + fps);
            fps = 0;
            ups = 0;
            score--;
            gameDisplay.setScoreLabel(score);
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    private void failed() {
        running = false;
        gameDisplay.showFailed();
    }


    private void update() {
        game.update();
        ups++;
    }

    private void render() {
        game.render();
        fps++;
    }

    public void addScore(int score) {
        this.score = Math.min(this.score + score, 100);
        gameDisplay.setScoreLabel(this.score);
    }

    public static void setRunning(boolean b){
        running = b;
    }
}
