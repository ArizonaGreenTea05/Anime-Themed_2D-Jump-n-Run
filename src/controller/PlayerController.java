package controller;

import input.Input;

import java.awt.event.KeyEvent;

public class PlayerController implements Controller {

    private Input input;

    public PlayerController(Input input) {
        this.input = input;
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
    public boolean isRequestingHit() {
        return input.isPressed(KeyEvent.VK_J);
    }



}