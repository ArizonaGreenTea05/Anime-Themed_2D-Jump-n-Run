package game.state;

import entity.GameObject;
import entity.MovingEntity;
import game.Game;
import gfx.SpriteLibrary;
import input.Input;

import java.util.ArrayList;
import java.util.List;

public abstract class State {

    protected List<GameObject> gameObjects;
    protected List<GameObject> mapObjects;
    protected SpriteLibrary spriteLibrary;
    protected Input input;
    private Game game;
    private boolean updatable = true;

    public State(Input input, Game game){
        this.input = input;
        this.game = game;
        gameObjects = new ArrayList<>();
        mapObjects = new ArrayList<>();
        spriteLibrary = new SpriteLibrary();
    }

    public void updateObjects() {
        for (int i = 0; i < gameObjects.size() && updatable; i++) {
            gameObjects.get(i).update();
        }

    }

    public void updateMap() {
        mapObjects.forEach(GameObject::update);
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

    public Game getGame(){
        return game;
    }

    public void setUpdatable(boolean updatable){
        this.updatable = updatable;
    }

    public boolean getUpdatable(){
        return updatable;
    }

    public abstract MovingEntity getPlayer();
}
