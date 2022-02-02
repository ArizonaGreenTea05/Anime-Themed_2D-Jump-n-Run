package entity;

import core.Direction;
import core.Position;
import motionAndAbilities.MotionAndAbilities;

import java.awt.*;

public abstract class Block extends GameObject {

    public static final int GROUND_BLOCK = 0;
    public static final int ACTION_BLOCK = 1;
    public static final int WALL_BLOCK = 2;

    private Image sprite;
    private String texture;
    protected boolean actionUsed = false;

    public Block(Position position, int texture) {
        super(64, 64, position.intX(), position.intY());
        if(texture == GROUND_BLOCK) {
            this.texture = "ground";
        } else if(texture == ACTION_BLOCK) {
            this.texture = "action";
        } else if(texture == WALL_BLOCK) {
            this.texture = "wall";
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
