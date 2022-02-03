package motionAndAbilities;

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
    protected int jumpHeight;

    public MotionAndAbilities(double speed) {
        this.speed = speed;
        this.normalSpeed = speed;
        this.vector = new Vector2D(0, 0);
    }

    protected double getFallSpeed(double x){
        if(x<0) {x = 0;}
        if(x>1.5) {x = 1.5;}
        return -0.1*Math.pow(x,2) + 5;
    }

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

                if (blockPosX < posX + thisGameObjectWidth + 2 && blockPosX > posX - object.getSize().getWidth() - 2) {
                    if (blockPosY < posY + thisGameObjectHeight + 2 && blockPosY > posY + thisGameObjectHeight - 5) {
                        position.setY(blockPosY - 64);
                        return true;
                    }
                }
            }
        }

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

                if (blockPosX < posX + thisGameObjectWidth && blockPosX > posX - object.getSize().getWidth()+3) {
                    if (blockPosY < posY - objectHeight + 2 && blockPosY > posY - objectHeight - 5) {
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


                if (blockPosY < posY + thisGameObjectHeight && blockPosY > posY - objectHeight) {
                    if (blockPosX > posX - objectWidth - 4 && blockPosX < posX + objectWidth/2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected void sprint(boolean sprint) {
        if(sprint && speed == normalSpeed) {
            speed++;
        } else if(!sprint){
            speed = normalSpeed;
        }
    }

    protected void damage(int damage){
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

            for (int i = 0; i < gameObjects.size() && updatable; i++) {
                GameObject gameObject = gameObjects.get(i);
                if(gameObject.getMotionAndAbilities() != this) {
                    int objectWidth = gameObject.getSize().getWidth();
                    int objectHeight = gameObject.getSize().getHeight();
                    int objectSideSpace = (64-objectWidth)/2;
                    int objectTopSpace = (64-objectHeight);
                    int objectPosX = gameObject.getPosition().intX() + objectSideSpace;
                    int objectPosY = gameObject.getPosition().intY() + objectTopSpace;



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


    public abstract Vector2D getVector();

    public abstract boolean isMoving();

    public abstract boolean isJumping();

    public abstract boolean isHitting();

    public abstract boolean isSitting();

    public abstract boolean canCauseBlockAction();

    public abstract void update(Controller controller, Position position, State state);
}
