package display;

import core.ScreenSize;
import entity.GameObject;
import game.state.State;

import java.awt.*;
import java.util.List;

public class Renderer {

    public void render(State state, Graphics graphics) {
        List<GameObject> gameObjects = state.getGameObjects();

        for (int i = 0; i < gameObjects.size(); i++) {
            if(shown(gameObjects.get(i))) {
                graphics.drawImage(
                        gameObjects.get(i).getSprite(),
                        gameObjects.get(i).getPosition().intX(),
                        gameObjects.get(i).getPosition().intY(),
                        null
                );
            }
        }
        /*
        state.getGameObjects().forEach(gameObject -> graphics.drawImage(
                gameObject.getSprite(),
                gameObject.getPosition().intX(),
                gameObject.getPosition().intY(),
                null
        ));
         */
    }

    private boolean shown(GameObject gameObject) {
        int x =  gameObject.getPosition().intX();
        int y =  gameObject.getPosition().intY();
        int width = ScreenSize.getWidth();
        int height = ScreenSize.getHeight();

        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
