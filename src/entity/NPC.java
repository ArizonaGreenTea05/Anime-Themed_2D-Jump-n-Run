package entity;

import controller.Controller;
import core.Position;
import entity.motionAndAbilities.MotionAndAbilities;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;

import java.awt.*;

public class NPC extends MovingEntity {

    public NPC(Controller controller, MotionAndAbilities mAndA, Position position, SpriteLibrary spriteLibrary, int maxLifes, State state) {
        super(controller, mAndA, position, state);
        animationManager = new AnimationManager(spriteLibrary.getUnit("npc_1"));
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
        if (shown(this)) {
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
        System.out.println(this.lifes);
        testIfAlive();
    }

    @Override
    public void subtractMaxLifes(int maxLifes){
        this.maxLifes -= maxLifes;
    }

    @Override
    public void doActionOnSamePosition(State state){

    }
}
