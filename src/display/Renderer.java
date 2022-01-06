package display;

import core.ScreenSize;
import entity.GameObject;
import game.state.State;

import java.awt.*;
import java.util.List;

public class Renderer {

    public void render(State state, Graphics graphics) {

        List<GameObject> gameObjects = state.getGameObjects();
        List<GameObject> mapObjects = state.getMapObjects();

        for (GameObject mapObject : mapObjects) {
            if (shown(mapObject)) {
                graphics.drawImage(
                        mapObject.getSprite(),
                        mapObject.getPosition().intX(),
                        mapObject.getPosition().intY(),
                        null
                );

            }
        }



        for (GameObject gameObject : gameObjects) {
            if (shown(gameObject)) {
                graphics.drawImage(
                        gameObject.getSprite(),
                        gameObject.getPosition().intX(),
                        gameObject.getPosition().intY(),
                        null
                );
            }
        }
    }


    private boolean shown(GameObject object) {
        int x =  object.getPosition().intX();
        int y =  object.getPosition().intY();
        int width = ScreenSize.getWidth();
        int height = ScreenSize.getHeight();

        return x >= -object.getSize().getWidth() && x < width && y >= -object.getSize().getHeight() && y <= height;
    }
}
