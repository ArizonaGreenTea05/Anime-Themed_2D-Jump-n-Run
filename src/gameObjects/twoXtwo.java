package gameObjects;

import core.Position;
import game.state.State;

import java.awt.*;

public class twoXtwo extends CustomBlock{
    public twoXtwo(Position position, String texture) {
        super(128, 128, position, texture);
        solid = true;
    }

    @Override
    public void render(Graphics graphics) {
        // block draws itself
        if (isShown()) {
            graphics.drawImage(
                    this.getSprite(),
                    this.getPosition().intX(),
                    this.getPosition().intY(),
                    null
            );
        }
    }

    @Override
    public void doActionOnContact(State state){
        // has no contact action
    }

    @Override
    public void doActionOnPositionX(State state){
        // has no x position action
    }

    @Override
    public void doActionOnSamePosition(State state){
        // has no action on same position
    }
}
