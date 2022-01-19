package display;

import entity.GameObject;
import game.state.State;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class Renderer {

    public void render(State state, Graphics graphics) {

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
