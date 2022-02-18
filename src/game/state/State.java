package game.state;

import gameObjects.GameObject;
import gameObjects.MovingEntity;
import game.Game;
import gfx.SpriteLibrary;
import input.Input;

import java.util.ArrayList;
import java.util.List;


// parent class of GameState

public abstract class State {

    protected GameObject lowestBlock;
    protected List<GameObject> gameObjects;
    protected List<GameObject> mapObjects;
    protected SpriteLibrary spriteLibrary;
    protected Input input;
    private static Game game;
    private boolean updatable = true;

    public State(Input input, Game game){
        this.input = input;
        this.game = game;
        gameObjects = new ArrayList<>();
        mapObjects = new ArrayList<>();
        spriteLibrary = new SpriteLibrary();
    }


// update methods

    public void updateObjects() {
        for (int i = 0; i < gameObjects.size() && updatable; i++) {
            gameObjects.get(i).update();
        }
    }

    public void updateMap() {
        mapObjects.forEach(GameObject::update);
    }


// setter methods

    public void setUpdatable(boolean updatable){
        this.updatable = updatable;
    }


// getter methods

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

    public boolean getUpdatable(){
        return updatable;
    }

    public abstract MovingEntity getPlayer();

    public GameObject getLowestBlock(){
        return lowestBlock;
    }

}
