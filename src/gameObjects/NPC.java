package gameObjects;

import controller.Controller;
import core.Position;
import motionAndAbilities.MotionAndAbilities;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

import java.awt.*;

public class NPC extends MovingEntity {

    public NPC(Controller controller, MotionAndAbilities mAndA, Position position, int texture, SpriteLibrary spriteLibrary, int maxLifes, State state) {
        super(controller, mAndA, position, state);
        // loads animation of npc
        animationManager = new AnimationManager(spriteLibrary.getUnit("npc_" + texture));
        solid = true;
        this.maxLifes = maxLifes;
        lifes = maxLifes;
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
            state.setUpdatable(false);
            state.getGameObjects().remove(this);
            state.setUpdatable(true);
        }
    }


// life setter methods

    @Override
    public void setLifes(int lifes){
        this.lifes = lifes;
    }

    @Override
    public void setMaxLifes(int maxLifes){
        this.maxLifes = maxLifes;
    }

    @Override
    public void addLifes(int lifes){
        this.lifes += lifes;
    }

    @Override
    public void addMaxLifes(int maxLifes){
        this.maxLifes += maxLifes;
    }

    @Override
    public void subtractLifes(int lifes){
        this.lifes -= lifes;
        testIfAlive();
    }

    @Override
    public void subtractMaxLifes(int maxLifes){
        this.maxLifes -= maxLifes;
    }
}
