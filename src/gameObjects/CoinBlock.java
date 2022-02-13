package gameObjects;

import core.Position;
import game.state.State;

import java.awt.*;

public class CoinBlock extends Block{

    public CoinBlock(Position position, String texture){
        super(position, texture);
        solid = true;
    }

    @Override
    public void update() {
        super.update();
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
        // block summons coin
        // can be used once
        if(!actionUsed) {
            state.setUpdatable(false);
            state.getGameObjects().add(
                    new Coin(
                            new Position(position.intX(), position.intY() - 64),
                            StaticEntity.COIN
                    )
            );
            state.setUpdatable(true);
            actionUsed = true;
        }
    }

    @Override
    public void doActionOnPositionX(State state){
        // has no x position action
    }

    @Override
    public void doActionOnSamePosition(State state){
        // has no same position action
    }
}
