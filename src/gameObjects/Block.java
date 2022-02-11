package gameObjects;

import controller.Controller;
import core.Direction;
import core.Position;
import motionAndAbilities.MotionAndAbilities;

import java.awt.*;


// child class of MovingEntity;
// parent class of NormalBlock, FinishBlock & ActionBlock

public abstract class Block extends GameObject {

    public static final String GROUND_BLOCK = "ground";
    public static final String TRANS_GROUND_BLOCK = "trans_ground";
    public static final String WALL_BLOCK = "wall";
    public static final String TRANS_WALL_BLOCK = "trans_wall";
    public static final String BRICKS = "brick";
    public static final String TRANS_BRICKS = "trans_brick";
    public static final String PLANKS = "planks";
    public static final String TRANS_PLANKS = "trans_planks";
    public static final String ACTION_BLOCK = "action";

    private Image sprite;
    protected boolean actionUsed = false;

    public Block(Position position, String texture) {
        super(64, 64, position.intX(), position.intY());
        // loads sprite of given texture
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
