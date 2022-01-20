package entity;

import core.Position;
import core.ScreenSize;
import core.Size;
import entity.motionAndAction.MotionAndAction;
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

    public abstract void render(Graphics graphics);

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

    public abstract void doActionOnContact(State state);

    public abstract void doActionOnPosition(State state);


    protected boolean shown(GameObject object) {
        int x =  object.getPosition().intX();
        int y =  object.getPosition().intY();
        int width = ScreenSize.getWidth();
        int height = ScreenSize.getHeight();

        return x >= -object.getSize().getWidth() && x < width && y >= -object.getSize().getHeight() && y <= height;
    }

}