package entity;

import controller.Controller;
import core.Position;
import entity.motionAndAction.MotionAndAction;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

import java.awt.*;

public class NPC extends MovingEntity {

    public NPC(Controller controller, MotionAndAction motion, Position position, SpriteLibrary spriteLibrary, State state) {
        super(controller, motion, position, state);
        animationManager = new AnimationManager(spriteLibrary.getUnit("npc_1"));
        solid = true;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics graphics) {
        if (shown(this)) {
            graphics.drawImage(
                    this.getSprite(),
                    this.getPosition().intX(),
                    this.getPosition().intY(),
                    null
            );
        }
    }

    @Override
    public void doAction(State state) {

    }
}
