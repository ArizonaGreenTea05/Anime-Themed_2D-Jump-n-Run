package entity;

import controller.NPCController;
import core.Position;
import entity.motion.NPCMotion;
import game.state.State;

public class ActionBlock extends Block{

    public ActionBlock(int posX, int posY, String texture){
        super(posX, posY, texture);
        solid = true;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void doAction(State state){
        if(!actionUsed) {
            state.setUpdatable(false);
            state.getGameObjects().add(
                    new NPC(
                            new NPCController(),
                            new NPCMotion(2),
                            new Position(position.intX(), position.intY() - 66),
                            state.getSpriteLibrary(),
                            state
                    )
            );
            state.setUpdatable(true);
            actionUsed = true;
        }
    }
}
