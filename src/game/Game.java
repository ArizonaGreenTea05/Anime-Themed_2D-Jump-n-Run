package game;

import display.GameDisplay;
import game.state.GameState;
import game.state.State;
import input.Input;
import menu.Menu;
import utils.ElseUtils;

public class Game {

    public static int SPRITE_SIZE = 64;

    private final GameDisplay gameDisplay;
    private Input input;
    private State state;


    public Game() {
        input = new Input();
        gameDisplay = new GameDisplay(input, "Jump'n'Run - "+ ElseUtils.makeNameNice(Menu.getGameTheme()) +" styled");
        state = new GameState(input);
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

}