package entity;

import core.Motion;
import core.ScreenSize;
import gfx.ImageUtils;
import menu.Menu;

import java.awt.*;

public abstract class StaticEntity extends GameObject{

    private Motion motion;

    public StaticEntity() {
        super(64,64, ScreenSize.getLeftBorder(), ScreenSize.getGround()-64);
        this.motion = new Motion(0);
    }

    @Override
    public void update() {
        position.apply(motion);
    }

    @Override
    public Image getSprite() {
        return ImageUtils.loadImage("/sprites/units/" + Menu.getGameTheme() + "/blocks/grass.png");
    }
}
