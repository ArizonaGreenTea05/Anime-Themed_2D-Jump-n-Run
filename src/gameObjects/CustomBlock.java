package gameObjects;

import controller.Controller;
import core.Direction;
import core.Position;
import motionAndAbilities.MotionAndAbilities;

import java.awt.*;

public abstract class CustomBlock extends GameObject {

    private Image sprite;
    public static final String C1 = "c1";
    public static final String C2 = "c2";
    public static final String C3 = "c3";

    public CustomBlock(int width, int height, Position position, String texture) {
        super(width, height, position.intX(), position.intY());
        this.width = width;
        this.height = height;
        sprite = loadSprite(texture);
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

    @Override
    public Controller getController(){
        return null;
    }
}
