package motionAndAbilities;
import controller.Controller;
import core.Position;
import core.ScreenSize;
import core.Vector2D;
import gameObjects.GameObject;
import game.state.State;

import java.util.List;

public class PlayerMaA extends MotionAndAbilities {

    public PlayerMaA(double speed) {
        super(speed);
        canHit = true;
        jumpHeight = 176;
    }

    @Override
    public void update(Controller controller, Position position, State state) {
        this.controller = controller;
        this.mapObjects = state.getMapObjects();
        this.gameObjects = state.getGameObjects();
        this.position = position;
        this.state = state;
        setPlayer();

        double deltaX = 0;
        double deltaY = 0;

        x = position.getX();
        y = position.getY();

        int leftBorder = ScreenSize.getLeftBorder();
        int rightBorder = ScreenSize.getRightBorder();
        int screenHeight = ScreenSize.getHeight();

        //wenn Position 64p (Character-Größe = 64, deswegen 128) über Boden wird fallling true
        //wenn Position größer als Boden und nicht Up requestet wird wird falling true
        if(y < savePosYJump-jumpHeight || (!controller.isRequestingUp() && !hasGround()) || !topSpace()){
            falling = true;
        }

        if(hasGround()){
            falling = false;
            gravity = 0;
        }

        if(y > screenHeight + 64){
            thisGameObject.subtractLifes(1);
        }

        if(falling) {
            gravity -= 0.1;
            sitting = false;
            deltaY += getFallSpeed(gravity);
        }

        if (controller.isRequestingUp() && !falling) {
            if(gravity == 0) {savePosYJump = (int) Math.round(y);}

            gravity += 0.1;
            sitting = false;
            deltaY -= getFallSpeed(gravity);
        }

        if(controller.isRequestingSprint()) {
            sprint(true);
        }

        if(!controller.isRequestingSprint()) {
            sprint(false);
        }

        if(!controller.isRequestingHit()) {
            canHit = true;
        }

        if(isHitting()) {
            sitting = false;
            damage(1);
        } else {

            if (controller.isRequestingDown()) {
                deltaY -= 1E-100;
                sitting = true;
            }
            if (controller.isRequestingLeft() && leftSpace()) {
                sitting = false;
                if(x <= leftBorder) {
                    moveMap(new Vector2D(1,0));
                } else {
                    deltaX -= 1;
                }
            }

            if (controller.isRequestingRight() && rightSpace()) {
                sitting = false;
                if(x >= rightBorder) {
                    moveMap(new Vector2D(-1,0));
                } else {
                    deltaX += 1;
                }
            }
        }

        if(controller.isRequestingESC()) {
            state.getGame().getGameDisplay().doPauseAction();
        }

        doBlockPositionXAction();
        doBlockPositionAction();


        vector = new Vector2D(deltaX, deltaY);
        vector.multiply(speed, 1);

    }

    private boolean playerSetted = false;
    private void setPlayer() {
        setThisGameObject();
        if(!playerSetted) {
            playerPosInList = findThisGameObjectInList();
            playerSetted = true;
        }
    }

    private void moveMap(Vector2D mapVector) {

        mapVector.multiply(speed, normalSpeed);

        for (GameObject mapObject : mapObjects) {
            int blockPosX = mapObject.getPosition().intX();
            int blockPosY = mapObject.getPosition().intY();

            mapObject.setPosition(new Position(blockPosX + (int) mapVector.getX(), blockPosY + (int) mapVector.getY()));
        }
        for (GameObject gameObject : gameObjects) {
            if(gameObject.getMotionAndAbilities() != this) {
                int objPosX = gameObject.getPosition().intX();
                int objPosY = gameObject.getPosition().intY();

                gameObject.setPosition(new Position(objPosX + (int) mapVector.getX(), objPosY + (int) mapVector.getY()));
            }
        }
    }

    private void doBlockPositionXAction() {
        doBlockPositionXAction(mapObjects);
        doBlockPositionXAction(gameObjects);
    }

    private void doBlockPositionXAction(List<GameObject> objects) {

        for (GameObject object : objects) {
            int blockPosX = object.getPosition().intX();
            if(x >= blockPosX && x <= blockPosX + 64) {
                object.doActionOnPositionX(state);
            }
        }
    }

    private void doBlockPositionAction() {
        doBlockPositionAction(mapObjects);
        doBlockPositionAction(gameObjects);
    }

    private void doBlockPositionAction(List<GameObject> objects) {

        boolean updatable = state.getUpdatable();
        for (int i = 0; updatable && i < objects.size(); i++) {
            updatable = state.getUpdatable();
            GameObject object = objects.get(i);
            int blockPosX = object.getPosition().intX();
            int blockPosY = object.getPosition().intY();
            if(x > blockPosX - 32 && x < blockPosX + 32) {
                if(y > blockPosY - 64 && y < blockPosY + 64) {
                    object.doActionOnSamePosition(state);
                }
            }
        }
    }


    @Override
    public Vector2D getVector() {
        return vector;
    }

    @Override
    public boolean isMoving() {
        return vector.length() > 0;
    }

    @Override
    public boolean isHitting() {
        return controller.isRequestingHit();
    }

    @Override
    public boolean isJumping() {
        return controller.isRequestingUp();
    }

    @Override
    public boolean isSitting(){
        return sitting;
    }

    @Override
    public boolean canCauseBlockAction() {
        return true;
    }
}
