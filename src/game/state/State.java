package game.state;

import entity.GameObject;
import gfx.SpriteLibrary;
import input.Input;

import java.util.ArrayList;
import java.util.List;

public abstract class State {

    protected List<GameObject> gameObjects;
    protected List<GameObject> mapObjects;
    protected SpriteLibrary spriteLibrary;
    protected Input input;

    public State(Input input){
        this.input = input;
        gameObjects = new ArrayList<>();
        mapObjects = new ArrayList<>();
        spriteLibrary = new SpriteLibrary();
    }

    public void updateObjects() {
        gameObjects.forEach(GameObject::update);
    }

    public void updateMap() {
        mapObjects.forEach(mapObject -> mapObject.update());
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public List<GameObject> getMapObjects() {
        return mapObjects;
    }

    public SpriteLibrary getSpriteLibrary(){
        return spriteLibrary;
    }
}
