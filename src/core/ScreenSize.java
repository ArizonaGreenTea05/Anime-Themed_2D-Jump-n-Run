package core;

import game.Game;

import java.awt.*;

public class ScreenSize {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int width = (int) screenSize.getWidth();
    private static int height = (int) screenSize.getHeight();
    private static int ground = height/3*2;;
    private static int leftBorder = width/4;
    private static int rightBorder = width/3*2;
    private static int mapWidth = mapWidth();
    private static int mapHeight = mapHeight();

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
}
