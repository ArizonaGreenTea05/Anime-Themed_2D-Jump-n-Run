package entity;

import core.Direction;
import motionAndAbilities.MotionAndAbilities;
import core.Position;

import java.awt.*;

public abstract class StaticEntity extends GameObject {

    public static final int COIN = 0;

    private String texture;
    private Image sprite;

    public StaticEntity(Position position, int texture) {
        super(64,64, position.intX(), position.intY());
        if(texture == COIN) {
            this.texture = "coin";
        }
        sprite = loadSprite(this.texture);
    }

    @Override
    public void update() {

    }

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

    @Override
    public void setLifes(int lifes){

    }

    @Override
    public void setMaxLifes(int maxLifes){

    }

    @Override
    public void addLifes(int lifes){

    }

    @Override
    public void addMaxLifes(int maxLifes){

    }

    @Override
    public void subtractLifes(int i){

    }

    @Override
    public void subtractMaxLifes(int i){

    }
}
