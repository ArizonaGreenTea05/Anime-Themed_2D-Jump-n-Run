package gameObjects;

import core.Position;
import game.state.State;

import java.awt.*;

public class NormalBlock extends Block{

    public NormalBlock(Position position, int texture, boolean solid){
        super(position, texture);
        this.solid = solid;
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
    public void doActionOnContact(State state) {
        // has no contact action
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
