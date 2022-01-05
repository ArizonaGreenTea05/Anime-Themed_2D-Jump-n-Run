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
    private double x;
    private double y;
    private int ground = ScreenSize.getGround();
    private int savePosYJump = ground;

    public Motion(double speed) {
        this.speed = speed;
        this.vector = new Vector2D(0, 0);
    }

    public void update(Controller controller, Position position, List<GameObject> mapObjects) {
        this.controller = controller;
        this.mapObjects = mapObjects;
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
                    moveMap(2, 0);
                    sitting = false;
                }

                if (controller.isRequestingRight() && rightSpace() && x < rightBorder) {
                    deltaX += 1.5;
                    sitting = false;
                }

                if (controller.isRequestingRight() && rightSpace() && x >= rightBorder) {
                    moveMap(-2, 0);
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

    private void moveMap(int x, int y) {
        for (int i = 0; i < mapObjects.size(); i++) {
            int blockPosX = mapObjects.get(i).getPosition().intX();
            int blockPosY = mapObjects.get(i).getPosition().intY();

            mapObjects.get(i).setPosition(new Position(blockPosX + x, blockPosY + y));
        }
    }

    private boolean hasGround() {
        if(y>=ground) {return true;}

        for (int i = 0; i < mapObjects.size(); i++) {

            if(mapObjects.get(i).isSolid()) {
                int blockPosX = mapObjects.get(i).getPosition().intX();
                int blockPosY = mapObjects.get(i).getPosition().intY();

                if(blockPosX <= x+64 && blockPosX >= x-64){
                    if(blockPosY < y+66 && blockPosY > y+60){
                        position.setY(blockPosY-64);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean rightSpace() {
        for (int i = 0; i < mapObjects.size(); i++) {

            if(mapObjects.get(i).isSolid()) {
                int blockPosX = mapObjects.get(i).getPosition().intX();
                int blockPosY = mapObjects.get(i).getPosition().intY();

                if(blockPosY < y+62 && blockPosY > y-62){
                    if(blockPosX <= x+64 && blockPosX > x-32){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean leftSpace() {
        for (int i = 0; i < mapObjects.size(); i++) {
            if(mapObjects.get(i).isSolid()) {
                int blockPosX = mapObjects.get(i).getPosition().intX();
                int blockPosY = mapObjects.get(i).getPosition().intY();

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
