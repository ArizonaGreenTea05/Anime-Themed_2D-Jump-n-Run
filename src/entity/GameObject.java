package entity;

import core.Position;
import core.Size;
import entity.motion.MotionAndAction;
import game.state.State;

import java.awt.*;

public abstract class GameObject {
    protected Position position;
    protected Size size;
    protected boolean solid;

    public GameObject(int width, int height, int posX, int posY) {
        position = new Position(posX, posY);
        size = new Size(width, height);
    }

    public abstract void update();
    public abstract Image getSprite();

    public abstract MotionAndAction getMotion();

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isSolid(){
        return solid;
    }

    public Size getSize() {
        return size;
    }

    public abstract void doAction(State state);

}