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

        for (GameObject mapObject : mapObjects) {

            if (mapObject.isSolid()) {
                int blockPosX = mapObject.getPosition().intX();
                int blockPosY = mapObject.getPosition().intY();

                if (blockPosX < x + 61 && blockPosX > x - mapObject.getSize().getWidth()+3) {
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
        for (GameObject mapObject : mapObjects) {

            if (mapObject.isSolid()) {
                int blockPosX = mapObject.getPosition().intX();
                int blockPosY = mapObject.getPosition().intY();

                if (blockPosX < x + 61 && blockPosX > x - mapObject.getSize().getWidth()+3) {
                    if (blockPosY < y - 56 && blockPosY > y - 66) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected boolean rightSpace() {
        for (GameObject mapObject : mapObjects) {

            if (mapObject.isSolid()) {
                int blockPosX = mapObject.getPosition().intX();
                int blockPosY = mapObject.getPosition().intY();

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
        for (GameObject mapObject : mapObjects) {
            if (mapObject.isSolid()) {
                int blockPosX = mapObject.getPosition().intX();
                int blockPosY = mapObject.getPosition().intY();

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
