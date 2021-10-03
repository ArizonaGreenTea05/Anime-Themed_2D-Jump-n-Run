package game;

import controller.PlayerController;
import display.GameDisplay;
import entity.GameObject;
import entity.Player;
import gfx.SpriteLibrary;
import input.Input;
import menu.Menu;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public static int SPRITE_SIZE = 64;

    private final GameDisplay gameDisplay;
    private final List<GameObject> gameObjects;
    private final Input input;
    private final SpriteLibrary spriteLibrary;

    public Game(int width, int height) {
        input = new Input();
        gameDisplay = new GameDisplay(width, height, input, "Jump'n'Run - "+ Menu.makeNameNice(Menu.getGameTheme()) +" styled");
        gameObjects = new ArrayList<>();

        spriteLibrary = new SpriteLibrary();

        gameObjects.add(new Player(new PlayerController(input), spriteLibrary));

        //gameObjects.add(new Block(64,64, ScreenSize.getLeftBorder(), ScreenSize.getGround()));


    }


    public void update() {
        gameObjects.forEach(GameObject::update);
    }

    public void render() {
        gameDisplay.render(this);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

}