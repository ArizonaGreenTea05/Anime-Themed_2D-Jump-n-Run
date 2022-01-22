package entity.motionAndAbilities;

import controller.Controller;
import core.Position;
import core.Vector2D;
import entity.GameObject;
import game.state.State;

import java.util.List;

import static core.Direction.*;
import static java.lang.Thread.sleep;

public abstract class MotionAndAbilities {

    protected Vector2D vector;
    protected final double speed;
    protected Position position;
    protected double x;
    protected double y;
    protected List<GameObject> mapObjects;
    protected List<GameObject> gameObjects;
    protected State state;
    protected boolean canHit;
    protected Controller controller;

    public MotionAndAbilities(double speed) {
        this.speed = speed;
        this.vector = new Vector2D(0, 0);
    }

    public abstract void update(Controller controller, Position position, State state);

    protected boolean hasGround() {
        if(testHasGround(mapObjects)){
            return true;
        } else {
            return testHasGround(gameObjects);
        }
    }

    private boolean testHasGround(List<GameObject> objects){
        for (GameObject object : objects) {

            if (object.getMotionAndAbilities() != this && object.isSolid()) {
                int blockPosX = object.getPosition().intX();
                int blockPosY = object.getPosition().intY();

                if (blockPosX < x + 61 && blockPosX > x - object.getSize().getWidth()+3) {
                    if (blockPosY < y + 66 && blockPosY > y + 60) {
                        position.setY(blockPosY - 64);
                        return true;
                    }
                }
            }
        }

        return false;
    }


    protected boolean topSpace() {
        if(!testTopSpace(mapObjects)){
            return false;
        } else {
            return testTopSpace(gameObjects);
        }

    }

    private boolean testTopSpace(List<GameObject> objects){
        for (GameObject object : objects) {

            if (object.getMotionAndAbilities() != this && object.isSolid()) {
                int blockPosX = object.getPosition().intX();
                int blockPosY = object.getPosition().intY();

                if (blockPosX < x + 61 && blockPosX > x - object.getSize().getWidth()+3) {
                    if (blockPosY < y - 56 && blockPosY > y - 66) {
                        if(actionCaused()) {
                            object.doActionOnContact(state);
                        }
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean actionCaused() {
        return this.canCauseBlockAction() && this.isJumping();
    }

    protected boolean rightSpace() {
        if(!testRightSpace(mapObjects)){
            return false;
        } else {
            return testRightSpace(gameObjects);
        }
    }

    private boolean testRightSpace(List<GameObject> objects){
        for (GameObject object : objects) {

            if (object.getMotionAndAbilities() != this && object.isSolid()) {
                int blockPosX = object.getPosition().intX();
                int blockPosY = object.getPosition().intY();

                if (blockPosY < y + 62 && blockPosY > y - 62) {
                    if (blockPosX < x + 64 && blockPosX > x - 32) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected boolean leftSpace() {
        if(!testLeftSpace(mapObjects)){
            return false;
        } else {
            return testLeftSpace(gameObjects);
        }
    }

    private boolean testLeftSpace(List<GameObject> objects){
        for (GameObject object : objects) {
            if (object.getMotionAndAbilities() != this && object.isSolid()) {
                int blockPosX = object.getPosition().intX();
                int blockPosY = object.getPosition().intY();

                if (blockPosY < y + 62 && blockPosY > y - 62) {
                    if (blockPosX > x - 64 && blockPosX < x + 32) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected double getFallSpeed(double x){
        double d = -0.01 * x*x + 2.9;
        if(d < 5 && d >= 0 ) return d;
        return 4;
    }


    public abstract Vector2D getVector();

    public abstract boolean isMoving();

    public abstract boolean isJumping();

    public abstract boolean isHitting();

    public abstract boolean isSitting();

    public abstract boolean canCauseBlockAction();

    protected void damage(State state){
        if(canHit) {
            canHit = false;

            List<GameObject> gameObjects = state.getGameObjects();
            boolean updatable = state.getUpdatable();
            for (int i = 0; i < gameObjects.size() && updatable; i++) {
                GameObject gameObject = gameObjects.get(i);
                if(gameObject.getMotionAndAbilities() != this) {
                    int posX = gameObject.getPosition().intX();
                    int posY = gameObject.getPosition().intY();
                    if (posY < y + 32 && posY > y - 32) {
                        if (gameObject.getDirection() == R && posX < x + 92 && posX > x) {
                            gameObject.subtractLifes(1);
                        } else if (gameObject.getDirection() == L && posX < x && posX > x - 92) {
                            gameObject.subtractLifes(1);
                        }
                    }
                }
                updatable = state.getUpdatable();
            }
        }
    }

}
