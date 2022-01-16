package entity;

import core.Position;
import core.Size;
import entity.motion.Motion;
import game.state.State;

import java.awt.*;

public abstract class GameObject {
    protected Position position;
    protected Size size;
    protected boolean solid;
    protected boolean blockActionCauseAble;

    public GameObject(int width, int height, int posX, int posY) {
        position = new Position(posX, posY);
        size = new Size(width, height);
    }

    public abstract void update();
    public abstract Image getSprite();

    public abstract Motion getMotion();

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isSolid(){
        return solid;
    }

    public boolean canCauseBlockAction(){
        return blockActionCauseAble;
    }

    public Size getSize() {
        return size;
    }

    public abstract void doAction(State state);

}