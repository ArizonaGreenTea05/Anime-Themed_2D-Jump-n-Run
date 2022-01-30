package game;

import display.GameDisplay;
import game.state.GameState;
import game.state.State;
import input.Input;
import menu.Menu;
import utils.ElseUtils;

public class Game {

    private GameLoop gameLoop;

    public static int SPRITE_SIZE = 64;

    private final GameDisplay gameDisplay;
    private Input input;
    private State state;


    public Game(String version) {
        input = new Input();
        gameDisplay = new GameDisplay(input, "Jump'n'Run - "+ ElseUtils.makeNameNice(Menu.getGameTheme()) +" styled ", " | " + version + " |", this);
        state = new GameState(input, this);
    }

    public void stopGameLoop() {
        GameLoop.setRunning(false);
    }


    public void update(){
        state.updateObjects();
    }

    public void render() {
        gameDisplay.render(state);
    }

    public State getState(){
        return state;
    }

    public GameDisplay getGameDisplay(){
        return gameDisplay;
    }

    public GameLoop getGameLoop(){
        return gameLoop;
    }

    public void setGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }
}