package controller;

import input.Input;

import java.awt.event.KeyEvent;

public class PlayerController implements Controller {

    private Input input;
    private static int jump = KeyEvent.VK_W;
    private static int down = KeyEvent.VK_S;
    private static int left = KeyEvent.VK_A;
    private static int right = KeyEvent.VK_D;
    private static int sprint = KeyEvent.VK_SHIFT;
    private static int hit = KeyEvent.VK_J;
    private static int pause = KeyEvent.VK_ESCAPE;
    private static int info = KeyEvent.VK_F3;

    public PlayerController(Input input) {
        this.input = input;
    }

    public static Object[][] getData() {
        return new String[][]{
                {"left", KeyEvent.getKeyText(left)},
                {"right", KeyEvent.getKeyText(right)},
                {"jump", KeyEvent.getKeyText(jump)},
                {"Sit", KeyEvent.getKeyText(down)},
                {"Sprint", KeyEvent.getKeyText(sprint)},
                {"Hit", KeyEvent.getKeyText(hit)},
                {"Pause", KeyEvent.getKeyText(pause)},
                {"Info", KeyEvent.getKeyText(info)},
        };
    }

    @Override
    public void setRequestingJump(boolean requestingDown) {

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
        return input.isPressed(jump);
    }

    @Override
    public boolean isRequestingDown() {
        return input.isPressed(down);
    }

    @Override
    public boolean isRequestingLeft() {
        return input.isPressed(left);
    }

    @Override
    public boolean isRequestingRight() {
        return input.isPressed(right);
    }

    @Override
    public boolean isRequestingSprint(){
        return input.isPressed(sprint);
    }

    @Override
    public boolean isRequestingHit() {
        return input.isPressed(hit);
    }

    @Override
    public boolean isRequestingPause(){
        return input.isPressed(pause);
    }

    @Override
    public boolean isRequestingInfo(){
        return input.isPressed(info);
    }
}