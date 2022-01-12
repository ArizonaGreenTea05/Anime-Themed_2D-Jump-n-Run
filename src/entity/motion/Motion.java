package entity.motion;

import controller.Controller;
import core.Position;
import core.Vector2D;
import entity.GameObject;

import java.util.List;

public abstract class Motion {

    protected Vector2D vector;
    protected final double speed;
    protected Position position;
    protected double x;
    protected double y;
    protected List<GameObject> mapObjects;
    protected List<GameObject> gameObjects;

    public Motion(double speed) {
        this.speed = speed;
        this.vector = new Vector2D(0, 0);
    }

    public abstract void update(Controller controller, Position position, List<GameObject> mapObjects, List<GameObject> gameObjects);

    protected boolean hasGround() {
        if(testHasGround(mapObjects)){
            return true;
        } else {
            return testHasGround(gameObjects);
        }
    }

    private boolean testHasGround(List<GameObject> objects){
        for (GameObject object : objects) {

            if (object.isSolid()) {
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

            if (object.isSolid()) {
                int blockPosX = object.getPosition().intX();
                int blockPosY = object.getPosition().intY();

                if (blockPosX < x + 61 && blockPosX > x - object.getSize().getWidth()+3) {
                    if (blockPosY < y - 50 && blockPosY > y - 66) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected boolean rightSpace() {
        if(testRightSpace(mapObjects)){
            return true;
        } else {
            return testRightSpace(gameObjects);
        }
    }

    private boolean testRightSpace(List<GameObject> objects){
        for (GameObject object : objects) {

            if (object.isSolid()) {
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
        if(testLeftSpace(mapObjects)){
            return true;
        } else {
            return testLeftSpace(gameObjects);
        }
    }

    private boolean testLeftSpace(List<GameObject> objects){
        for (GameObject object : objects) {
            if (object.isSolid()) {
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

    public abstract boolean isHitting();

    public abstract boolean isSitting();

}
