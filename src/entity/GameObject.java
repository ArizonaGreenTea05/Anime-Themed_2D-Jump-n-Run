package entity;

import core.Position;
import core.Size;

import java.awt.*;

public abstract class GameObject {
    protected Position position;
    protected Size size;

    public GameObject(int width, int height, int posX, int posY) {
        position = new Position(posX, posY);
        size = new Size(width, height);
    }

    public abstract void update();
    public abstract Image getSprite();

    public Position getPosition() {
        return position;
    }

    public Size getSize() {
        return size;
    }

}