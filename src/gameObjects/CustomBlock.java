package gameObjects;

import controller.Controller;
import core.Direction;
import core.Position;
import motionAndAbilities.MotionAndAbilities;

import java.awt.*;
import java.util.Arrays;

public abstract class CustomBlock extends GameObject {

    private Image sprite;
    private static final Image[] staticSprites = new Image[3];

    public CustomBlock(int width, int height, Position position, int texture) {
        super(width, height, position.intX(), position.intY());
        this.width = width;
        this.height = height;


        // loads sprite of given texture
        if(staticSprites[texture] == null) {
            staticSprites[texture] = loadSprite("c" + texture);
        }
        sprite =  staticSprites[texture];
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

    @Override
    public void resetSprites(){
        Arrays.fill(staticSprites, null);
        sprite = null;
    }

}
