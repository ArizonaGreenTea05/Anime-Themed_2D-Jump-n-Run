package entity;

import controller.Controller;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import menu.Menu;

import java.util.List;

public class Player extends MovingEntity {

    private static Controller controller;

    public Player(Controller controller, SpriteLibrary spriteLibrary, List<GameObject> mapObjects, List<GameObject> gameObjects) {
        super(controller, mapObjects, gameObjects);
        animationManager = new AnimationManager(spriteLibrary.getUnit(Menu.getPlayerName()));
        solid = true;
        this.controller = controller;
    }

    @Override
    public void update() {
        super.update();
    }

    public static Controller getController(){
        return controller;
    }
}
