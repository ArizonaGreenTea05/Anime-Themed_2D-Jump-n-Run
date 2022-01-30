package entity.motionAndAbilities;
import controller.Controller;
import core.Position;
import core.ScreenSize;
import core.Vector2D;
import game.state.State;

public class NPCMaA extends MotionAndAbilities {
    private boolean falling;
    private boolean sitting;
    private double gravity;
    private final int ground = ScreenSize.getGround();
    private int savePosYJump = ground;

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
            gravity -= 0.5;
            sitting = false;
        }

        if (controller.isRequestingUp() && !falling) {
            if(gravity == 0) {savePosYJump = (int) Math.round(y);}

            deltaY -= getFallSpeed(gravity);
            gravity += 0.5;
            sitting = false;
        }

        if(controller.isRequestingSprint()) {
            sprint(true);
        }

        if(!controller.isRequestingSprint()) {
            sprint(false);
        }

        if(controller.isNotRequestingHit()) {
            canHit = true;
        }

        if(isHitting()) {
            sitting = false;
            damage(state);
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
