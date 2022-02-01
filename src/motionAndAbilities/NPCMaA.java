package motionAndAbilities;
import controller.Controller;
import core.Position;
import core.Vector2D;
import entity.GameObject;
import game.state.State;

public class NPCMaA extends MotionAndAbilities {
    private boolean cooldownRunning = false;
    private boolean didDamage = false;

    public NPCMaA(double speed) {
        super(speed);
        canHit = true;
    }

    @Override
    public void update(Controller controller, Position position, State state) {
        this.controller = controller;
        this.mapObjects = state.getMapObjects();
        this.gameObjects = state.getGameObjects();
        this.position = position;
        this.state = state;
        setThisGameObject();
        controlMotionAndAbilities();

        double deltaX = 0;
        double deltaY = 0;

        x = position.getX();
        y = position.getY();

        //wenn Position 64p (Character-Größe = 64, deswegen 128) über Boden wird fallling true
        //wenn Position größer als Boden und nicht Up requestet wird wird falling true
        if(y < savePosYJump-160 || (!controller.isRequestingUp() && !hasGround()) || !topSpace()){
            falling = true;
        }

        if(hasGround()){
            falling = false;
            gravity = 0;
        }

        if(falling) {
            deltaY += getFallSpeed(gravity);
            gravity -= 0.01;
            sitting = false;
        }


        if (controller.isRequestingUp() && !falling) {
            if(gravity == 0) {savePosYJump = (int) Math.round(y);}

            deltaY -= getFallSpeed(gravity);
            gravity += 0.01;
            sitting = false;
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
                deltaX -= 1;
                sitting = false;
            }

            if (controller.isRequestingRight() && rightSpace()) {
                deltaX += 1;
                sitting = false;
            }

        }


        vector = new Vector2D(deltaX, deltaY);
        vector.multiply(speed, normalSpeed);

    }

    private void controlMotionAndAbilities() {
        GameObject player = gameObjects.get(playerPosInList);
        int playerWidth = player.getSize().getWidth();
        int playerHeight = player.getSize().getHeight();
        int playerSideSpace = (64-playerWidth)/2;
        int playerTopSpace = (64-playerHeight);
        int pPosX = player.getPosition().intX() + playerSideSpace;
        int pPosY = player.getPosition().intY() + playerTopSpace;

        int thisGameObjectWidth = thisGameObjectSize.getWidth();
        int thisGameObjectHeight = thisGameObjectSize.getHeight();
        int thisGameObjectSideSpace = (64-thisGameObjectWidth)/2;
        int thisGameObjectTopSpace = (64-thisGameObjectHeight);
        int posX = (int) (x+thisGameObjectSideSpace);
        int posY = (int) (y+thisGameObjectTopSpace);

        if(thisGameObject.isShown()) {
            if (posX < pPosX) {
                controller.setRequestingLeft(false);
                controller.setRequestingRight(true);
            }
            if (posX > pPosX) {
                controller.setRequestingRight(false);
                controller.setRequestingLeft(true);
            }


            if(pPosY < posY + 32 && pPosY > posY - 32){
                if (pPosX < posX + thisGameObjectWidth + 32 && pPosX > posX-thisGameObjectWidth-32) {
                    if(!cooldownRunning) {
                        cooldownRunning = true;
                        new Thread(() -> {
                            controller.setRequestingHit(true);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            controller.setRequestingHit(false);
                            cooldownRunning = false;
                        }).start();
                    }
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
    public boolean isJumping() {
        return controller.isRequestingUp();
    }

    @Override
    public boolean isHitting() {
        return controller.isRequestingHit();
    }

    @Override
    public boolean isSitting(){
        return sitting;
    }

    @Override
    public boolean canCauseBlockAction() {
        return false;
    }
}
