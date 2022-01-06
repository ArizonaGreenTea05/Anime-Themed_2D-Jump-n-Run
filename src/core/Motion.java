package core;
import controller.Controller;
import entity.GameObject;

import java.util.List;

public class Motion {

    private Vector2D vector;
    private final double speed;
    private boolean falling;
    private boolean sitting;
    private double gravity;
    private Controller controller;
    private Position position;
    private List<GameObject> mapObjects;
    private List<GameObject> gameObjects;
    private double x;
    private double y;
    private final int ground = ScreenSize.getGround();
    private int savePosYJump = ground;

    public Motion(double speed) {
        this.speed = speed;
        this.vector = new Vector2D(0, 0);
    }

    public void update(Controller controller, Position position, List<GameObject> mapObjects, List<GameObject> gameObjects) {
        this.controller = controller;
        this.mapObjects = mapObjects;
        this.gameObjects = gameObjects;
        this.position = position;

        double deltaX = 0;
        double deltaY = 0;

        x = position.getX();
        y = position.getY();

        int leftBorder = ScreenSize.getLeftBorder();
        int rightBorder = ScreenSize.getRightBorder();

        //wenn Position 64p (Character-Größe = 64, deswegen 128) über Boden wird fallling true
        //wenn Position größer als Boden und nicht Up requestet wird wird falling true
        if(y < savePosYJump-160 || (!controller.isRequestingUp() && !hasGround())){
            falling = true;
        }

        if(hasGround()){
            falling = false;
            gravity = 0;
        }

        if(falling) {
            deltaY += getFallSpeed(gravity);
            gravity -= 0.5;
            sitting = false;
        }

        if (controller.isRequestingUp() && !falling) {
            if(gravity == 0) {savePosYJump = (int) Math.round(y);}

            deltaY -= getFallSpeed(gravity);
            gravity += 0.5;
            sitting = false;
        }

        if(isHitting()) {
            sitting = false;
        } else {

            if (controller.isRequestingDown()) {
                deltaY -= 1E-100;
                sitting = true;
            }

            if(controller.isPlayer()) {
                if (controller.isRequestingLeft() && leftSpace() && x > leftBorder) {
                    deltaX -= 1.5;
                    sitting = false;
                }

                if (controller.isRequestingLeft() && leftSpace() && x <= leftBorder) {
                    deltaX -= 1.5;
                    moveMap(new Vector2D(1.5,0));
                    sitting = false;
                }

                if (controller.isRequestingRight() && rightSpace() && x < rightBorder) {
                    deltaX += 1.5;
                    sitting = false;
                }

                if (controller.isRequestingRight() && rightSpace() && x >= rightBorder) {
                    deltaX += 1.5;
                    moveMap(new Vector2D(-1.5,0));
                    sitting = false;
                }
            } else {
                if (controller.isRequestingLeft() && leftSpace()) {
                    deltaX -= 1.5;
                    sitting = false;
                }

                if (controller.isRequestingRight() && rightSpace()) {
                    deltaX += 1.5;
                    sitting = false;
                }
            }
        }


        vector = new Vector2D(deltaX, deltaY);
        vector.multiply(speed);

    }

    private void moveMap(Vector2D mapVector) {

        mapVector.multiply(speed);

        for (GameObject mapObject : mapObjects) {
            int blockPosX = mapObject.getPosition().intX();
            int blockPosY = mapObject.getPosition().intY();

            mapObject.setPosition(new Position(blockPosX + (int) mapVector.getX(), blockPosY + (int) mapVector.getY()));
        }
        for (GameObject gameObject : gameObjects) {
            int objPosX = gameObject.getPosition().intX();
            int objPosY = gameObject.getPosition().intY();

            gameObject.setPosition(new Position(objPosX + (int) mapVector.getX(), objPosY + (int) mapVector.getY()));
        }
    }

    private boolean hasGround() {
        if(y>=ground) {return true;}

        for (GameObject mapObject : mapObjects) {

            if (mapObject.isSolid()) {
                int blockPosX = mapObject.getPosition().intX();
                int blockPosY = mapObject.getPosition().intY();

                if (blockPosX <= x + 64 && blockPosX >= x - 64) {
                    if (blockPosY < y + 66 && blockPosY > y + 60) {
                        position.setY(blockPosY - 64);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean rightSpace() {
        for (GameObject mapObject : mapObjects) {

            if (mapObject.isSolid()) {
                int blockPosX = mapObject.getPosition().intX();
                int blockPosY = mapObject.getPosition().intY();

                if (blockPosY < y + 62 && blockPosY > y - 62) {
                    if (blockPosX <= x + 64 && blockPosX > x - 32) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean leftSpace() {
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

    private double getFallSpeed(double x){
        double d = -0.01 * x*x + 2.9;
        if( d >= 0 ) return d;
        return 5;
    }


    public Vector2D getVector() {
        return vector;
    }

    public boolean isMoving() {
        return vector.length() > 0;
    }

    public boolean isHitting() {
        return controller.isRequestingHit();
    }

    public boolean isSitting(){
        return sitting;
    }
}
