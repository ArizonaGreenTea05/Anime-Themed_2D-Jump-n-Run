package gameObjects;

import controller.Controller;
import core.Position;
import display.GameDisplay;
import motionAndAbilities.MotionAndAbilities;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import menu.Menu;

import java.awt.*;

public class Player extends MovingEntity {

    private static Controller controller;

    public Player(Controller controller, MotionAndAbilities mAndA, Position position, SpriteLibrary spriteLibrary, int maxLifes, State state) {
        super(controller, mAndA, position, state);
        animationManager = new AnimationManager(spriteLibrary.getUnit(Menu.getPlayerName()));
        solid = true;
        this.maxLifes = maxLifes;
        lifes = maxLifes;
        this.controller = controller;
        setLifesLabelText();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics graphics) {
        if (isShown()) {
            graphics.drawImage(
                    this.getSprite(),
                    this.getPosition().intX(),
                    this.getPosition().intY(),
                    null
            );
        }
    }

    @Override
    public void doActionOnContact(State state) {

    }

    @Override
    public void doActionOnPositionX(State state){

    }

    @Override
    public void setLifes(int lifes){
        this.lifes = lifes;
        setLifesLabelText();
    }

    @Override
    public void setMaxLifes(int maxLifes){
        this.maxLifes = maxLifes;
        setLifesLabelText();
    }

    @Override
    public void addLifes(int lifes){
        this.lifes += lifes;
        setLifesLabelText();
    }

    @Override
    public void addMaxLifes(int maxLifes){
        this.maxLifes += maxLifes;
        setLifesLabelText();
    }

    @Override
    public void subtractLifes(int lifes){
        this.lifes -= lifes;
        setLifesLabelText();
        testIfAlive();
    }

    @Override
    public void subtractMaxLifes(int maxLifes){
        this.maxLifes -= maxLifes;
        setLifesLabelText();
    }

    private void setLifesLabelText(){
        GameDisplay.setLifes(this.lifes + "/" + this.maxLifes);
    }

    @Override
    public void doActionOnSamePosition(State state){

    }

    @Override
    public void testIfAlive(){
        if(lifes <= 0){
            state.getGame().getGameDisplay().showFailed();
        }
    }

}
