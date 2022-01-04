package entity;

import controller.Controller;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

import java.util.List;

public class NPC extends MovingEntity {

    public NPC(Controller controller, SpriteLibrary spriteLibrary, List<GameObject> mapObjects) {
        super(controller, spriteLibrary, mapObjects);
        animationManager = new AnimationManager(spriteLibrary.getUnit("npc_1"));
        solid = true;
    }

    @Override
    public void update() {
        super.update();
    }
}
