package entity;

import controller.Controller;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

public class NPC extends MovingEntity {

    public NPC(Controller controller, SpriteLibrary spriteLibrary) {
        super(controller, spriteLibrary);
        animationManager = new AnimationManager(spriteLibrary.getUnit("npc_1"));
    }

    @Override
    public void update() {
        super.update();
    }
}
