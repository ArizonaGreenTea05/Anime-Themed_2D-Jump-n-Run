package entity;

import game.state.State;

import java.awt.*;

public class Ground extends CustomSizeBlock{

    public Ground(int width, int height, int posX, int posY, String texture){
        super(width, height, posX,  posY, texture);
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
    public void doAction(State state) {

    }
}
