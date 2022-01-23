package controller;

import java.awt.event.KeyEvent;

public class NPCController implements Controller {

    public NPCController() {
    }

    @Override
    public boolean isRequestingUp() {
        return false;
    }

    @Override
    public boolean isRequestingDown() {
        return false;
    }

    @Override
    public boolean isRequestingLeft(){
        return false;
    }
    @Override
    public boolean isRequestingRight(){
        return true;
    }

    @Override
    public boolean isRequestingSprint(){
        return false;
    }

    @Override
    public boolean isRequestingHit(){
        return false;
    }

    @Override
    public boolean isNotRequestingHit() {
        return !isRequestingHit();
    }

    @Override
    public boolean isRequestingESC(){
        return false;
    }

}