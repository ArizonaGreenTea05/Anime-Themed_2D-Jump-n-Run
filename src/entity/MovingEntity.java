package entity;

import controller.Controller;
import core.Direction;
import entity.motionAndAction.MotionAndAction;
import core.Position;
import core.ScreenSize;
import game.state.State;
import gfx.AnimationManager;

import java.awt.*;

public abstract class MovingEntity extends GameObject {

    protected Controller controller;
    protected AnimationManager animationManager;
    protected int lifes;
    protected int maxLifes;
    private MotionAndAction motion;
    private Direction direction;
    private State state;

    public MovingEntity(Controller controller, MotionAndAction motion, Position position, State state) {
        super(64,64, position.intX(), position.intY());
        this.controller = controller;
        this.motion = motion;
        this.direction = Direction.R;
        this.state = state;
    }
    @Override
    public void update() {
        motion.update(controller, position, state);
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
    public Direction getDirection(){
        return direction;
    }

    @Override
    public void setLifes(int lifes){
        this.lifes = lifes;
    }

    @Override
    public void setMaxLifes(int maxLifes){
        this.maxLifes = maxLifes;
    }

    @Override
    public void addLifes(int lifes){
        this.lifes += lifes;
    }

    @Override
    public void addMaxLifes(int maxLifes){
        this.maxLifes += maxLifes;
    }

    @Override
    public void subtractLifes(int lifes){
        this.lifes -= lifes;
    }

    @Override
    public void subtractMaxLifes(int maxLifes){
        this.maxLifes -= maxLifes;
    }

    @Override
    public Image getSprite() {
        return animationManager.getSprite();
    }

    @Override
    public MotionAndAction getMotion() {
        return motion;
    }
}
