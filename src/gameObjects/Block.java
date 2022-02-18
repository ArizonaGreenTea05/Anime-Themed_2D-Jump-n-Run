package gameObjects;

import controller.Controller;
import core.Direction;
import core.Position;
import motionAndAbilities.MotionAndAbilities;

import java.awt.*;
import java.util.Arrays;


// child class of MovingEntity;
// parent class of NormalBlock, FinishBlock & ActionBlock

public abstract class Block extends GameObject {

    public static final int GROUND_BLOCK = 0;
    public static final int TRANS_GROUND_BLOCK = 1;
    public static final int WALL_BLOCK = 2;
    public static final int TRANS_WALL_BLOCK = 3;
    public static final int BRICKS = 4;
    public static final int TRANS_BRICKS = 5;
    public static final int PLANKS = 6;
    public static final int TRANS_PLANKS = 7;
    public static final int ACTION_BLOCK = 8;
    public static final int TREE = 9;
    public static final int LEAVES = 10;

    private Image sprite;
    private static Image[] staticSprites = new Image[11];
    protected boolean actionUsed = false;

    public Block(Position position, int texture) {
        super(64, 64, position.intX(), position.intY());

        // loads sprite of given texture
        if(staticSprites[texture] == null) {
            staticSprites[texture] = loadSprite(String.valueOf(texture));
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
