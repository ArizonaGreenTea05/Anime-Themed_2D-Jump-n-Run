package core;

import java.awt.*;

public class ScreenSize {
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int width = (int) screenSize.getWidth();
    public static int height = (int) screenSize.getHeight();
    public static int ground = height - 160;
    public static int leftBorder = width/4;
    public static int rightBorder = width/3*2;

    public static int getWidth(){
        return width;
    }

    public static int getHeight(){
        return height;
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
