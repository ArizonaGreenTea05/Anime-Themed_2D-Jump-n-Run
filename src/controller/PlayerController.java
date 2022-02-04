package controller;

import input.Input;

import java.awt.event.KeyEvent;

public class PlayerController implements Controller {

    private Input input;

    public PlayerController(Input input) {
        this.input = input;
    }

    @Override
    public void setRequestingUp(boolean requestingDown) {

    }

    @Override
    public void setRequestingDown(boolean requestingDown) {

    }

    @Override
    public void setRequestingLeft(boolean requestingLeft) {

    }

    @Override
    public void setRequestingRight(boolean requestingRight) {

    }

    @Override
    public void setRequestingSprint(boolean requestingSprint) {

    }

    @Override
    public void setRequestingHit(boolean requestingHit) {

    }

    @Override
    public boolean isRequestingUp() {
        return input.isPressed(KeyEvent.VK_UP) || input.isPressed(KeyEvent.VK_W) || input.isPressed(KeyEvent.VK_SPACE);
    }

    @Override
    public boolean isRequestingDown() {
        return input.isPressed(KeyEvent.VK_DOWN) || input.isPressed(KeyEvent.VK_S);
    }

    @Override
    public boolean isRequestingLeft() {
        return input.isPressed(KeyEvent.VK_LEFT) || input.isPressed(KeyEvent.VK_A);
    }

    @Override
    public boolean isRequestingRight() {
        return input.isPressed(KeyEvent.VK_RIGHT) || input.isPressed(KeyEvent.VK_D);
    }

    @Override
    public boolean isRequestingSprint(){
        return input.isPressed(KeyEvent.VK_CAPS_LOCK) || input.isPressed(KeyEvent.VK_CONTROL);
    }

    @Override
    public boolean isRequestingHit() {
        return input.isPressed(KeyEvent.VK_J);
    }

    @Override
    public boolean isRequestingESC(){
        return input.isPressed(KeyEvent.VK_ESCAPE);
    }

    @Override
    public boolean isRequestingF3(){
        return input.isPressed(KeyEvent.VK_F3);
    }
}