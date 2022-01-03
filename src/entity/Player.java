package entity;

import controller.Controller;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import menu.Menu;

public class Player extends MovingEntity {

    public Player(Controller controller, SpriteLibrary spriteLibrary) {
        super(controller, spriteLibrary);
        animationManager = new AnimationManager(spriteLibrary.getUnit(Menu.getPlayerName()));
        solid = true;
    }

    @Override
    public void update() {
        super.update();
    }
}
