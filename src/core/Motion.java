package core;
import controller.Controller;
import entity.GameObject;
import game.state.State;

import java.util.List;

public class Motion {

    private Vector2D vector;
    private final double speed;
    private boolean falling;
    private boolean sitting;
    private double gravity;
    private Controller controller;
    private List<GameObject> mapObjects;
    private double x;
    private double y;

    public Motion(double speed) {
        this.speed = speed;
        this.vector = new Vector2D(0, 0);
    }

    public void update(Controller controller, Position position, List<GameObject> mapObjects) {
        this.controller = controller;
        this.mapObjects = mapObjects;

        double deltaX = 0;
        double deltaY = 0;

        x = position.getX();
        y = position.getY();

        int ground = ScreenSize.getGround();

        //wenn Position 64p (Character-Größe = 64, deswegen 128) über Boden wird fallling true
        //wenn Position größer als Boden und nicht Up requestet wird wird falling true
        if(y < ground-128 || (!controller.isRequestingUp() && y < ground)){
            falling = true;
        }

        //wenn Position kleiner-gleich Boden wird falling false und gravity auf 0
        if(y >= ground){
            falling = false;
            gravity = 0;
        }

        if(falling) {
            deltaY += getFallSpeed(gravity);
            gravity -= 0.5;
            sitting = false;
        }

        if (controller.isRequestingUp() && !falling) {
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

            if (controller.isRequestingLeft()  && leftSpace()) {
                deltaX -= 1.5;
                sitting = false;
            }

            if (controller.isRequestingRight() && rightSpace()) {
                deltaX += 1.5;
                sitting = false;
            }
        }


        vector = new Vector2D(deltaX, deltaY);
        vector.multiply(speed);

    }

    private boolean rightSpace() {
        for (int i = 0; i < mapObjects.size(); i++) {

            if(mapObjects.get(i).isSolid()) {
                int blockPosX = mapObjects.get(i).getPosition().intX();
                int blockPosY = mapObjects.get(i).getPosition().intY();

                if(blockPosY <= y+62 && blockPosY >= y-62){
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

                if (blockPosY <= y + 62 && blockPosY >= y - 62) {
                    if (blockPosX >= x - 64 && blockPosX < x + 32) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private double getFallSpeed(double x){
        double d = -0.01 * x*x + 2.8;
        if( d >= 0 ) return d;
        return 1;
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
