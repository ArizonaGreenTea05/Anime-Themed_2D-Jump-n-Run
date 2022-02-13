package core;

import java.awt.*;

public class ScreenSize {

    // defines constants regarding size of display

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WIDTH = (int) SCREEN_SIZE.getWidth();
    private static final int HEIGHT = (int) SCREEN_SIZE.getHeight();
    private static final int GROUND = HEIGHT + 128;
    private static final int LEFT_BORDER = 9*64;
    private static final int RIGHT_BORDER = WIDTH - LEFT_BORDER;
    private static final int TOP_BORDER = 128;
    private static final int BOTTOM_BORDER = HEIGHT -256;

    public static int getWidth(){
        return WIDTH;
    }

    public static int getHeight(){
        return HEIGHT;
    }

    public static int getGround(){
        return GROUND;
    }

    public static int getLeftBorder() {
        return LEFT_BORDER;
    }

    public static int getRightBorder() {
        return RIGHT_BORDER;
    }

    public static int getTopBorder() {
        return TOP_BORDER;
    }

    public static int getBottomBorder() {
        return BOTTOM_BORDER;
    }
}
