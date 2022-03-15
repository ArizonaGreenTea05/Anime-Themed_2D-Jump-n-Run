package gfx;

import core.Direction;
import game.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimationManager {
    private final SpriteSet spriteSet;
    private BufferedImage currentAnimationSheet;
    private final int updatesPerFrame;
    private int currentFrameTime;
    private int frameIndex;
    private int directionIndex;

    // decides which animation has to be chosen

    public AnimationManager(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
        // every 20 frames the next image is loaded
        this.updatesPerFrame = 20;
        this.frameIndex = 0;
        this.currentFrameTime = 0;
        // standard animation is 'stand'
        playAnimation("stand");
    }

    // loads sprite out of defined document (e.g. 'stand')
    // out of animation row regarding the entity's motion
    public Image getSprite() {
        return currentAnimationSheet.getSubimage(
                frameIndex * Game.SPRITE_SIZE,
                directionIndex * Game.SPRITE_SIZE,
                Game.SPRITE_SIZE,
                Game.SPRITE_SIZE
        );
    }


// updates which 'column' of sprite sheet should be used -> animates entity
    public void update(Direction direction) {
        currentFrameTime++;
        directionIndex = direction.getAnimationRow();

        if(currentFrameTime >= updatesPerFrame) {
            currentFrameTime = 0;
            frameIndex++;

            if(frameIndex >= currentAnimationSheet.getWidth() / Game.SPRITE_SIZE) {
                frameIndex = 0;
            }
        }
    }


// setter method for animation sheet
    public void playAnimation(String name) {
        this.currentAnimationSheet = (BufferedImage) spriteSet.get(name);
    }

}
