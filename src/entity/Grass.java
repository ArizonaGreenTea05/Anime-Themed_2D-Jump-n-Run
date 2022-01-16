package entity;

import game.state.State;

public class Grass extends Block{

    public Grass(int posX, int posY, String texture){
        super(posX, posY, texture);
        solid = true;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void doAction(State state){

    }
}
