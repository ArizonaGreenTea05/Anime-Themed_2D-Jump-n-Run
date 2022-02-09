package gameObjects;

import controller.Controller;
import core.Direction;
import core.Position;
import core.ScreenSize;
import core.Size;
import motionAndAbilities.MotionAndAbilities;
import game.state.State;
import utils.FileLoader;

import java.awt.*;

public abstract class GameObject {
    protected Position position;
    protected Size size;
    protected boolean solid;
    protected int width, height;

    public GameObject(int width, int height, int posX, int posY) {
        this.position = new Position(posX, posY);
        this.size = new Size(width, height);
        this.width = width;
        this.height = height;
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


    public boolean isShown() {
        int x =  this.getPosition().intX();
        int y =  this.getPosition().intY();
        int width = ScreenSize.getWidth();
        int height = ScreenSize.getHeight();

        return x >= -this.getSize().getWidth() && x < width && y >= -this.getSize().getHeight() && y <= height;
    }

    protected Image loadSprite(String texture){
        return FileLoader.loadImage(texture,"/game/themes/" + menu.Menu.getGameTheme() + "/blocks/");
    }

    public abstract Direction getDirection();

    public abstract void setLifes(int lifes);

    public abstract void setMaxLifes(int maxLifes);

    public abstract void addLifes(int lifes);

    public abstract void addMaxLifes(int maxLifes);

    public abstract void subtractLifes(int i);

    public abstract Controller getController();
}