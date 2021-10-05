package display;

import game.state.State;

import java.awt.*;

public class Renderer {

    public void render(State state, Graphics graphics) {
        state.getGameObjects().forEach(gameObject -> graphics.drawImage(
                gameObject.getSprite(),
                gameObject.getPosition().intX(),
                gameObject.getPosition().intY(),
                null
        ));
    }
}
