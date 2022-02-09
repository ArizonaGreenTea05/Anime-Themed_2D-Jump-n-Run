package gameObjects;

import core.Position;
import game.state.State;

import java.awt.*;

public class FinishBlock extends Block{

    public FinishBlock(Position position, String texture){
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
        // has no contact action
    }

    @Override
    public void doActionOnPositionX(State state){
        // if player has reached this x coordinate it won the game
        state.getGame().getGameDisplay().showWon();
    }

    @Override
    public void doActionOnSamePosition(State state){
        // has no same position action
    }
}