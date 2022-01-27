package entity;

import core.Direction;
import core.Position;
import core.ScreenSize;
import core.Size;
import entity.motionAndAbilities.MotionAndAbilities;
import game.state.State;
import gfx.ImageUtils;

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

    public abstract void subtractMaxLifes(int maxLifes);

    public abstract Image getSprite();

    public abstract MotionAndAbilities getMotionAndAbilities();

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

    public abstract void doActionOnSamePosition(State state);

    public abstract void doActionOnPositionX(State state);


    protected boolean shown(GameObject object) {
        int x =  object.getPosition().intX();
        int y =  object.getPosition().intY();
        int width = ScreenSize.getWidth();
        int height = ScreenSize.getHeight();

        return x >= -object.getSize().getWidth() && x < width && y >= -object.getSize().getHeight() && y <= height;
    }

    protected Image loadSprite(String texture){
        return ImageUtils.loadImage("/game/themes/" + menu.Menu.getGameTheme() + "/blocks/" + texture + ".png");
    }

    public abstract Direction getDirection();

    public abstract void setLifes(int lifes);

    public abstract void setMaxLifes(int maxLifes);

    public abstract void addLifes(int lifes);

    public abstract void addMaxLifes(int maxLifes);

    public abstract void subtractLifes(int i);
}