package game.state;

import entity.GameObject;
import gfx.SpriteLibrary;
import input.Input;

import java.util.ArrayList;
import java.util.List;

public abstract class State {

    protected List<GameObject> gameObjects;
    protected SpriteLibrary spriteLibrary;
    protected Input input;

    public State(Input input){
        this.input = input;
        gameObjects = new ArrayList<>();
        spriteLibrary = new SpriteLibrary();
    }

    public void update() {
        gameObjects.forEach(gameObject -> gameObject.update());
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }
}
