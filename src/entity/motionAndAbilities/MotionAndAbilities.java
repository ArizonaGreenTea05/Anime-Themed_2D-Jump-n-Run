package entity.motionAndAbilities;

import controller.Controller;
import core.Position;
import core.ScreenSize;
import core.Size;
import core.Vector2D;
import entity.GameObject;
import game.state.State;

import java.util.List;

import static core.Direction.*;

public abstract class MotionAndAbilities {

    protected Vector2D vector;
    protected double speed;
    protected final double normalSpeed;
    protected Position position;
    protected double x;
    protected double y;
    protected List<GameObject> mapObjects;
    protected List<GameObject> gameObjects;
    protected State state;
    protected boolean canHit;
    protected Controller controller;
    static int playerPosInList;
    protected boolean falling;
    protected boolean sitting;
    protected double gravity;
    protected final int ground = ScreenSize.getGround();
    protected int savePosYJump = ground;
    protected GameObject thisGameObject;
    protected Size thisGameObjectSize;

    public MotionAndAbilities(double speed) {
        this.speed = speed;
        this.normalSpeed = speed;
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
                int objectWidth = object.getSize().getWidth();
                int objectHeight = object.getSize().getHeight();
                int objectSideSpace = (64-objectWidth)/2;
                int objectTopSpace = (64-objectHeight);
                int blockPosX = object.getPosition().intX() + objectSideSpace;
                int blockPosY = object.getPosition().intY() + objectTopSpace;


                int thisGameObjectWidth = thisGameObjectSize.getWidth();
                int thisGameObjectHeight = thisGameObjectSize.getHeight();
                int thisGameObjectSideSpace = (64-thisGameObjectWidth)/2;
                int thisGameObjectTopSpace = (64-thisGameObjectHeight);
                int posX = (int) (x+thisGameObjectSideSpace);
                int posY = (int) (y+thisGameObjectTopSpace);

                if (blockPosX < posX + thisGameObjectWidth + 2 && blockPosX > posX - object.getSize().getWidth() - 2) {
                    if (blockPosY < posY + thisGameObjectHeight + 2 && blockPosY > posY + thisGameObjectHeight - 2) {
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
                int objectWidth = object.getSize().getWidth();
                int objectHeight = object.getSize().getHeight();
                int objectSideSpace = (64-objectWidth)/2;
                int objectTopSpace = (64-objectHeight);
                int blockPosX = object.getPosition().intX() + objectSideSpace;
                int blockPosY = object.getPosition().intY() + objectTopSpace;


                int thisGameObjectWidth = thisGameObjectSize.getWidth();
                int thisGameObjectHeight = thisGameObjectSize.getHeight();
                int thisGameObjectSideSpace = (64-thisGameObjectWidth)/2;
                int thisGameObjectTopSpace = (64-thisGameObjectHeight);
                int posX = (int) (x+thisGameObjectSideSpace);
                int posY = (int) (y+thisGameObjectTopSpace);

                if (blockPosX < posX + thisGameObjectWidth && blockPosX > posX - object.getSize().getWidth()+3) {
                    if (blockPosY < posY - objectHeight + 2 && blockPosY > posY - objectHeight - 2) {
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
                int objectWidth = object.getSize().getWidth();
                int objectHeight = object.getSize().getHeight();
                int objectSideSpace = (64-objectWidth)/2;
                int objectTopSpace = (64-objectHeight);
                int blockPosX = object.getPosition().intX() + objectSideSpace;
                int blockPosY = object.getPosition().intY() + objectTopSpace;

                int thisGameObjectWidth = thisGameObjectSize.getWidth();
                int thisGameObjectHeight = thisGameObjectSize.getHeight();
                int thisGameObjectSideSpace = (64-thisGameObjectWidth)/2;
                int thisGameObjectTopSpace = (64-thisGameObjectHeight);
                int posX = (int) (x+thisGameObjectSideSpace);
                int posY = (int) (y+thisGameObjectTopSpace);

                if (blockPosY < posY + thisGameObjectHeight && blockPosY > posY - objectHeight) {
                    if (blockPosX < posX + objectWidth && blockPosX > posX - objectWidth/2) {
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
                int objectWidth = object.getSize().getWidth();
                int objectHeight = object.getSize().getHeight();
                int objectSideSpace = (64-objectWidth)/2;
                int objectTopSpace = (64-objectHeight);
                int blockPosX = object.getPosition().intX() + objectSideSpace;
                int blockPosY = object.getPosition().intY() + objectTopSpace;

                int thisGameObjectWidth = thisGameObjectSize.getWidth();
                int thisGameObjectHeight = thisGameObjectSize.getHeight();
                int thisGameObjectSideSpace = (64-thisGameObjectWidth)/2;
                int thisGameObjectTopSpace = (64-thisGameObjectHeight);
                int posX = (int) (x+thisGameObjectSideSpace);
                int posY = (int) (y+thisGameObjectTopSpace);

                if (blockPosY < posY + thisGameObjectHeight && blockPosY > posY - objectHeight) {
                    if (blockPosX > posX - objectWidth && blockPosX < posX + objectWidth/2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected double getFallSpeed(double x){
        double d = -0.0005 * x*x + 1.5;
        if(d > 2) {return 1.5;}
        if(d < 0) {return 0;}
        return d;
    }

    protected void sprint(boolean sprint) {
        if(sprint && speed == normalSpeed) {
            speed++;
        } else if(!sprint){
            speed = normalSpeed;
        }
    }


    public abstract Vector2D getVector();

    public abstract boolean isMoving();

    public abstract boolean isJumping();

    public abstract boolean isHitting();

    public abstract boolean isSitting();

    public abstract boolean canCauseBlockAction();

    protected void damage(){
        if(canHit) {
            canHit = false;

            boolean updatable = state.getUpdatable();

            GameObject thisGameObject = gameObjects.get(findThisGameObjectInList());

            for (int i = 0; i < gameObjects.size() && updatable; i++) {
                GameObject gameObject = gameObjects.get(i);
                if(gameObject.getMotionAndAbilities() != this) {
                    int objectWidth = gameObject.getSize().getWidth();
                    int objectHeight = gameObject.getSize().getHeight();
                    int objectSideSpace = (64-objectWidth)/2;
                    int objectTopSpace = (64-objectHeight);
                    int objectPosX = gameObject.getPosition().intX() + objectSideSpace;
                    int objectPosY = gameObject.getPosition().intY() + objectTopSpace;

                    int thisGameObjectWidth = thisGameObjectSize.getWidth();
                    int thisGameObjectHeight = thisGameObjectSize.getHeight();
                    int thisGameObjectSideSpace = (64-thisGameObjectWidth)/2;
                    int thisGameObjectTopSpace = (64-thisGameObjectHeight);
                    int posX = (int) (x+thisGameObjectSideSpace);
                    int posY = (int) (y+thisGameObjectTopSpace);

                    if (posY < y + 32 && posY > y - 32) {
                        if (thisGameObject.getDirection() == R && objectPosX < posX + thisGameObjectWidth + 32 && objectPosX > posX) {
                            gameObject.subtractLifes(1);
                        } else if (thisGameObject.getDirection() == L && objectPosX < posX && objectPosX > posX - objectWidth - 32) {
                            gameObject.subtractLifes(1);
                        }
                    }
                }
                updatable = state.getUpdatable();
            }
        }
    }

    protected int findThisGameObjectInList(){
        for (int i = 0; i < gameObjects.size(); i++) {
            if(gameObjects.get(i).getMotionAndAbilities() == this){
                return i;
            }
        }
        return -1;
    }


    protected boolean thisGameObjectSetted = false;
    protected void setThisGameObject() {
        if(!thisGameObjectSetted) {
            thisGameObject = gameObjects.get(findThisGameObjectInList());
            thisGameObjectSize = thisGameObject.getSize();
            thisGameObjectSetted = true;
        }
    }
}
