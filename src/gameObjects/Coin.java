package gameObjects;

import core.Position;
import game.state.State;

import java.awt.*;

public class Coin extends StaticEntity{

    public Coin(Position position, int texture){
        super(position, texture);
        solid = false;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics graphics) {
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

    }

    @Override
    public void doActionOnPositionX(State state){

    }

    @Override
    public void doActionOnSamePosition(State state){
        state.setUpdatable(false);
        state.getGameObjects().remove(this);
        state.setUpdatable(true);
        state.getGame().getGameLoop().addScore(5);
    }
}
