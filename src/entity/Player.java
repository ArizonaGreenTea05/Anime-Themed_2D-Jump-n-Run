package entity;

import controller.Controller;
import core.Position;
import entity.motion.Motion;
import game.state.State;
import gfx.AnimationManager;
import gfx.SpriteLibrary;
import menu.Menu;

import java.util.List;

public class Player extends MovingEntity {

    private static Controller controller;

    public Player(Controller controller, Motion motion, Position position, SpriteLibrary spriteLibrary, State state) {
        super(controller, motion, position, state);
        animationManager = new AnimationManager(spriteLibrary.getUnit(Menu.getPlayerName()));
        solid = true;
        this.controller = controller;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void doAction(State state) {

    }

    public static Controller getController(){
        return controller;
    }
}
