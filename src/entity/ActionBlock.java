package entity;

import controller.NPCController;
import core.Position;
import entity.motionAndAbilities.NPCMaA;
import game.state.State;

import java.awt.*;

public class ActionBlock extends Block{

    public ActionBlock(int posX, int posY, int texture){
        super(posX, posY, texture);
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
        if(!actionUsed) {
            state.setUpdatable(false);
            state.getGameObjects().add(
                    new NPC(
                            new NPCController(),
                            new NPCMaA(2),
                            new Position(position.intX(), position.intY() - 66),
                            state.getSpriteLibrary(),
                            3,
                            state
                    )
            );
            state.setUpdatable(true);
            actionUsed = true;
        }
    }

    @Override
    public void doActionOnPosition(State state){

    }
}
