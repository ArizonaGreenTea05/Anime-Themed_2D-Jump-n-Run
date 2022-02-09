package motionAndAbilities;

import controller.Controller;
import core.Position;
import core.ScreenSize;
import core.Size;
import core.Vector2D;
import gameObjects.GameObject;
import game.state.State;

import java.util.List;

import static core.Direction.*;

public abstract class MotionAndAbilities {

    protected State state;
    protected final int ground = ScreenSize.getGround();

    protected Vector2D vector;

    protected double speed;
    protected final double normalSpeed;

    protected Position position;
    protected double x;
    protected double y;

    protected List<GameObject> mapObjects;
    protected List<GameObject> gameObjects;;
    protected static GameObject player;
    protected GameObject thisGameObject;
    protected Size thisGameObjectSize;
    static int playerPosInList;

    protected Controller controller;
    protected boolean falling;
    protected double gravity;
    protected boolean sitting;
    protected boolean canHit;
    protected int savePosYJump = ground;
    protected int jumpHeight;


    public MotionAndAbilities(double speed) {
        // normalSpeed saves the 'normal' speed, so speed can be reset after sprinting
        this.speed = speed;
        this.normalSpeed = speed;
        // new Vector -> defines in which direction entity is moving
        this.vector = new Vector2D(0, 0);
    }

    // parabola for exponential growing of fall speed
    protected double getFallSpeed(double x){
        if(x<0) {x = 0;}
        if(x>1.5) {x = 1.5;}
        return -0.1*Math.pow(x,2) + 5;
    }


    // test if a specific position (relative to this game object) has ground
    protected boolean hasGround(int xOffset) {

        int thisGameObjectWidth = thisGameObjectSize.getWidth();
        int thisGameObjectHeight = thisGameObjectSize.getHeight();
        int thisGameObjectSideSpace = (64-thisGameObjectWidth)/2;
        int thisGameObjectTopSpace = (64-thisGameObjectHeight);
        int posX = (int) (x+thisGameObjectSideSpace + xOffset);
        int posY = (int) (y+thisGameObjectTopSpace);

        if(hasGround(mapObjects, posX, posY)){
            return true;
        } else {
            return hasGround(gameObjects, posX, posY);
        }
    }
    // test if this game object has ground
    protected boolean hasGround() {

        int thisGameObjectWidth = thisGameObjectSize.getWidth();
        int thisGameObjectHeight = thisGameObjectSize.getHeight();
        int thisGameObjectSideSpace = (64-thisGameObjectWidth)/2;
        int thisGameObjectTopSpace = (64-thisGameObjectHeight);
        int posX = (int) (x+thisGameObjectSideSpace);
        int posY = (int) (y+thisGameObjectTopSpace);

        if(hasGround(mapObjects, posX, posY)){
            return true;
        } else {
            return hasGround(gameObjects, posX, posY);
        }
    }

    // tests if this game object has ground at specific position (only used by the other two 'hasGrounds')
    private boolean hasGround(List<GameObject> objects, int posX, int posY){

        int thisGameObjectWidth = thisGameObjectSize.getWidth();
        int thisGameObjectHeight = thisGameObjectSize.getHeight();

        for (GameObject object : objects) {

            if (object.getMotionAndAbilities() != this && object.isSolid()) {
                int objectWidth = object.getSize().getWidth();
                int objectHeight = object.getSize().getHeight();
                int objectSideSpace = (64-objectWidth)/2;
                int objectTopSpace = (64-objectHeight);
                int blockPosX = object.getPosition().intX() + objectSideSpace;
                int blockPosY = object.getPosition().intY() + objectTopSpace;

                // defining of left and right 'border'
                // if left border of block < right border of player AND right border of block > left border of player
                // -> x-position fits
                if (blockPosX < posX + thisGameObjectWidth + 2 && blockPosX + object.getSize().getWidth() + 2 > posX) {

                    // defining of top and bottom 'border'
                    // if top border of block < bottom border of player + 5 AND top border of block > bottom border of player - 2 (huge tolerance because of fall speed)
                    // -> y-position fits
                    if (blockPosY < posY + thisGameObjectHeight + 5 && blockPosY > posY + thisGameObjectHeight - 2) {
                        // this game object set directly onto the block when in tolerance limit-> no bugging into block
                        position.setY(blockPosY - 64);
                        // has ground
                        return true;
                    }
                }
            }
        }
        // else:
        return false;
    }


