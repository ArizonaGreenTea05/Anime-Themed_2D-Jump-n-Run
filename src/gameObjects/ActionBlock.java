package gameObjects;

import core.Position;
import game.state.State;

import java.awt.*;

public class ActionBlock extends Block{

    public ActionBlock(Position position, int texture){
        super(position, texture);
        solid = true;
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
        if(!actionUsed) {
            state.setUpdatable(false);
            state.getGameObjects().add(
                    new Coin(
                            new Position(position.intX(), position.intY() - 66),
                            StaticEntity.COIN
                    )
            );
            state.setUpdatable(true);
            actionUsed = true;
        }
    }

    @Override
    public void doActionOnPositionX(State state){

    }

    @Override
    public void doActionOnSamePosition(State state){

    }
}
