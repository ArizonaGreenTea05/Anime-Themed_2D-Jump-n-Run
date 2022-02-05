package gameObjects;

import controller.Controller;
import core.Direction;
import motionAndAbilities.MotionAndAbilities;

import java.awt.*;

public abstract class CustomSizeBlock extends GameObject {

    private String texture;
    private int width, height;
    private Image sprite;

    public CustomSizeBlock(int width, int height, int posX, int posY, String texture) {
        super(width, height, posX, posY);
        this.texture = texture;
        this.width = width;
        this.height = height;
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

    @Override
    public Controller getController(){
        return null;
    }
}
