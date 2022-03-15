package game;

import display.GameDisplay;
import display.LoadingScreen;
import game.state.GameState;
import game.state.State;
import input.Input;

public class Game {

    private GameLoop gameLoop;

    public static int SPRITE_SIZE = 64;

    private final GameDisplay gameDisplay;
    private final Input input;
    private static State state;



    public Game(String version) {
        input = new Input();
        gameDisplay = new GameDisplay(input, "2D Jump'n'Run", " | " + version + " |", this);

        // loading screen initialized
        LoadingScreen loadingScreen = new LoadingScreen();
        Thread tLoadingScreen = new Thread(loadingScreen);
        tLoadingScreen.start();

        // initialization of GameState takes a long time because every GameObject needs to be initialized
        state = new GameState(input, this);

        // loading screen stopped
        tLoadingScreen.stop();
        loadingScreen.dispose();
    }


// updater and renderer

    public void update(){
        state.updateObjects();
        state.updateMap();
    }

    public void render() {
        gameDisplay.render(state);
    }


// stopping game loop when game stopped or paused

    public void stopGameLoop() {
        GameLoop.setRunning(false);
    }


// getter methods

    public static State getState(){
        return state;
    }

    public GameDisplay getGameDisplay(){
        return gameDisplay;
    }

    public GameLoop getGameLoop(){
        return gameLoop;
    }


// setter methods

    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

}