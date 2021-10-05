package game;

import display.GameDisplay;
import game.state.GameState;
import game.state.State;
import input.Input;
import menu.Menu;

public class Game {

    public static int SPRITE_SIZE = 64;

    private final GameDisplay gameDisplay;
    private Input input;
    private State state;


    public Game(int width, int height) {
        input = new Input();
        gameDisplay = new GameDisplay(width, height, input, "Jump'n'Run - "+ Menu.makeNameNice(Menu.getGameTheme()) +" styled");
        state = new GameState(input);
    }


    public void update(){
        state.update();
    }


    public void render() {
        gameDisplay.render(state);
    }

}