    protected boolean topSpace() {
        if(!topSpace(mapObjects)){
            return false;
        } else {
            return topSpace(gameObjects);
        }
    }

    private boolean topSpace(List<GameObject> objects){
        int thisGameObjectWidth = thisGameObjectSize.getWidth();
        int thisGameObjectHeight = thisGameObjectSize.getHeight();
        int thisGameObjectSideSpace = (64-thisGameObjectWidth)/2;
        int thisGameObjectTopSpace = (64-thisGameObjectHeight);
        int posX = (int) (x+thisGameObjectSideSpace);
        int posY = (int) (y+thisGameObjectTopSpace);


        for (GameObject object : objects) {

            if (object.getMotionAndAbilities() != this && object.isSolid()) {
                int objectWidth = object.getSize().getWidth();
                int objectHeight = object.getSize().getHeight();
                int objectSideSpace = (64-objectWidth)/2;
                int objectTopSpace = (64-objectHeight);
                int blockPosX = object.getPosition().intX() + objectSideSpace;
                int blockPosY = object.getPosition().intY() + objectTopSpace;

                // same as hasGround() just with top
                if (blockPosX < posX + thisGameObjectWidth && blockPosX > posX - object.getSize().getWidth()+3) {
                    if (blockPosY < posY - objectHeight + 2 && blockPosY > posY - objectHeight - 5) {
                        // if the action is not caused this object will cause it
                        if(actionCaused()) {
                            object.doActionOnContact(state);
                        }
                        // does not have top space
                        return false;
                    }
                }
            }
        }
        // else:
        return true;
    }

    private boolean actionCaused() {
        return this.canCauseBlockAction() && this.isJumping();
    }

    protected boolean rightSpace() {
        if(!rightSpace(mapObjects)){
            return false;
        } else {
            return rightSpace(gameObjects);
        }
    }

