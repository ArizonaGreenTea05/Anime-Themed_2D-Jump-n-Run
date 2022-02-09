package gameObjects;

import controller.Controller;
import core.Direction;
import core.Position;
import core.ScreenSize;
import motionAndAbilities.MotionAndAbilities;
import game.state.State;
import gfx.AnimationManager;

import java.awt.*;



// child class of MovingEntity;
// parent class of Player & NPC

public abstract class MovingEntity extends GameObject {

    protected Controller controller;
    protected AnimationManager animationManager;
    protected int lifes;
    protected int maxLifes;
    private MotionAndAbilities mAndA;
    private Direction direction;
    protected State state;

    public MovingEntity(Controller controller, MotionAndAbilities mAndA, Position position, State state) {
        // every moving entity has bounds 32p x 56p
        super(32,56, position.intX(), position.intY());
        this.controller = controller;
        this.mAndA = mAndA;
        this.direction = Direction.R;
        this.state = state;
    }

    @Override
    public void update() {
        // updates Motion and Abilities
        mAndA.update(controller, position, state);
        // updates position regarding Motion and Abilities
        position.apply(mAndA);
        manageDirection();
        // updates animation
        decideAnimation();
        animationManager.update(direction);
    }

    private void decideAnimation() {

        // decides which animation sheet should be used

        if(mAndA.isHitting()){
            animationManager.playAnimation("hit");
        } else if(mAndA.isMoving() || controller.isRequestingRight() || controller.isRequestingLeft()){
            animationManager.playAnimation("walk");
        } else {
            animationManager.playAnimation("stand");
        }
    }

    protected void manageDirection(){
        if(mAndA.isMoving()){
            this.direction = Direction.fromMotion(controller);
        }
    }

    public abstract void testIfAlive();


// getter methods

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

    @Override
    public Controller getController(){
        return controller;
    }
}
