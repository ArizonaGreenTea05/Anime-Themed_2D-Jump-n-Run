package entity;

import controller.Controller;
import core.Position;
import entity.motion.Motion;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

public class NPC extends MovingEntity {

    public NPC(Controller controller, Motion motion, Position position, SpriteLibrary spriteLibrary, State state) {
        super(controller, motion, position, state);
        animationManager = new AnimationManager(spriteLibrary.getUnit("npc_1"));
        solid = true;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void doAction(State state) {

    }
}
