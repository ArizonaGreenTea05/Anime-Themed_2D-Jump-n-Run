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

    public Player(Controller controller, MotionAndAbilities mAndA, Position position, SpriteLibrary spriteLibrary, int maxLifes, State state) {
        super(controller, mAndA, position, state);
        // loads animation of in menu chosen player
        animationManager = new AnimationManager(spriteLibrary.getUnit(Menu.getPlayerName()));
        solid = true;
        this.maxLifes = maxLifes;
        lifes = maxLifes;
        // label that shows life count is updated
        updateLifesLabelText();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics graphics) {
        // entity draws itself
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
        // has no contact action
    }

    @Override
    public void doActionOnPositionX(State state){
        // has no x position action
    }

    @Override
    public void doActionOnSamePosition(State state){
        // has no same position action
    }

    @Override
    public void testIfAlive(){
        if(lifes <= 0){
            state.getGame().getGameDisplay().showFailed();
        }
    }


// life setter methods

    @Override
    public void setLifes(int lifes){
        this.lifes = lifes;
        updateLifesLabelText();
    }

    @Override
    public void setMaxLifes(int maxLifes){
        this.maxLifes = maxLifes;
        updateLifesLabelText();
    }

    @Override
    public void addLifes(int lifes){
        if(this.lifes < maxLifes) {
            this.lifes += lifes;
        }
        updateLifesLabelText();
    }

    @Override
    public void addMaxLifes(int maxLifes){
        this.maxLifes += maxLifes;
        updateLifesLabelText();
    }

    @Override
    public void subtractLifes(int lifes){
        this.lifes -= lifes;
        updateLifesLabelText();
        testIfAlive();
    }

    @Override
    public void subtractMaxLifes(int maxLifes){
        this.maxLifes -= maxLifes;
        updateLifesLabelText();
    }

    private void updateLifesLabelText(){
        GameDisplay.setLifes(this.lifes + "/" + this.maxLifes);
    }

}
