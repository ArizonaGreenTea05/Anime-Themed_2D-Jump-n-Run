package entity;

import controller.Controller;
import core.Direction;
import core.Motion;
import core.Position;
import core.ScreenSize;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

import java.awt.*;

public abstract class MovingEntity extends GameObject {

    private Controller controller;
    private Motion motion;
    private AnimationManager animationManager;
    private Direction direction;

    public MovingEntity(Controller controller, SpriteLibrary spriteLibrary) {
        super(64,64, ScreenSize.getLeftBorder(), ScreenSize.getGround()-64);
        this.controller = controller;
        this.motion = new Motion(2);
        this.direction = Direction.R;
        animationManager = new AnimationManager(spriteLibrary.getUnit());
    }

    @Override
    public void update() {
        motion.update(controller);
        position.apply(motion);
        manageDirection();
        decideAnimation();
        animationManager.update(direction);
    }

    private void decideAnimation() {
        if(motion.isMoving() || (controller.isRequestingRight() && Position.getX() >= ScreenSize.getRightBorder())|| (controller.isRequestingLeft() && Position.getX() <= ScreenSize.getLeftBorder())){
            animationManager.playAnimation("walk");
        } else {
            animationManager.playAnimation("stand");
        }
    }

    protected void manageDirection(){
        if(motion.isMoving()){
            this.direction = Direction.fromMotion(motion);
        }
    }

    @Override
    public Image getSprite() {
        return animationManager.getSprite();
    }
}
