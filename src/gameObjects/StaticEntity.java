package gameObjects;

import controller.Controller;
import core.Direction;
import motionAndAbilities.MotionAndAbilities;
import core.Position;

import java.awt.*;


// child class of MovingEntity;
// parent class of Coin

public abstract class StaticEntity extends GameObject {

    public static final String COIN = "coin";

    private Image sprite;

    public StaticEntity(Position position, String texture) {
        super(64,64, position.intX(), position.intY());
        sprite = loadSprite(texture);
    }

    @Override
    public void update() {
        // no update
    }


// getter methods

    @Override
    public Image getSprite() {
        return sprite;
    }

    @Override
    public MotionAndAbilities getMotionAndAbilities() {
        return null;
    }


    @Override
    public Direction getDirection(){
        return null;
    }


// life setter methods

    @Override
    public void setLifes(int lifes){
        // is not alive
    }

    @Override
    public void setMaxLifes(int maxLifes){
        // is not alive
    }

    @Override
    public void addLifes(int lifes){
        // is not alive
    }

    @Override
    public void addMaxLifes(int maxLifes){
        // is not alive
    }

    @Override
    public void subtractLifes(int i){
        // is not alive
    }

    @Override
    public void subtractMaxLifes(int i){
        // is not alive
    }

    @Override
    public Controller getController(){
        return null;
    }

}
