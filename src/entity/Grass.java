package entity;

import core.Position;
import game.state.State;

import java.awt.*;

public class Grass extends Block{

    public Grass(Position position, int texture){
        super(position, texture);
        solid = true;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Graphics graphics) {
        if (shown(this)) {
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

    }
}
