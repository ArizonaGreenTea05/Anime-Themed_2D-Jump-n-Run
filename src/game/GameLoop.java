package game;

import display.GameDisplay;

public class GameLoop implements Runnable {

    private static final double scoreBackup = 100;
    private static double score = scoreBackup;

    private final Game game;
    private final GameDisplay gameDisplay;

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

        // score counter counts in 0.1 second steps
        new Thread(() -> {
            while(running) {
                gameDisplay.setScoreLabel(score);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                score -= 0.1;
            }
        }).start();


        while(running){
                currentTime = System.currentTimeMillis();
                double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000d;
                accumulator += lastRenderTimeInSeconds;
                lastUpdate = currentTime;

                // updates ca. 120 times per second
                if (accumulator >= updateRate) {
                    while (accumulator >= updateRate) {
                        update();
                        accumulator -= updateRate;
                    }
                    // renders when updated
                    render();
                }

                printStats();

                if (score <= 0) {
                    failed();
                }
        }
    }


    private void update() {
        game.update();
        ups++;
    }

    private void render() {
        game.render();
        fps++;
    }


    private void printStats() {
        // prints stats every second
        if(System.currentTimeMillis() > nextStatTime) {
            // prints fps & ups in terminal
            System.out.printf("FPS: %d, UPS: %d%n", fps, ups);
            // prints fps in fps-label
            gameDisplay.setFPS(fps);
            fps = 0;
            ups = 0;
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    private void failed() {
        // when failed game loop stopped and failed-panel shown
        running = false;
        gameDisplay.showFailed();
    }

    public void addScore(int score) {
        this.score = Math.min(this.score + score, 100);
        gameDisplay.setScoreLabel(this.score);
    }

    public static void setRunning(boolean b){
        running = b;
    }

    public void resetScore() {
        score = scoreBackup;
    }
}
