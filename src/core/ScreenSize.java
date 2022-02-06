package core;

import game.Game;

import java.awt.*;

public class ScreenSize {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int width = (int) screenSize.getWidth();
    private static final int height = (int) screenSize.getHeight();
    private static final int ground = height-128;
    private static final int leftBorder = 9*64;
    private static final int rightBorder = width-9*64;
    private static final int topBorder = 128;
    private static final int bottomBorder = height-256;
    private static final int mapWidth = mapWidth();
    private static final int mapHeight = mapHeight();

    private static int mapWidth(){

        int mapWidth = width / Game.SPRITE_SIZE;

        while (mapWidth * Game.SPRITE_SIZE <= width){
            mapWidth++;
        }

        return mapWidth;
    }

    private static int mapHeight(){

        int mapHeight = width / Game.SPRITE_SIZE;

        while (mapHeight * Game.SPRITE_SIZE <= height){
            mapHeight++;
        }

        return mapHeight;
    }



    public static int getWidth(){
        return width;
    }

    public static int getHeight(){
        return height;
    }

    public static int getMapWidth(){
        return mapWidth;
    }

    public static int getMapHeight(){
        return mapHeight;
    }

    public static int getGround(){
        return ground;
    }

    public static int getLeftBorder() {
        return leftBorder;
    }

    public static int getRightBorder() {
        return rightBorder;
    }

    public static int getTopBorder() {
        return topBorder;
    }

    public static int getBottomBorder() {
        return bottomBorder;
    }
}