    private boolean rightSpace(List<GameObject> objects){
        int thisGameObjectWidth = thisGameObjectSize.getWidth();
        int thisGameObjectHeight = thisGameObjectSize.getHeight();
        int thisGameObjectSideSpace = (64-thisGameObjectWidth)/2;
        int thisGameObjectTopSpace = (64-thisGameObjectHeight);
        int posX = (int) (x+thisGameObjectSideSpace);
        int posY = (int) (y+thisGameObjectTopSpace);


        for (GameObject object : objects) {

            if (object.getMotionAndAbilities() != this && object.isSolid()) {
                int objectWidth = object.getSize().getWidth();
                int objectHeight = object.getSize().getHeight();
                int objectSideSpace = (64-objectWidth)/2;
                int objectTopSpace = (64-objectHeight);
                int blockPosX = object.getPosition().intX() + objectSideSpace;
                int blockPosY = object.getPosition().intY() + objectTopSpace;

                // same as hasGround and topSpace, just turned to the right
                if (blockPosY < posY + thisGameObjectHeight-2 && blockPosY > posY - objectHeight+2) {
                    if (blockPosX < posX + thisGameObjectWidth+7 && blockPosX > posX - thisGameObjectWidth/2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected boolean leftSpace() {
        if(!leftSpace(mapObjects)){
            return false;
        } else {
            return leftSpace(gameObjects);
        }
    }

    private boolean leftSpace(List<GameObject> objects){
        int thisGameObjectWidth = thisGameObjectSize.getWidth();
        int thisGameObjectHeight = thisGameObjectSize.getHeight();
        int thisGameObjectSideSpace = (64-thisGameObjectWidth)/2;
        int thisGameObjectTopSpace = (64-thisGameObjectHeight);
        int posX = (int) (x+thisGameObjectSideSpace);
        int posY = (int) (y+thisGameObjectTopSpace);

        for (GameObject object : objects) {
            if (object.getMotionAndAbilities() != this && object.isSolid()) {
                int objectWidth = object.getSize().getWidth();
                int objectHeight = object.getSize().getHeight();
                int objectSideSpace = (64-objectWidth)/2;
                int objectTopSpace = (64-objectHeight);
                int blockPosX = object.getPosition().intX() + objectSideSpace;
                int blockPosY = object.getPosition().intY() + objectTopSpace;

                // same as right space, just inverted
                if (blockPosY < posY + thisGameObjectHeight-2 && blockPosY > posY - objectHeight+2) {
                    if (blockPosX > posX - objectWidth - 6 && blockPosX < posX + objectWidth/2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // sprinting
    protected void sprint(boolean sprint, double sprintSpeed) {
        if(sprint && speed == normalSpeed) {
            // if entity requests sprinting and it is not sprinting at the moment the sprint speed is added to speed
            speed += sprintSpeed;
        } else if(!sprint){
            // if entity is not requesting to sprint the speed is reset to the normal speed
            speed = normalSpeed;
        }
    }

    // hitting
    protected void damage(int damage){
        // the entity hits once per request -> pressing button 5 times = 5 hits
        if(canHit) {
            canHit = false;

            boolean updatable = state.getUpdatable();

            GameObject thisGameObject = gameObjects.get(findThisGameObjectInList());

            int thisGameObjectWidth = thisGameObjectSize.getWidth();
            int thisGameObjectHeight = thisGameObjectSize.getHeight();
            int thisGameObjectSideSpace = (64-thisGameObjectWidth)/2;
            int thisGameObjectTopSpace = (64-thisGameObjectHeight);
            int posX = (int) (x+thisGameObjectSideSpace);
            int posY = (int) (y+thisGameObjectTopSpace);

            // loop only runs if updatable == true, because it comes to error when entity is killed (list gets shorter -> length does not fit anymore)
            for (int i = 0; i < gameObjects.size() && updatable; i++) {
                GameObject gameObject = gameObjects.get(i);
                if(gameObject.getMotionAndAbilities() != this) {
                    int objectWidth = gameObject.getSize().getWidth();
                    int objectHeight = gameObject.getSize().getHeight();
                    int objectSideSpace = (64-objectWidth)/2;
                    int objectTopSpace = (64-objectHeight);
                    int objectPosX = gameObject.getPosition().intX() + objectSideSpace;
                    int objectPosY = gameObject.getPosition().intY() + objectTopSpace;

                    // if other entity is within a radius of
                        // y: half a block higher to half a block lower,
                        // x: half a block into the direction of looking
                    // it can be hit
                    if (objectPosY < posY + 32 && objectPosY > posY - 32) {
                        if (thisGameObject.getDirection() == R && objectPosX < posX + thisGameObjectWidth + 32 && objectPosX > posX) {
                            gameObject.subtractLifes(damage);
                        } else if (thisGameObject.getDirection() == L && objectPosX < posX && objectPosX > posX - objectWidth - 32) {
                            gameObject.subtractLifes(damage);
                        }
                    }
                }
                updatable = state.getUpdatable();
            }
        }
    }

    // finds the game object this motion belongs to
    protected int findThisGameObjectInList(){
        for (int i = 0; i < gameObjects.size(); i++) {
            if(gameObjects.get(i).getMotionAndAbilities() == this){
                return i;
            }
        }
        return -1;
    }

    // sets variable 'thisGameObject' only once (by first calling the method)
    protected boolean thisGameObjectSetted = false;
    protected void setThisGameObject() {
        if(!thisGameObjectSetted) {
            thisGameObject = gameObjects.get(findThisGameObjectInList());
            thisGameObjectSize = thisGameObject.getSize();
            thisGameObjectSetted = true;
        }
    }


// getter methods
    public abstract Vector2D getVector();

    public static GameObject getPlayer(){
        return player;
    }


// motion booleans

    public abstract boolean isMoving();

    public abstract boolean isJumping();

    public abstract boolean isHitting();

    public abstract boolean isSitting();

    public abstract boolean canCauseBlockAction();


// update

    public abstract void update(Controller controller, Position position, State state);
}
