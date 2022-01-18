package entity;

import entity.motion.Motion;
import game.state.State;
import gfx.ImageUtils;

import java.awt.*;

public abstract class Block extends GameObject {

    private String block;
    protected boolean actionUsed = false;

    public Block(int posX, int posY, String texture) {
        super(64, 64, posX, posY);
        this.block = texture;
        blockActionCauseAble = false;
    }

    @Override
    public void update() {

    }

    @Override
    public Image getSprite() {
        return ImageUtils.loadImage("/game/themes/" + menu.Menu.getGameTheme() + "/blocks/" + block + ".png");
    }

    @Override
    public Motion getMotion() {
        return null;
    }


}
