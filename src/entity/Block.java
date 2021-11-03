package entity;

import gfx.ImageUtils;

import java.awt.*;

public abstract class Block extends GameObject {

    private String block;

    public Block(int posX, int posY, String block) {
        super(64, 64, posX, posY);
        this.block = block;
    }

    @Override
    public void update() {
    }


    @Override
    public Image getSprite() {
        return ImageUtils.loadImage("/game/themes/" + menu.Menu.getGameTheme() + "/blocks/" + block + ".png");
    }
}
