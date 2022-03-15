package gameObjects;

import controller.Controller;
import core.Direction;
import motionAndAbilities.MotionAndAbilities;
import core.Position;

import java.awt.*;
import java.util.Arrays;


// child class of MovingEntity;
// parent class of Coin

public abstract class StaticEntity extends GameObject {

    public static final int COIN = 0;

    private Image sprite;
    private static final Image[] staticSprites = new Image[1];

    public StaticEntity(Position position, int texture) {
        super(64,64, position.intX(), position.intY());

        // loads sprite of given texture
        if(staticSprites[texture] == null) {
            staticSprites[texture] = loadSprite("se" + texture);
        }
        sprite =  staticSprites[texture];
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

    @Override
    public void resetSprites(){
        Arrays.fill(staticSprites, null);
        sprite = null;
    }

}
