package entity;

import controller.Controller;
import core.Direction;
import core.Motion;
import core.Position;
import core.ScreenSize;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

import java.awt.*;
import java.util.List;

public abstract class MovingEntity extends GameObject {

    protected Controller controller;
    protected AnimationManager animationManager;
    private Motion motion;
    private Direction direction;
    private List<GameObject> mapObjects;
    private List<GameObject> gameObjects;

    public MovingEntity(Controller controller, List<GameObject> mapObjects, List<GameObject> gameObjects) {
        super(64,64, ScreenSize.getLeftBorder(), ScreenSize.getGround()-64);
        this.controller = controller;
        this.motion = new Motion(2);
        this.direction = Direction.R;
        this.mapObjects = mapObjects;
        this.gameObjects = gameObjects;
    }

    @Override
    public void update() {
        motion.update(controller, position, mapObjects, gameObjects);
        position.apply(motion);
        manageDirection();
        decideAnimation(position);
        animationManager.update(direction);
    }

    private void decideAnimation(Position position) {

        double x = position.getX();

        if(motion.isHitting()){
            animationManager.playAnimation("hit");
        } else if(motion.isMoving() || (controller.isRequestingRight() && x >= ScreenSize.getRightBorder())|| (controller.isRequestingLeft() && x <= ScreenSize.getLeftBorder())){
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
