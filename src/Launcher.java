import core.ScreenSize;
import menu.Menu;
public class Launcher {

    public static void main(String[] args) {

        new Menu(ScreenSize.getWidth(), ScreenSize.getHeight());

    }
}