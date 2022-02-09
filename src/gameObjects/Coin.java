package gameObjects;

import core.Position;
import game.state.State;
import motionAndAbilities.MotionAndAbilities;

import java.awt.*;

public class Coin extends StaticEntity{

    public Coin(Position position, String texture){
        super(position, texture);
        solid = false;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics graphics) {
        // coin draws itself
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
        // updating paused -> can be removed without destroying the update loop
        state.setUpdatable(false);
        state.getGameObjects().remove(this);
        state.setUpdatable(true);

        // restores 5 score points (5seconds)
        state.getGame().getGameLoop().addScore(5);
        // restores 1 life
        MotionAndAbilities.getPlayer().addLifes(1);
    }
}
