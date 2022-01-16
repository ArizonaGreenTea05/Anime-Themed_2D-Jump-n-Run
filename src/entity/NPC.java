package entity;

import controller.Controller;
import core.Position;
import entity.motion.Motion;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

import java.util.List;

public class NPC extends MovingEntity {

    public NPC(Controller controller, Motion motion, Position position, SpriteLibrary spriteLibrary, List<GameObject> mapObjects, List<GameObject> gameObjects) {
        super(controller, motion, position, mapObjects, gameObjects);
        animationManager = new AnimationManager(spriteLibrary.getUnit("npc_1"));
        solid = true;
    }

    @Override
    public void update() {
        super.update();
    }
}
