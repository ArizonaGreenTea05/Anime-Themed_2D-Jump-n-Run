package display;

import gameObjects.GameObject;
import game.state.State;

import java.awt.*;
import java.util.List;

public class Renderer {

    public void render(State state, Graphics graphics) {

        // renders every game- and map-object

        List<GameObject> gameObjects = state.getGameObjects();
        List<GameObject> mapObjects = state.getMapObjects();

        for (GameObject mapObject : mapObjects) {
            mapObject.render(graphics);
        }

        for (GameObject gameObject : gameObjects) {
            gameObject.render(graphics);
        }
    }
}
