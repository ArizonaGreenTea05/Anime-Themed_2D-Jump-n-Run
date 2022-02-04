package controller;

import java.awt.event.KeyEvent;

public class NPCController implements Controller {

    private boolean isRequestingUp = false;
    private boolean isRequestingDown = false;
    private boolean isRequestingLeft = false;
    private boolean isRequestingRight = false;
    private boolean isRequestingSprint = false;
    private boolean isRequestingHit = false;

    public NPCController() {
    }

    @Override
    public void setRequestingUp(boolean requestingUp) {
        this.isRequestingUp = requestingUp;
    }

    @Override
    public void setRequestingDown(boolean requestingDown) {
        this.isRequestingDown = requestingDown;
    }

    @Override
    public void setRequestingLeft(boolean requestingLeft) {
        this.isRequestingLeft = requestingLeft;
    }

    @Override
    public void setRequestingRight(boolean requestingRight) {
        this.isRequestingRight = requestingRight;
    }

    @Override
    public void setRequestingSprint(boolean requestingSprint) {
        this.isRequestingSprint = requestingSprint;
    }

    @Override
    public void setRequestingHit(boolean requestingHit) {
        this.isRequestingHit = requestingHit;
    }
    @Override
    public boolean isRequestingUp() {
        return isRequestingUp;
    }

    @Override
    public boolean isRequestingDown() {
        return isRequestingDown;
    }

    @Override
    public boolean isRequestingLeft(){
        return isRequestingLeft;
    }
    @Override
    public boolean isRequestingRight(){
        return isRequestingRight;
    }

    @Override
    public boolean isRequestingSprint(){
        return isRequestingSprint;
    }

    @Override
    public boolean isRequestingHit(){
        return isRequestingHit;
    }

    @Override
    public boolean isRequestingESC(){
        return false;
    }

    @Override
    public boolean isRequestingF3(){
        return false;
    }

}