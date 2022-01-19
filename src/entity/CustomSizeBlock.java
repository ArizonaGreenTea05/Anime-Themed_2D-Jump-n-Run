package entity;

import entity.motion.MotionAndAction;
import gfx.ImageUtils;

import java.awt.*;

public abstract class CustomSizeBlock extends GameObject {

    private String texture;
    private int width, height;

    public CustomSizeBlock(int width, int height, int posX, int posY, String texture) {
        super(width, height, posX, posY);
        this.texture = texture;
        this.width = width;
        this.height = height;
    }

    @Override
    public void update() {

    }


    @Override
    public Image getSprite() {
        return ImageUtils.loadImage("/game/themes/" + menu.Menu.getGameTheme() + "/blocks/" + texture + ".png", width,height);
    }

    @Override
    public MotionAndAction getMotion() {
        return null;
    }

}
