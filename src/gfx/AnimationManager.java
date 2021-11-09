package gfx;

import core.Direction;
import game.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimationManager {
    private SpriteSet spriteSet;
    private BufferedImage currentAnimationSheet;
    private int updatesPerFrame;
    private int currentFrameTime;
    private int frameIndex;
    private int directionIndex;

    public AnimationManager(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
        this.updatesPerFrame = 25;
        this.frameIndex = 0;
        this.currentFrameTime = 0;
        playAnimation("stand");
    }

    public Image getSprite() {
        return currentAnimationSheet.getSubimage(
                frameIndex * Game.SPRITE_SIZE,
                directionIndex * Game.SPRITE_SIZE,
                Game.SPRITE_SIZE,
                Game.SPRITE_SIZE
        );
    }

    public void update(Direction direction) {
        currentFrameTime++;
        directionIndex = direction.getAnimationRow();

        if(currentFrameTime >= updatesPerFrame) {
            currentFrameTime = 0;
            frameIndex++;

            if(frameIndex >= currentAnimationSheet.getWidth() / Game.SPRITE_SIZE - 1) {
                frameIndex = 0;
            }
        }
    }

    public void playAnimation(String name) {
        this.currentAnimationSheet = (BufferedImage) spriteSet.get(name);
    }

}
