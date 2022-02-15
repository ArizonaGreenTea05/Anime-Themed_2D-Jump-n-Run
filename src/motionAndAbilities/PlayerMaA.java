package motionAndAbilities;
import controller.Controller;
import core.Position;
import core.ScreenSize;
import core.Vector2D;
import gameObjects.GameObject;
import game.state.State;

import java.util.List;

public class PlayerMaA extends MotionAndAbilities {
    private boolean f3Pressed = false;
    private int maxJumpPos = 0;

    public PlayerMaA(double speed) {
        super(speed);
        // a player is an entity that can hit and has a maximal jump height of 176p
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
        int topBorder = ScreenSize.getTopBorder();
        int bottomBorder = ScreenSize.getBottomBorder();
        int screenHeight = ScreenSize.getHeight();
        lowestBlockPos = (int) state.getLowestBlock().getPosition().getY();



        // if position is 64p above the ground falling is set true
        // if position is above the ground and up is not requested falling is set true
        if(y < maxJumpPos || (!controller.isRequestingUp() && !hasGround()) || !topSpace()){
            falling = true;
        }

        // if player has ground falling and gravity are reset
        if(hasGround()){
            falling = false;
            gravity = 0;
        }

        // if player is below screen it dies
        if(y > screenHeight + 64){
            thisGameObject.subtractLifes(1);
        }

        if(falling) {
            // if player is falling gravity gets stronger, player can not sit
            gravity += 0.1;
            sitting = false;

            // fall speed is defined
            double fallSpeed = getFallSpeed(gravity);
            // either map or player is moved, depending on position if player
            // if lowest block is reached, map is not moved anymore
            if(y >= bottomBorder && lowestBlockPos > screenHeight) {
                moveMap(new Vector2D(0,-fallSpeed));
            } else {
                deltaY += fallSpeed;
            }
        }


        // if player wants to jump and is not falling
        if(controller.isRequestingUp() && !falling) {

            // player can not sit
            sitting = false;

            // if player has ground x position is saved (for knowing how high to jump)
            if(hasGround()) {
                savePosYJump = (int) Math.round(y);
                maxJumpPos = savePosYJump-jumpHeight;
            }

            // gravity (in this case used as inverted gravity for jump speed) gets stronger
            gravity += 0.1;

            // either map or player is moved, depending on position if player
            double jumpSpeed = getFallSpeed(gravity);
            if(y <= topBorder) {
                moveMap(new Vector2D(0,jumpSpeed));
                maxJumpPos += jumpSpeed;
            } else {
                deltaY -= jumpSpeed;
            }
        }

        // if player wants to sprint it sprints
        // if it does not it does not
        sprint(controller.isRequestingSprint(), 1.5);

        // if player is not requesting to hit the ability of hitting is set to true
        if(!controller.isRequestingHit()) {
            canHit = true;
        }

        // if player is hitting it can not sit and damages entity in range
        if(isHitting()) {
            sitting = false;
            damage(1);
        } else {
        // if it is not hitting

            // if it requests sitting it sits
            // deltaY is changed to update player sprites
            if (controller.isRequestingDown()) {
                deltaY -= 1E-100;
                sitting = true;
            }

            // if player requests left and left side is free either player or map is moved depending on position of player
            if (controller.isRequestingLeft() && leftSpace()) {
                sitting = false;
                if(x <= leftBorder) {
                    moveMap(new Vector2D(1,0));
                } else {
                    deltaX -= 1;
                }
            }

            // same as requesting left just mirrored
            if (controller.isRequestingRight() && rightSpace()) {
                sitting = false;
                if(x >= rightBorder) {
                    moveMap(new Vector2D(-1,0));
                } else {
                    deltaX += 1;
                }
            }
        }


        // if escape is pressed game is paused
        if(controller.isRequestingPause()) {
            state.getGame().getGameDisplay().doPauseAction();
        }

        // if F3 is pressed info is either shown or hidden
        if(controller.isRequestingInfo()) {
            if(!f3Pressed) {
                f3Pressed = true;
                state.getGame().getGameDisplay().doInfo();
            }
        } else {
            f3Pressed = false;
        }

        // every block at same x position as player does its 'positionXAction' (e.g. finish block)
        doBlockPositionXAction();
        // every block at same position as player does its 'positionAction' (e.g. coin)
        doBlockPositionAction();

        // vector of moving is defined
        vector = new Vector2D(deltaX, deltaY);
        vector.multiply(speed, 1);

    }

    // this game object is set and position of player (this game object9 saved
    private boolean playerSet = false;
    private void setPlayer() {
        setThisGameObject();
        if(!playerSet) {
            playerPosInList = findThisGameObjectInList();
            playerSet = true;
        }
    }

    // moving every game and map object regarding the given vector
    private void moveMap(Vector2D mapVector) {

        mapVector.multiply(speed, 1);

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

    // do block position x action of every game and map object
    private void doBlockPositionXAction() {
        doBlockPositionXAction(mapObjects);
        doBlockPositionXAction(gameObjects);
    }

    // do block position x action of every game map object of given list
    private void doBlockPositionXAction(List<GameObject> objects) {

        for (GameObject object : objects) {
            int blockPosX = object.getPosition().intX();
            if(x >= blockPosX && x <= blockPosX + 64) {
                object.doActionOnPositionX(state);
            }
        }
    }

    // do block position action of every game and map object
    private void doBlockPositionAction() {
        doBlockPositionAction(mapObjects);
        doBlockPositionAction(gameObjects);
    }

    // do block position action of every game map object of given list
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


// getter method

    @Override
    public Vector2D getVector() {
        return vector;
    }


// motion booleans

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
