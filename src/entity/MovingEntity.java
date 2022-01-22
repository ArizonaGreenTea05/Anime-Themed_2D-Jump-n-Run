package entity;

import controller.Controller;
import core.Direction;
import display.GameDisplay;
import entity.motionAndAbilities.MotionAndAbilities;
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
    private MotionAndAbilities mAndA;
    private Direction direction;
    private State state;

    public MovingEntity(Controller controller, MotionAndAbilities mAndA, Position position, State state) {
        super(64,64, position.intX(), position.intY());
        this.controller = controller;
        this.mAndA = mAndA;
        this.direction = Direction.R;
        this.state = state;
    }
    @Override
    public void update() {
        mAndA.update(controller, position, state);
        position.apply(mAndA);
        manageDirection();
        decideAnimation(position);
        animationManager.update(direction);
    }

    private void decideAnimation(Position position) {

        double x = position.getX();

        if(mAndA.isHitting()){
            animationManager.playAnimation("hit");
        } else if(mAndA.isMoving() || (controller.isRequestingRight() && x >= ScreenSize.getRightBorder())|| (controller.isRequestingLeft() && x <= ScreenSize.getLeftBorder())){
            animationManager.playAnimation("walk");
        } else {
            animationManager.playAnimation("stand");
        }
    }

    protected void manageDirection(){
        if(mAndA.isMoving()){
            this.direction = Direction.fromMotion(mAndA);
        }
    }

    protected void testIfAlive(){
        if(lifes == 0){
            state.setUpdatable(false);
            state.getGameObjects().remove(this);
            state.setUpdatable(true);
        }
    }

    @Override
    public Direction getDirection(){
        return direction;
    }

    @Override
    public Image getSprite() {
        return animationManager.getSprite();
    }

    @Override
    public MotionAndAbilities getMotionAndAbilities() {
        return mAndA;
    }
}
